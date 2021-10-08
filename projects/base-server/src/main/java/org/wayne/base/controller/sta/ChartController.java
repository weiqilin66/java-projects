package org.wayne.base.controller.sta;

import org.wayne.base.entity.Goods;
import org.wayne.base.entity.SecondShopForMax;
import org.wayne.base.entity.TbKw;
import org.wayne.base.mapper.GoodsMapper;
import org.wayne.base.mapper.SecondShopForMaxMapper;
import org.wayne.base.mapper.TbKwMapper;
import org.wayne.base.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/statistics/chart")
public class ChartController {

    @Resource
    GoodsMapper goodsMapper;

    @Autowired
    ChartService chartService;
    @Resource
    TbKwMapper tbKwMapper;
    @Resource
    SecondShopForMaxMapper secondShopForMaxMapper;

//    @Value("${wayne.goodPath}")
    String filePath;


    @GetMapping("/get2diff")
    public RespBeanQ getMaxMinFromShop(String maxShop, String minShop) {

        return RespBeanQ.ok(chartService.get2diff(maxShop, minShop));
    }

    //清除springcache缓存
    @RequestMapping("/remove")
    @CacheEvict(cacheNames = "diff", allEntries = true)
    public String remove() {
        return "清除diff缓存完毕";
    }

    /**
     * @return lwq.returnbean.RespBeanQ
     * @Description 根据差价再分析 最高价格两张以上的店铺作为坑的目标 / 取集合中对象某个字段相同的对象map.containsKey()
     * @date 2020/5/15
     */
    @GetMapping("/thief")
    public RespBeanQ thiefShop() {
        List<Map<String, Object>> maxMin = getMaxMinToday();
        Map<String, Object> resMap = chartService.getMap(maxMin);

        return RespBeanQ.build().setData(resMap);
    }

    /**
     * 抽取当天差价方法
     */
    private List<Map<String, Object>> getMaxMinToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String now = dateFormat.format(new Date());
        return chartService.getMaxMin(now, null);
    }

    /**
     * 根据店铺名称二次加工数据
     */
    @GetMapping("/byShop")
    public RespBeanQ byShop(String shopName) {
        List<Map<String, Object>> maxMin = getMaxMinToday();
        List<Map<String, Object>> resList = new ArrayList<>();
        for (Map<String, Object> map : maxMin) {
            if (map.get("maxShop").equals(shopName)) {
                resList.add(map);
            }
        }
        return RespBeanQ.build().setData(resList);
    }

    /**
     * @return 读取Excel获取宝贝关键字
     * @date 2020/5/13
     */
    @GetMapping("/goodList")
    public RespBeanQ getGoodList() {
        List<TbKw> list = tbKwMapper.selAll();
        return RespBeanQ.build().setData(list);
    }

    @GetMapping("/initExcel")
    public RespBeanQ initExcelData() throws IOException {
        return chartService.loadExcelData(filePath);
    }

    /**
     * @Description 折线图接口
     * @date 2020/5/13
     */
    @GetMapping("/byTitle")
    public Map<String, Object> byTitle(String title, int date) {
        Map<String, Object> resMap = new HashMap<>();
        List<Goods> resList = new ArrayList<>();
        // 检索关键字优化
        String[] arr = title.split("\\s+");
        String condition = "";
        try {
            title = arr[0] + "%" + arr[1] + "%" + arr[2];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            resMap.put("error", "未按规则输入检索条件!");
            return resMap;
        }
        if (arr[2].contains("!")) {// ! 非  过滤标题
            String[] cd = arr[2].split("!");
            condition = cd[1];
            title = arr[0] + "%" + arr[1] + "%" + cd[0];

        }
        List<String> days = getDaysBetwwen(date - 1);
        List<SecondShopForMax> shops = secondShopForMaxMapper.selectAll2();// 统计名店
        List<String> list = new ArrayList<>();
        for (SecondShopForMax shop : shops) {
            list.add(shop.getName());
        }
        resMap.put("shops", list);

        String finalTitle = title;
        String finalCondition = condition;
        list.parallelStream().forEach(shop -> {
            List<Goods> goods = goodsMapper.byTitle(shop, finalTitle, days, finalCondition);
            List<String> times = new ArrayList<>();//所有日期
            for (Goods good : goods) {
                times.add(good.getEtlDate());
            }
            // 得到两个日期数组差异 加工没获取到数据price为0
            List<String> newList = getDIffList(days, times);
            for (String s : newList) {
                Goods good = new Goods();//添加到list中每次都要new新对象 如果是重复set 在集合中上一个对象的值也会被改掉
                good.setEtlDate(s);
                good.setPrice((float) 0);
                goods.add(good);
            }
            // 根据etl_date重新排序集合 n天的宝贝
            Collections.sort(goods, new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
//                    return o1.getEtlDate().compareTo(o2.getEtlDate()); //升序
                    return o2.getEtlDate().compareTo(o1.getEtlDate()); //降序
                }
            });
            float[] prices = new float[date];
            for (int j = 0; j < goods.size(); j++) {
                prices[j] = goods.get(j).getPrice();
            }

            resMap.put(shop, prices);
            // 最新一天的对象
            if (goods.get(0).getPrice() != 0) {
                resList.add(goods.get(0));
            }
        });
        //按销量排序下 (字符串用compareTo 数值用－)
        Collections.sort(resList, new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                return o2.getSales() - o1.getSales(); //降序
            }
        });
        resMap.put("objList", resList);
        return resMap;
    }

    /**
     * @Description 查当日价格差超过20的宝贝
     * @date 2020/3/11
     */
    @GetMapping("/maxAndMin")
    public RespBeanQ getMaxAndMin(String shop) {
        // 日期字符串
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String now = dateFormat.format(new Date());
        List<Goods> total = goodsMapper.selTotal(now);
        if (total.size() == 0) {
            return RespBeanQ.error("今天还没爬取数据!");
        }
        List<Map<String, Object>> maxMin = null;
        if ("".equals(shop) || shop == null) {
            maxMin = chartService.getMaxMin(now, null);
        } else {
            maxMin = chartService.getMaxMin(now, shop);
        }
        if (maxMin.get(0).get("error") != null) {
            return RespBeanQ.error(maxMin.get(0).get("error").toString());
        }
        return RespBeanQ.build().setData(maxMin);
    }


    /**
     * @param [days]
     * @return java.util.Date
     * @Description 获取近days-1天的日期
     * @date 2020/5/12
     */
    private static Date getDateAdd(int days) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

    private static List<String> getDaysBetwwen(int days) { //最近几天日期
        List<String> dayss = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(getDateAdd(days));
        Long startTIme = start.getTimeInMillis();
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
//            System.out.println(df.format(d));
            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }

    /**
     * @param
     * @return
     * @Description 快速比较两个集合的差异(主键)
     * @date 2020/5/12
     */
    private List<String> getDIffList(List<String> allOpenidList, List<String> dbOpenidList) {
        if (dbOpenidList != null && !dbOpenidList.isEmpty()) {
            Map<String, String> dataMap = new HashMap<String, String>();
            for (String id : dbOpenidList) {
                dataMap.put(id, id);
            }

            List<String> newList = new ArrayList<String>();
            for (String id : allOpenidList) {
                if (!dataMap.containsKey(id)) {
                    newList.add(id);
                }
            }
            return newList;
        } else {
            return allOpenidList;
        }
    }
}

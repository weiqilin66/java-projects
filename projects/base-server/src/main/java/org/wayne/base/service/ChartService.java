package org.wayne.base.service;

import org.wayne.base.entity.Goods;
import org.wayne.base.entity.SecondShopForMax;
import org.wayne.base.entity.TbKw;
import org.wayne.base.mapper.GoodsMapper;
import org.wayne.base.mapper.SecondShopForMaxMapper;
import org.wayne.base.mapper.TbKwMapper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: LinWeiQi
 */
@Service
public class ChartService {
    @Resource
    TbKwMapper tbKwMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    SecondShopForMaxMapper secondShopForMaxMapper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String now = dateFormat.format(new Date());

    /**
     * 获取数组元素重复次数
     *
     * @date 2020/5/16
     */
    public Map<String, Object> getMap(List<Map<String, Object>> maxMin) {
        Map<String, Integer> maxMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        List<String> repeatMaxList = new ArrayList<>();
        for (Map<String, Object> maxMinMap : maxMin) {
            String maxShop = maxMinMap.get("maxShop").toString();
            //价格最高 重复的店铺
            if (maxMap.containsKey(maxShop)) {
                if (!repeatMaxList.contains(maxShop)) {
                    repeatMaxList.add(maxShop);
                }
                //记录重复次数
                Integer num = maxMap.get(maxShop);
                maxMap.put(maxShop, num + 1);
            } else {
                maxMap.put(maxShop, 1);
            }
        }
        // 通过value移除
        Collection<Integer> vs = maxMap.values();
        if (vs.contains(1)) {
            vs.removeIf(value -> value == 1);

        }
        resMap.put("list", repeatMaxList);
        resMap.put("map", maxMap);
        return resMap;
    }

    /**
     * @return 读取Excel宝贝存入数据库
     * @date 2020/5/14
     */
    @Deprecated
    public RespBeanQ loadExcelData(String excelPath) throws IOException {
        List<TbKw> kwList = new ArrayList<>();
        Workbook workbook = null;
        try {
            File file = new File(excelPath);
            workbook = new XSSFWorkbook(file);
            //2. 获取 workbook 中表单的数量
//            int numberOfSheets = workbook.getNumberOfSheets(); 只读第一页sheet1 2 4 列kw关键字
            for (int i = 0; i < 1; i++) {
                //3. 获取表单
                Sheet sheet = workbook.getSheetAt(i);
                //4. 获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    //5. 跳过标题行
                    if (j == 0) {
                        continue;//跳过标题行
                    }
                    //6. 获取行
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;//防止数据中间有空行
                    }
                    //7. 获取列数(该行有数值的列数)
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    int[] col = {1, 3};
                    //8 循环行中列
                    for (int k : col) {
                        Cell cell;
                        if (row.getCell(k) == null) {// k =1 但是第一列为空 第二列有值
                            continue;
                        }
                        cell = row.getCell(k);
                        //填充色为 getFillForegroundColor()
                        short fillForegroundColor = cell.getCellStyle().getFillForegroundColor();
                        String cellValue = cell.getStringCellValue();
                        if ("".equals(cellValue)) {
                            continue;
                        }
                        TbKw kw = new TbKw();
                        if (k == 3) {
                            cellValue = "ps4 " + cellValue;
                            kw.setName(cellValue);
                            if (fillForegroundColor != 64) {
                                kw.setType("warning");
                            } else {
                                kw.setType("info");// 默认灰色
                            }
                            kwList.add(kw);

                        } else {
                            cellValue = "switch " + cellValue;
                            kw.setName(cellValue);
                            if (fillForegroundColor != 64) {
                                kw.setType("warning");
                            } else {
                                kw.setType("info");// 默认灰色
                            }
                            kwList.add(kw);
                        }
                    }
                }
            }
        } catch (IOException | InvalidFormatException e) {
            System.out.println(e + "\n打开excel失败");
            return RespBeanQ.error("读取excel失败!");
        } finally {
            assert workbook != null;
            workbook.close();
        }
        // 存入数据库
        try {
            tbKwMapper.delAll();
            tbKwMapper.batchInsert(kwList);
        } catch (Exception e) {
            return RespBeanQ.error("loading Excel sql Error");
        }

        return RespBeanQ.ok();
    }

    /**
     * @Description 分析宝贝差价 加入缓存 测试时候记得关闭缓存
     * 最大价格取二手店铺的 最低价格卡多个条件取符合规则的
     * @date 2020/5/15
     */
    @Cacheable(value = "diff")
    public List<Map<String, Object>> getMaxMin(String date, String shop) {
        List<TbKw> totalList = tbKwMapper.selAll();
        List<Map<String, Object>> resList0 = new ArrayList<>();
        List<Map<String, Object>> resList = Collections.synchronizedList(resList0);
        List<SecondShopForMax> secondShops = new ArrayList<>();
        if (shop != null) {//传入参数只分析单个店铺 目前只分析老猎人
            SecondShopForMax e = new SecondShopForMax();
            e.setName("宁波老猎人电玩店");
            secondShops.add(e);
        } else {
            secondShops = secondShopForMaxMapper.selectAll();
        }
        List<SecondShopForMax> blackList = secondShopForMaxMapper.selBlackList();
        //加工关键字
        for (TbKw tbKw : totalList) {
            String goodName = tbKw.getName();
            String[] arr = goodName.split("\\s+");
            try {
                goodName = arr[0] + "%" + arr[1] + "%" + arr[2];
            } catch (ArrayIndexOutOfBoundsException ignored) {
                HashMap<String, Object> m = new HashMap<>();
                m.put("error", "存在不符合 检索关键字规则的字段=> " + goodName);
                resList.add(m);
                return resList;
            }

        }
        List<SecondShopForMax> finalSecondShops = secondShops;
        totalList.parallelStream().forEach(p -> {
            String goodName = p.getName();
            String[] arr = goodName.split("\\s+");
            String condition = "";
            goodName = arr[0] + "%" + arr[1] + "%" + arr[2];

            if (arr[2].contains("!")) {
                String[] cd = arr[2].split("!");
                condition = cd[1];
                goodName = arr[0] + "%" + arr[1] + "%" + cd[0];
            }
            Map<String, Object> map = new HashMap<>();
            List<Goods> maxList = goodsMapper.getMax(date, goodName, condition, finalSecondShops);
            List<Goods> minList = goodsMapper.getMin(date, goodName, condition, blackList);
            if (!maxList.isEmpty() && !minList.isEmpty()) {
                Goods max;
                Goods min;
                /**
                 * @Description Lambda获取集合中某个属性最大的对象
                 * @param  maxList集合 sales对象中的属性
                 * @date 2020/5/13
                 */
                if (maxList.size() > 1) {// 价格而相同 取销量高的那家
                    Optional<Goods> maxOne = maxList.stream().max(Comparator.comparingInt(Goods::getSales));
                    max = maxOne.get();
                    map.put("max", max);
                    map.put("maxShop", max.getShop());
                    maxList.remove(max);
                    map.put("otherMax", maxList);

                } else {//单条最大最小记录
                    max = maxList.get(0);
                    map.put("max", max);
                    map.put("otherMax", null);
                    map.put("maxShop", max.getShop());
                }
                if (minList.size() > 1) {// 价格而相同 取销量高的那家
                    Optional<Goods> minOne = minList.stream().max(Comparator.comparingInt(Goods::getSales));
                    min = minOne.get();
                    map.put("min", min);
                    map.put("minShop", min.getShop());
                    minList.remove(min);
                    map.put("otherMin", minList);

                } else {
                    min = minList.get(0);
                    map.put("min", min);
                    map.put("otherMin", null);
                    map.put("minShop", min.getShop());
                }
                float diff = max.getPrice() - min.getPrice();
               /* if (diff < 20
//                    && diff>100    //优化标题时候不卡最大差价
                ) {//差价大于20
                    continue;
                }*/
                if (diff >= 20) {
                    map.put("diff", diff);
                    String rName = goodName.replace("%", " ");
                    map.put("goodName", rName);
                    resList.add(map);
                }
            }
        });

        return resList;
    }


    public List<Map<String, Object>> get2diff(String maxShop, String minShop) {
        List<TbKw> totalList = tbKwMapper.selAll();
        // 定义同步集合 否则多线程导致数组越界
        List<Map<String, Object>> resList = Collections.synchronizedList(new ArrayList<>());
        totalList.parallelStream().forEach(p -> {
            String goodName = p.getName();
            String[] arr = goodName.split("\\s+");
            String condition = "";
            goodName = arr[0] + "%" + arr[1] + "%" + arr[2];

            if (arr[2].contains("!")) {
                String[] cd = arr[2].split("!");
                condition = cd[1];
                goodName = arr[0] + "%" + arr[1] + "%" + cd[0];
            }
            List<Goods> maxList = goodsMapper.getMaxMinFromSHop(maxShop, goodName, condition);
            List<Goods> minList = goodsMapper.getMaxMinFromSHop(minShop, goodName, condition);
            if (maxList != null && minList != null && !maxList.isEmpty() && !minList.isEmpty()) {
                //差价大于10
                float mp = maxList.get(0).getPrice();
                float np = minList.get(0).getPrice();
                float diff = mp - np;
                Map<String, Object> res = new HashMap<>();
                if (diff >= 10) {
                    res.put("max", maxList.get(0));
                    res.put("min", minList.get(0));
                    res.put("diff", diff);
                    resList.add(res);
                }
            }
        });
        Collections.sort(resList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (int) ((float) o2.get("diff") - (float) o1.get("diff"));
            }
        });
        return resList;
    }
}

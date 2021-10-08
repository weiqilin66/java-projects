package org.wayne.base.controller.stock;

import org.wayne.base.entity.MyStock;
import org.wayne.base.mapper.MyStockMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/stock/stock1")
public class StockController {
    @Resource
    MyStockMapper stockMapper;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String now = dateFormat.format(new Date());

    @GetMapping("/getTitle")
    public RespBeanQ getTitle(String kw) {
        List<String> list = stockMapper.getTitles(kw);
        return RespBeanQ.ok(list);
    }
    // 分页查询
    /*public RespPageBean getByPage(int page, int size, String keyWord, int id){
        RespPageBean respPageBean = new RespPageBean();
        if (page!=0 && size!=0) {
            page = (page-1)*size;
        }
        long total = stockMapper.getTotal(now);
        respPageBean.setTotal(total);
        Employee employee = new Employee();
        employee.setName(keyWord);
        if(id != -1){
            employee.setId(id);
        }
        List<Employee> list = stockMapper.getByPage(page, size,employee,null);
        respPageBean.setData(list);
        return respPageBean;
    }*/

    @GetMapping("/")
    public RespBeanQ getAll() {
        return RespBeanQ.build().setData(stockMapper.getAllWithHunter(now));
//        return RespBeanQ.build().setData(stockMapper.getAll());
    }

    @PostMapping("/check")
    public RespBeanQ check(@RequestBody MyStock stock) {
        //存在性验证
        List<MyStock> myStocks = stockMapper.selStock(stock.getTitle());
        StringBuilder msg = new StringBuilder("已存在类似商品: [");
        for (MyStock myStock : myStocks) {
            msg.append(myStock.getTitle());
            msg.append(" ");
        }
        msg.append("]");
        if (myStocks.size() > 0) {
            return RespBeanQ.build().setData(msg.toString());
        }
        return RespBeanQ.ok();
    }

    //新增
    @PostMapping("/")
    @Transactional
    public RespBeanQ add(@RequestBody MyStock stock) {
        String title = stock.getTitle();
        int res = stockMapper.insert(stock);
        if (res < 1) {
            return RespBeanQ.error("插入失败");
        }
        return RespBeanQ.ok();
    }

    //修改(库存/所有)
    @PutMapping("/")
    @Transactional
    public RespBeanQ update(@RequestBody MyStock stock) {
        int res = stockMapper.updateById(stock);
        if (res != 1) {
            return RespBeanQ.error("更新出错");
        }
        return RespBeanQ.ok();
    }

    //删除
    @DeleteMapping("/{id}")
    @Transactional
    public RespBeanQ del(@PathVariable int id) {
        int res = stockMapper.deleteById(id);
        if (res != 1) {
            return RespBeanQ.error("删除失败");
        }
        return RespBeanQ.ok();
    }

}

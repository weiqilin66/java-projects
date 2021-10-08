package org.wayne.base.controller.emp;

import org.wayne.base.entity.*;
import org.wayne.base.mapper.*;
import org.wayne.base.service.EmpService;
import org.wayne.base.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wayne.api.entity.RespBeanQ;
import org.wayne.api.entity.RespPageBeanQ;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * @Description: emp分页查询, 导入导出
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00"); //保留两个小数点
    @Autowired
    EmpService empService;
    @Resource
    EmployeeMapper employeeMapper;
    @Resource
    NationMapper nationMapper;
    @Resource
    org.wayne.base.mapper.joblevelMapper joblevelMapper;
    @Resource
    PoliticsstatusMapper politicsstatusMapper;
    @Resource
    PositionMapper positionMapper;
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * @param RequestParam 指定请求参数的相关属性
     * @Description 分页查询
     * @example select *from table limit 5,10  查询第6-15条数据
     * @date 2020/2/13
     */
    @GetMapping("/")
    public RespPageBeanQ getEmpPage(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    @RequestParam(defaultValue = "") String keyWord,
                                    @RequestParam(defaultValue = "-1") int id) {
        return empService.getByPage(page, size, keyWord, id);
    }



    /*---------------------------  文件上传下载  -------------------------------------------------*/

    /**
     * @Description ResponseEntity<byte [ ]> 该类实现响应头、文件数据（以字节存储）、状态封装在一起交给浏览器处理以实现浏览器的文件下载。
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> export() {

        List<Employee> list = employeeMapper.getEmployeeByPage(null, null, null, null);
        return POIUtils.employee2Excel(list);
    }

//    @Value("${wayne.uploadPath}")
    String uploadPath;

    @PostMapping("/upload")
    public RespBeanQ upload(MultipartFile file) throws IOException {
        // -------------------------上传数据----------------------------
        File f = new File(uploadPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        filename += uuid;
//        file.transferTo(new File(uploadPath + filename)); //transferTo之后file缓存中就没有了 此处连接解析不保存文件

        // -------------------------解析数据----------------------------
        List<Employee> employeeList = POIUtils.excel2Employee(file, nationMapper.getNations(), politicsstatusMapper.getAll(), departmentMapper.getAllDepWithoutChildren(),
                positionMapper.queryAll(), joblevelMapper.selectAll());
        employeeList.stream().forEach(System.out::println);
        // ------------------------插入数据库---------------------------
        Integer res = employeeMapper.addEmps(employeeList);

        if (res == employeeList.size()) {
            return RespBeanQ.ok("成功上传" + res + "条数据");
        }
        return RespBeanQ.error("上传失败");
    }

    /*------------------------------  CRUD  ----------------------------------------------------*/
    @PostMapping("/")
    public RespBeanQ addEmp(@RequestBody Employee e) {
        // 原因1 添加前端不传id属性 2 期初employee的id属性为integer
        //e.setId(1);// 神奇bug 自动生成随机值加入id并且超长
        // bug解决 之前id设置为Integer类型 默认值为null 执行employeeMapper.insert(e)时 mybatis把null转换成数值也就是那串超长的id 报类型转换错误
        // 换成int 类型之后bug2 默认值为N/A @requestBody进行JSON转换时候JSON parse error: null
        // 最终解决 前端某个位置指定了this.emp.id =null null无法转换成int类型 之前为integer类型可以赋值为null 但是执行mapper报类型转换错
        // 合同期计算 年份相减 + 月份相减 得到合同期剩余月份数 再转换成年
        getContractTerm(e);
        int res = employeeMapper.insert(e);
        if (res == 1) {
            return RespBeanQ.ok("添加成功");
        }
        return RespBeanQ.error();
    }

    @PutMapping("/")
    public RespBeanQ update(@RequestBody Employee e) {
        getContractTerm(e);
        int res = employeeMapper.updateByPrimaryKeySelective(e);
        if (res == 1) {
            return RespBeanQ.ok("更新成功");
        }
        return RespBeanQ.error();
    }

    // 合同期计算 年份相减 + 月份相减 得到合同期剩余月份数 再转换成年
    private void getContractTerm(Employee e) {
        double month = (Double.parseDouble(yearFormat.format(e.getEndContract()))
                - Double.parseDouble(yearFormat.format(e.getBeginContract()))) * 12
                + Double.parseDouble(monthFormat.format(e.getEndContract()))
                - Double.parseDouble(monthFormat.format(e.getBeginContract()));

        double year = Double.parseDouble(decimalFormat.format(month / 12));
        e.setContractTerm(year);
    }

    @DeleteMapping("/{id}")
    public RespBeanQ del(@PathVariable int id) {

        int res = employeeMapper.deleteById(id);
        if (res == 1) {
            return RespBeanQ.ok("删除成功");
        }
        return RespBeanQ.error();
    }

    @GetMapping("/nations")
    public List<Nation> getNations() {

        return nationMapper.getNations();
    }

    @GetMapping("/joblevels")
    public List<Joblevel> getjoblevels() {

        return joblevelMapper.selectAll();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getpoliticsstatus() {

        return politicsstatusMapper.getAll();
    }

    @RequestMapping("/pos")
    public List<Position> getAllPos() {

        return positionMapper.queryAll();
    }

    @RequestMapping("/max")
    public RespBeanQ max() {
        int res = employeeMapper.getMaxID() + 1;

        return RespBeanQ.build().setStatus(200)
                // 8位字符 不足补0
                .setData(String.format("%08d", res));
    }

    @GetMapping("/dep")
    public RespBeanQ getAllDep() {

        return RespBeanQ.build().setData(departmentMapper.getAllDepByParentId(-1));
    }

}

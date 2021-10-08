package org.wayne.base.service;

import org.wayne.base.entity.Employee;
import org.wayne.base.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.wayne.api.entity.RespPageBeanQ;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: select *from table limit 5,10  查询第6-15条数据
 * @author: LinWeiQi
 */
@Service
public class EmpService {

    @Resource
    EmployeeMapper employeeMapper;

    // 分页查询
    public RespPageBeanQ getByPage(int page, int size, String keyWord, int id) {
        RespPageBeanQ respPageBean = new RespPageBeanQ();
        if (page != 0 && size != 0) {
            page = (page - 1) * size;
        }
        long total = employeeMapper.getTotal(null, null);
        respPageBean.setTotal(total);
        Employee employee = new Employee();
        employee.setName(keyWord);
        if (id != -1) {
            employee.setId(id);
        }
        List<Employee> list = employeeMapper.getEmployeeByPage(page, size, employee, null);
        respPageBean.setData(list);
        return respPageBean;
    }
}

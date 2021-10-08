package org.wayne.base.mapper;

import org.wayne.base.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 递归
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-01-28
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepByParentId(int pid);

    void addDepByProcess(Department dep);

    void delByProcess(Department dep);

    List<Department> getAllDepWithoutChildren();
}

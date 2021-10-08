package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.Menu;
import org.wayne.base.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-01-28
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from role where id in(select rid from hr_role where hrId=#{id} )")
    List<Role> queryRoles(Integer id);

    List<Menu> getAllMenu();

    List<Role> getRoles();

    @Select("select mid from menu_role where rid = #{id}")
    List<Integer> getPermissionsIdByRid(int id);


}

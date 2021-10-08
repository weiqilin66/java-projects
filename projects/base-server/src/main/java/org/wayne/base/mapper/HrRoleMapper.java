package org.wayne.base.mapper;

import org.wayne.base.entity.HrRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-01-28
 */
public interface HrRoleMapper extends BaseMapper<HrRole> {

    int addRoles(int hrid, int[] rids);
}

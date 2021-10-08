package org.wayne.base.mapper;

import org.wayne.base.entity.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-01-28
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    int insertBatchId(@Param("mids") int[] mids, int rid);
}

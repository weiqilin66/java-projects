package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.Politicsstatus;
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
public interface PoliticsstatusMapper extends BaseMapper<Politicsstatus> {

    @Select("select * from politicsstatus")
    List<Politicsstatus> getAll();
}

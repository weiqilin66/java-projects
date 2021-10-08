package org.wayne.base.mapper;

import org.wayne.base.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PositionMapper extends BaseMapper<Position> {

    @Select("select * from position")
    List<Position> queryAll();
}

package org.wayne.base.mapper;

import org.wayne.base.entity.Hr;
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
public interface HrMapper extends BaseMapper<Hr> {

    @Select("select * from hr where username = #{username}")
    Hr getHr(String username);

    List<Hr> getAllHrs(int id);
}

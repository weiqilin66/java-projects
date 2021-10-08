package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.TbKw;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-05-13
 */
public interface TbKwMapper extends BaseMapper<TbKw> {

    List<TbKw> selAll();

    void delAll();

    int batchInsert(List<TbKw> list);
}

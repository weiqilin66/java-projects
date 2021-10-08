package org.wayne.thief.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.api.entity.Card;
import org.wayne.api.entity.TbBo;

import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
public interface CardMapper extends BaseMapper<Card>{

    void batchInsert(List<TbBo> list);
}

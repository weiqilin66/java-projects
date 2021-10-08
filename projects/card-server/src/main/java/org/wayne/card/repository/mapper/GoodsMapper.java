package org.wayne.card.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wayne.api.entity.Goods;


import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select count(*) from goods")
    long selectAll();

    List<Goods> byTitle(String shop, String title, List<String> days, String condition);

    List<Goods> selTotal(String now);

    List<Goods> check(String date);


}

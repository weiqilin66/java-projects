package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.Goods;
import org.wayne.base.entity.SecondShopForMax;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-03-09
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select count(*) from goods")
    long selectAll();

    List<Goods> byTitle(String shop, String title, List<String> days, String condition);

    List<Goods> getMax(String now, String goodName, String condition, List<SecondShopForMax> list);

    List<Goods> getMin(String now, String goodName, String condition, List<SecondShopForMax> list);

    List<Goods> selTotal(String now);

    List<Goods> check(String date);

    List<Goods> getMaxMinFromSHop(String shop, String goodName, String condition);

}

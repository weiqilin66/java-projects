package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.MyStock;
import org.wayne.base.entity.MyStockVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-06-05
 */
public interface MyStockMapper extends BaseMapper<MyStock> {

    @Select("select * from my_stock")
    List<MyStock> getAll();

    @Select("select * from my_stock where title like concat('%',#{title},'%')")
    List<MyStock> selStock(String title);

    List<MyStockVo> getAllWithHunter(String now);

    @Select("select title from goods where shop = '宁波老猎人电玩店' and title like concat('%',#{title},'%') group by title")
    List<String> getTitles(String kw);

    @Select("select count(*) from my_stock a left join goods b on a.title = b.title and b.shop = '宁波老猎人电玩店' and etl_date=#{now} ")
    long getTotal(String now);
}

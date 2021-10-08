package org.wayne.thief.mapper;

import org.apache.ibatis.annotations.Param;
import org.wayne.api.entity.Auctions;
import org.wayne.api.entity.Card;

import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
public interface GoodsMapper {

    void batchInsert(@Param("list") List<Auctions> list, @Param("card") Card card, @Param("date")String date, @Param("time")String time);
    void batchInsert2(@Param("list") List<Auctions> list,@Param("card") Card card,@Param("date")String date,@Param("time")String time);
}

package org.wayne.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wayne.base.entity.SecondShopForMax;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-05-22
 */
public interface SecondShopForMaxMapper extends BaseMapper<SecondShopForMax> {

    @Select("select * from second_shop_for_max where enabled = 1")
    List<SecondShopForMax> selectAll();

    // 折线图
    @Select("select * from second_shop_for_max")
    List<SecondShopForMax> selectAll2();

    @Select("select * from black_list_shop where enabled = 1")
    List<SecondShopForMax> selBlackList();
}

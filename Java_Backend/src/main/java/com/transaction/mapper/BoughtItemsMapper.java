package com.transaction.mapper;

import com.transaction.pojo.BoughtItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author weiyanhu
 * @since 2023-04-19
 */
@Mapper
public interface BoughtItemsMapper extends BaseMapper<BoughtItems> {

    public List<BoughtItems> getBoughtItemsByBuyerId(int buyer_id);

    public List<BoughtItems> getBoughtItemsBySellerId(int seller_id);

}

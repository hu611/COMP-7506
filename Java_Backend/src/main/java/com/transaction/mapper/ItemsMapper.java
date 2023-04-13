package com.transaction.mapper;

import com.transaction.mapper.ItemsMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transaction.pojo.Items;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author weiyanhu
 * @since 2023-04-13
 */
@Mapper
public interface ItemsMapper extends BaseMapper<Items> {

}
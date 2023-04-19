package com.transaction.mapper;

import com.transaction.mapper.ItemsMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transaction.pojo.Items;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    public List<Items> selectAllItems(Map<String, Object> map);

    public Items selectByOffSet(int offset);

}

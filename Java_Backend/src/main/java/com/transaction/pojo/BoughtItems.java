package com.transaction.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author weiyanhu
 * @since 2023-04-19
 */
@Data
@ToString
@TableName("bought_items")
public class BoughtItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "transaction_id", type = IdType.AUTO)
    private Integer transactionId;

    private String itemName;

    private String itemPicLoc;

    private Integer buyerId;

    private Integer sellerId;

    private Integer dealPrice;

}

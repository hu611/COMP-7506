package com.baomidou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author weiyanhu
 * @since 2023-04-19
 */
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

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemPicLoc() {
        return itemPicLoc;
    }

    public void setItemPicLoc(String itemPicLoc) {
        this.itemPicLoc = itemPicLoc;
    }
    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }
    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }
    public Integer getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Integer dealPrice) {
        this.dealPrice = dealPrice;
    }

    @Override
    public String toString() {
        return "BoughtItems{" +
            "transactionId=" + transactionId +
            ", itemName=" + itemName +
            ", itemPicLoc=" + itemPicLoc +
            ", buyerId=" + buyerId +
            ", sellerId=" + sellerId +
            ", dealPrice=" + dealPrice +
        "}";
    }
}

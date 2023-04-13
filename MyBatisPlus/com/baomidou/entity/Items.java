package com.baomidou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author weiyanhu
 * @since 2023-04-13
 */
@ApiModel(value = "Items对象", description = "")
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    private String userId;

    private String itemPicLoc;

    private String itemName;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getItemPicLoc() {
        return itemPicLoc;
    }

    public void setItemPicLoc(String itemPicLoc) {
        this.itemPicLoc = itemPicLoc;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "Items{" +
            "itemId=" + itemId +
            ", userId=" + userId +
            ", itemPicLoc=" + itemPicLoc +
            ", itemName=" + itemName +
        "}";
    }
}

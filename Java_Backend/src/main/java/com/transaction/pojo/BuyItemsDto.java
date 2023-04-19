package com.transaction.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BuyItemsDto {
    String userId;
    String itemId;
}

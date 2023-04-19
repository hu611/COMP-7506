package com.transaction.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellItemsDto {
    String item_name;
    String price;
    String item_description;
    String userId;
}

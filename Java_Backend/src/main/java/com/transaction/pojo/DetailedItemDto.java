package com.transaction.pojo;

import lombok.Data;

@Data
public class DetailedItemDto {
    String item_name;
    String item_description;
    int price;
    String img_url;
}

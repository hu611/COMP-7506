package com.transaction.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 传输给安卓端的类，发送需要的图片链接
 */
@Data
@ToString
public class ResponseItemsDto {
    String[] imageUrlList;
    String[] itemNameList;
}

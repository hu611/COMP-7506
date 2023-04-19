drop table if exists bought_items;

create table bought_items (
                              transaction_id int not null auto_increment,
                              item_name varchar(255) not null,
                              item_pic_loc varchar(255) not null,
                              buyer_id int not null,
                              seller_id int not null,
                              deal_price int not null,
                              PRIMARY KEY(transaction_id)
)
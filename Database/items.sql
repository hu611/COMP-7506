drop table if exists Items;

create table Items(
    item_id int not null auto_increment,
    user_id varchar(20), 
    item_pic_loc varchar(255),
    item_name   varchar(255),
    PRIMARY KEY(item_id)
)
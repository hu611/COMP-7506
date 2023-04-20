drop table if exists Items;

create table Items(
    item_id int not null auto_increment,
    user_id varchar(20), 
    item_pic_loc varchar(255),
    item_name   varchar(255),
    item_description    varchar(512),
    price   int,
    PRIMARY KEY(item_id)
);

INSERT INTO Items (item_id, user_id, item_pic_loc, item_name, item_description, price) VALUES (1, '1', 'bag.png', 'bag', 'This is a bag description, lolllllllll', 200);
INSERT INTO Items (item_id, user_id, item_pic_loc, item_name, item_description, price) VALUES (2, '2', 'apple.png', 'apple', 'This is an apple', 20);
INSERT INTO Items (item_id, user_id, item_pic_loc, item_name, item_description, price) VALUES (3, '3', 'basketball.png', 'basketball', 'basketball', 300);
INSERT INTO Items (item_id, user_id, item_pic_loc, item_name, item_description, price) VALUES (4, '1', 'chicken.png', 'Kunkun', 'Kunkun', 20);


drop table if exists users;

create table Users(
    user_id int not null auto_increment,
    user_name varchar(255),
    user_password varchar(20),
    user_phone varchar(11),
    user_address varchar(255),
    user_balance int not null,
    PRIMARY KEY(user_id)
)
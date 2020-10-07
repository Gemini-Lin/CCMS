/*
    数据库SQL操作
    数据库版本：
*/
drop database if exists ccms;
create database ccms;
use ccms;

drop table if exists `user`;
create table `user`(
    `id` int(8) unsigned not null auto_increment,
    `username` varchar(255) default null,
    `password` varchar(255) default null,
    primary key (`id`)
) engine = InnoDB auto_increment = 3 default charset = utf8;

/*
    默认用户角色为教师、学生
*/
insert into `user` values(1,'teacher','123456');
insert into `user` values(2,'student','123456');
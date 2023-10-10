create database fruitdb;
use fruitdb;

create table t_fruit(
                        fid INT comment '主键',
                        fname VARCHAR(200) COMMENT '名称',
                        price INT  COMMENT '价格',
                        fcount INT  COMMENT '数量' ,
                        remark VARCHAR(200) COMMENT '备注'
);
ALTER TABLE t_fruit ADD PRIMARY KEY t_fruit(fid);

insert into t_fruit(fid,fname,price,fcount)
values(1,'苹果',3,40)
     ,(2,'香蕉',43,55)
     ,(3,'梨子',32,34)
     ,(4,'葡萄',2,22)
     ,(5,'草莓',33,63)
     ,(6,'哈密瓜',45,64)
     ,(7,'桃子',6,34)
     ,(8,'梨子',8,56)
     ,(9,'火龙果',5,33)
     ,(10,'榴莲',32,77)
     ,(11,'山竹',11,15)
     ,(12,'柚子',29,23);
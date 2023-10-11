create database fruitdb;
use fruitdb;
create table t_fruit(
                        fid INT AUTO_INCREMENT PRIMARY KEY comment '主键',
                        fname VARCHAR(200) COMMENT '名称',
                        price INT  COMMENT '价格',
                        fcount INT  COMMENT '数量' ,
                        remark VARCHAR(200) COMMENT '备注'
);

insert into t_fruit(fname,price,fcount)
values('苹果',3,40)
     ,('香蕉',43,55)
     ,('梨子',32,34)
     ,('葡萄',2,22)
     ,('草莓',33,63)
     ,('哈密瓜',45,64)
     ,('桃子',6,34)
     ,('梨子',8,56)
     ,('火龙果',5,33)
     ,('榴莲',32,77)
     ,('山竹',11,15)
     ,('柚子',29,23);
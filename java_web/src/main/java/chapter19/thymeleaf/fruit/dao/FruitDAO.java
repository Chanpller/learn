package chapter19.thymeleaf.fruit.dao;


import chapter19.thymeleaf.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //获取所有的库存列表信息
    List<Fruit> getFruitList();

    Fruit queryFruitById(Integer fid);

    int updateFruit(Integer fid, String fname, Integer price, Integer fcount, String remark);

    int addFruit( String fname, Integer price, Integer fcount, String remark);

    int deleteFruit(Integer fid);

    List<Fruit> getFruitList(int page,String keyword);

    long getFruitListCount(String keyword);
}

package chapter19.thymeleaf.fruit.dao.impl;

import chapter19.thymeleaf.fruit.dao.FruitDAO;
import chapter19.thymeleaf.fruit.pojo.Fruit;
import common.myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from t_fruit");
    }
}

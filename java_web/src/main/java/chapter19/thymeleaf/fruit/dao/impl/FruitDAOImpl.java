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

    @Override
    public Fruit queryFruitById(Integer fid) {
        return super.load("select * from t_fruit where fid=?",fid);
    }

    @Override
    public int updateFruit(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        return super.executeUpdate("update t_fruit set fname=?, price=?,fcount=?,remark=? where fid=?",fname,price,fcount,remark,fid);
    }

    @Override
    public int addFruit( String fname, Integer price, Integer fcount, String remark) {
        return super.executeUpdate("insert into t_fruit(fname, price,fcount,remark) values(?,?,?,?)",fname,price,fcount,remark);
    }

    @Override
    public int deleteFruit(Integer fid) {
        return super.executeUpdate("delete from t_fruit where fid=?",fid);
    }

    @Override
    public List<Fruit> getFruitList(int page,String keyword) {
        return super.executeQuery("select * from t_fruit where fname like ? or remark like ? limit ?,5","%"+keyword+"%","%"+keyword+"%",(page-1)*5);
    }

    @Override
    public long getFruitListCount(String keyword) {
        return (Long) super.executeComplexQuery("select count(*) from t_fruit where fname like ? or remark like ?","%"+keyword+"%","%"+keyword+"%")[0];
    }
}

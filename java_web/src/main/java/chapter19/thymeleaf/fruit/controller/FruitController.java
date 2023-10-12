package chapter19.thymeleaf.fruit.controller;

import chapter19.thymeleaf.fruit.dao.FruitDAO;
import chapter19.thymeleaf.fruit.dao.impl.FruitDAOImpl;
import chapter19.thymeleaf.fruit.pojo.Fruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class FruitController {

    FruitDAO fruitDAO = new FruitDAOImpl();

    public String index(Integer page,String keyword,String type,HttpServletRequest request)  {
        HttpSession session = request.getSession();
        if(type!=null){
            page=1;
            session.setAttribute("keyword",keyword);
        }else{
            if(page==null){
                page=1;
            }
            keyword = (String)session.getAttribute("keyword");
        }
        if(keyword==null)
            keyword="";
        List<Fruit> fruitList = fruitDAO.getFruitList(page,keyword);
        long fruitListCount = fruitDAO.getFruitListCount(keyword);
        session.setAttribute("fruitList",fruitList);
        request.setAttribute("page",page);
        request.setAttribute("fruitListCount",fruitListCount);
        return "index";
    }

    public String add(String fname,Integer price,Integer fcount,String remark)  {
        fruitDAO.addFruit(fname,price,fcount,remark);
        return "redirect:fruit.do";
    }
    public String delete(Integer fid)  {
        fruitDAO.deleteFruit(fid);
        return "redirect:fruit.do";
    }
    public String edit(Integer fid, HttpServletRequest request)  {
        Fruit fruit = fruitDAO.queryFruitById(fid);
        request.setAttribute("fruit",fruit);
       return "edit";
    }
    public String update(Integer fid,String fname,Integer price,Integer fcount,String remark)  {
        fruitDAO.updateFruit(fid,fname,price,fcount,remark);
        return "redirect:fruit.do";
    }
}

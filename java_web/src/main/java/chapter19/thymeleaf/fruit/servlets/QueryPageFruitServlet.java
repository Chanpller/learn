package chapter19.thymeleaf.fruit.servlets;

import chapter19.thymeleaf.fruit.dao.FruitDAO;
import chapter19.thymeleaf.fruit.dao.impl.FruitDAOImpl;
import chapter19.thymeleaf.fruit.pojo.Fruit;
import common.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class QueryPageFruitServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        FruitDAO fruitDAO = new FruitDAOImpl();
        String pageStr = req.getParameter("page");
        if(pageStr!=null){
            page = Integer.valueOf(pageStr);
        }
        List<Fruit> fruitList = fruitDAO.getFruitList(page,"");
        long fruitListCount = fruitDAO.getFruitListCount("");
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);
        req.setAttribute("page",page);
        req.setAttribute("fruitListCount",fruitListCount);
        processTemplate("indexPage",req,resp);
    }
}

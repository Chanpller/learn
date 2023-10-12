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

public class QueryFruitServlet extends ViewBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);
        processTemplate("index",req,resp);
    }
}

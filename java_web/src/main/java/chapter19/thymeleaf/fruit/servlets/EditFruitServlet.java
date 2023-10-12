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

public class EditFruitServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer fid = Integer.valueOf(req.getParameter("fid"));
        FruitDAO fruitDAO = new FruitDAOImpl();
        Fruit fruit = fruitDAO.queryFruitById(fid);
        req.setAttribute("fruit",fruit);
        processTemplate("edit",req,resp);
    }
}

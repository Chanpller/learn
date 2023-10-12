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

public class UpdateFruitServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        FruitDAO fruitDAO = new FruitDAOImpl();
        Integer fid = Integer.valueOf(req.getParameter("fid"));
        String fname = req.getParameter("fname");
        Integer price = Integer.valueOf(req.getParameter("price"));
        Integer fcount = Integer.valueOf(req.getParameter("fcount"));
        String remark = req.getParameter("remark");
        fruitDAO.updateFruit(fid,fname,price,fcount,remark);

        resp.sendRedirect("queryPageFruitServlet");
    }
}

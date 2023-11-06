package com.example.qqzone.myssm.filter;

import com.example.qqzone.myssm.transcation.TranscationManager;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TranscationManager.beginTrans();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            servletRequest.setCharacterEncoding("utf-8");
            filterChain.doFilter(servletRequest,servletResponse);
            TranscationManager.commit();
        }catch (Exception e){
            try {
                TranscationManager.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

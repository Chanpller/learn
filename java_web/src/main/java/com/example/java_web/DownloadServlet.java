package com.example.java_web;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class DownloadServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取的路径是src
        InputStream resourceAsStream = getServletContext().getResourceAsStream("1.jpg");
        resp.setContentType(getServletContext().getMimeType("1.jpg"));

        // 把中文名进行UTF-8 编码操作。
        String str = "attachment; fileName=" + URLEncoder.encode("中文.jpg", "UTF-8");
// 然后把编码后的字符串设置到响应头中
        resp.setHeader("Content-Disposition", str);
        IOUtils.copy(resourceAsStream,resp.getOutputStream());
    }

}

package org.redrock.kebiao.servlet;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.redrock.kebiao.send.Send;
import org.redrock.kebiao.util.KebiaoUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuNum = (String) request.getParameter("stuNum");
        String result = Send.sendPost("https://wx.idsbllp.cn/api/kebiao", "stuNum=" + stuNum);
        String data = KebiaoUtil.jsonToics(JSONObject.fromObject(result));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        response.getOutputStream(), "UTF-8"
                )
        );
        writer.write(data);
        writer.flush();
        writer.close();

    }
}

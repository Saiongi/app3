package application.servlets;

import application.TestDoc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import java.io.PrintWriter;





/**
 * Created by Света on 04.07.2016.
 */
//@WebServlet(name = "MyServlet")
//@Path("/ecm")
public class MyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


 //   @Produces({"application/xml","application/json"})


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //PrintWriter out = response.getWriter();
        //out.print("<h1>Hello Servlet</h1>");

        request.setAttribute("name", "Devcolibri");
        request.getRequestDispatcher("/WEB-INF/mypage.jsp").forward(request, response);

    }
}

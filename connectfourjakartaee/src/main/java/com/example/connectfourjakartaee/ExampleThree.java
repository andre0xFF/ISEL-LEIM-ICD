package com.example.connectfourjakartaee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "ExampleThree",
        description = "JSP Servlet With Annotations",
        urlPatterns = {"/ExampleThree"}
)
public class ExampleThree extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");

        request.setAttribute("text", message);
        request.getRequestDispatcher("/ExampleThree.jsp").forward(request, response);
    }
}

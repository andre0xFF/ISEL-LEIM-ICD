package com.example.connectfourjakartaee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(
        name = "ExampleOne",
        description = "JSP Servlet With Annotations",
        urlPatterns = {"/ExampleOne"}
)
public class ExampleOne extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(
                "<!DOCTYPE html><html>" +
                        "<head>" +
                        "<meta charset=\"UTF-8\" />" +
                        "<title>HTML Rendered by Servlet</title>" +
                        "</head>" +
                        "<body>" +
                        "<h1>HTML Rendered by Servlet</h1></br>" +
                        "<p>This page was rendered by the ExampleOne Servlet!</p>" +
                        "</body>" +
                        "</html>"
        );
    }
}

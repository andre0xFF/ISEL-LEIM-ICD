package com.example.connectfourjakartaee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;

@WebServlet(
        name = "Controller",
        description = "Controller Servlet",
        urlPatterns = {"/Controller"}
)
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if("/GridBoard".equals(request.getRequestURI())){
            int rows = 6;
            int cols = 7;
            Color buttonColor = null;

            Button[][] grid = new Button[rows][cols];
            //TODO fetch data from database for board status???

            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){

                    if(row % 2 == 0){
                        buttonColor = Color.red;
                    }else{
                        buttonColor = Color.blue;
                    }

                    grid[row][col] = new Button(buttonColor);

                }

            }
            request.setAttribute("grid", grid);

            request.getRequestDispatcher("/GridBoard.jsp").forward(request, response);
        }



    }


}

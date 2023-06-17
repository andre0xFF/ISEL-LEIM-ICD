package com.example.connectfourjakartaee;

import java.awt.*;

public class Button {
    private Color color;


    public Button(Color color){

        setColor(color);

    }

    public void setColor(Color color) {

        if(color != null) {
            this.color = color;
        }else{
            this.color = Color.gray;
        }
    }

    public Color getColor() {
        return color;
    }
}

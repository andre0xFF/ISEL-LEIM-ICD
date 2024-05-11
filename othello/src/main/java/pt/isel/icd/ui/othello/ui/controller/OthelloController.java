package pt.isel.icd.othello.ui.controller;

import pt.isel.icd.othello.ui.views.OthelloView;
import pt.isel.icd.othello.ui.views.ViewSwitcher;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.Model;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;

public class OthelloController implements Controller {

    @Override
    public void setView(View view) {
        
    }

    @Override
    public void setModel(Model model) {

    }
    
    public void updateView(JFrame frame, OthelloView newView){
        ViewSwitcher.update(frame, newView);
        
    }
}
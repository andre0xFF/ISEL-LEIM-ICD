package pt.isel.icd.othello.ui.controller;

import pt.isel.icd.othello.ui.views.AuthView;
import pt.isel.icd.othello.ui.views.SignUpView;
import pt.isel.icd.othello.ui.views.StartMenuView;
import pt.isel.icd.othello.ui.views.ViewSwitcher;
import pt.isel.icd.patterns.modelviewcontroller.Controller;
import pt.isel.icd.patterns.modelviewcontroller.Model;
import pt.isel.icd.patterns.modelviewcontroller.View;

import javax.swing.*;

public class StartMenuController implements Controller {
    
    View view;
    Model model;

    
//    private AuthView authview;
//    private SignUpView signUpView;
    
    
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }
    
//    public void navAuthMenu(JFrame frame, View panel){
//        ViewSwitcher.update(frame, panel);
//    }
    
    public void navSignUPMenu(JFrame frame, JPanel panel){
        ViewSwitcher.update(frame, panel);
    }
    
    public void navNewView(JFrame frame, JPanel panel){
        ViewSwitcher.update(frame, panel);
    }
    
    public void updateView(JFrame frame, View view){
        ViewSwitcher.update(frame, new StartMenuView(new JFrame()));
    }
}
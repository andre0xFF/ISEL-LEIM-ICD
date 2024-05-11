package pt.isel.icd.othello.ui.transition;

import pt.isel.icd.patterns.modelviewcontroller.View;

import java.util.HashMap;

public class StateMachine {
    
    public HashMap<Event, View> nextStates = new HashMap<Event, View>();
    
    public void addStateTransition(Event event, View view){
        this.nextStates.put(event, view);
    }
    
    public View getNextState(Event event){
//        State newState = this.getNextState(event)
        
        return this.nextStates.get(event);
         
    }
}
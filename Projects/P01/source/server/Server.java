package server;

import mvc.View;

import java.util.ArrayList;
import java.util.List;

public class Server implements mvc.Model, DAO {

    private ArrayList<View> views = new ArrayList<>();

    @Override
    public List<View> get_views() {

        return this.views;
    }

    public Server() {

    }
}

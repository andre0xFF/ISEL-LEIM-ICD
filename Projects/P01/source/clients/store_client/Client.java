package clients.store_client;

import mvc.View;

import java.util.ArrayList;
import java.util.List;

public class Client implements mvc.Model {

    private ArrayList<View> views = new ArrayList<>();

    @Override
    public List<View> get_views() {

        return this.views;
    }

    public Client() {

    }


}

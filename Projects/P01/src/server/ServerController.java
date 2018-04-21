package server;

import mvc.Controller;
import mvc.Model;

public class ServerController implements Controller {

    private Server model;

    public ServerController(Server model) {

        this.set(model);
    }

    @Override
    public void set(Model model) {

        this.model = (Server) model;
    }
}

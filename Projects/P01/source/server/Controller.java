package server;

public class Controller implements mvc.Controller {

    private Server model;

    public Controller(Server model) {

        this.set(model);
    }

    @Override
    public void set(mvc.Model model) {

        this.model = (Server) model;
    }
}

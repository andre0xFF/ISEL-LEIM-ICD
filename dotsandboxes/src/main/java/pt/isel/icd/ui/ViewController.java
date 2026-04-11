package pt.isel.icd.ui;

import pt.isel.icd.ClientController;

public interface ViewController {

    void setClientController(ClientController controller);
    void setViewManager(ViewManager viewManager);

    String getFxmlPath();
}

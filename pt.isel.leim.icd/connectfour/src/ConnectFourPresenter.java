import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourPresenter implements ActionListener {
    private ConnectFourView view;
    private ConnectFourClientModel model;

    public ConnectFourPresenter(ConnectFourView view, ConnectFourClientModel model) {
        view.setActionListener(this);

        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.loginButton())) {
            System.out.println("Login button clicked");
        }

//        if (e.getSource().equals(view.newGameButton())) {
//
//        }

//        if (e.getSource().equals(view.dropTokenButton())) {
//            model.dropToken(view.column());
//        }
    }
}

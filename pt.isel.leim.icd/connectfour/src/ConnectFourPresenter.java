import org.xml.sax.SAXException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

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
        if (e.getSource().equals(this.view.loginButton())) {
            try {
                this.model.login(
                        this.view.usernameField().getText(),
                        this.view.passwordField().getPassword()
                );
            } catch (IOException | SAXException ex) {
                // TODO
                throw new RuntimeException(ex);
            }
        }

//        if (e.getSource().equals(view.newGameButton())) {
//
//        }

//        if (e.getSource().equals(view.dropTokenButton())) {
//            model.dropToken(view.column());
//        }
    }
}

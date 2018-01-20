package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class DialogDesignController implements Initializable {

    @FXML
    Button continuebtn;
    @FXML
    ToggleButton playbtn;
    @FXML
    ToggleButton hostbtn;
    @FXML
    TextField address;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playbtn.setOnAction(e -> {
            playbtn.setSelected(true);
            hostbtn.setSelected(false);
            address.setDisable(false);
            continuebtn.setDisable(false);
        });
        hostbtn.setOnAction(e -> {
            hostbtn.setSelected(true);
            playbtn.setSelected(false);
            continuebtn.setDisable(false);
        });
        continuebtn.setOnAction(e -> {
            Main.address = address.getText();
            Main.server = hostbtn.isSelected();
            Main game = new Main();
            try {
                game.start(OptionDialog.window);
                OptionDialog.window.centerOnScreen();
            } catch (Exception ex) {
            }
        });
    }

}

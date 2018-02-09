package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class MenuController implements Initializable {

    @FXML
    Button continuebtn;
    @FXML
    ToggleButton playbtn;
    @FXML
    ToggleButton hostbtn;
    @FXML
    TextField address;
    @FXML
    Button rankingbtn;

    private RecordDAO recordDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordDAO = new RecordDAO();
        continuebtn.setDisable(true);

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
            Game.address = address.getText();
            Game.server = hostbtn.isSelected();
            Game game = new Game();
            try {
                game.start(OptionDialog.window);
                OptionDialog.window.centerOnScreen();
            } catch (Exception ex) {
            }
        });

        rankingbtn.setOnAction(e -> {
            recordDAO.getRecords();
        });
    }

}

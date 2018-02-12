package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    Button continuebtn;
    @FXML
    ToggleButton playbtn;
    @FXML
    ToggleButton hostbtn;
    @FXML
    private ToggleButton level1Btn;
    @FXML
    private ToggleButton level2Btn;
    @FXML
    private ToggleButton level3Btn;
    @FXML
    Button rankingbtn;

    private RecordDAO recordDAO;
    public static Socket clientSocket;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordDAO = new RecordDAO();
        continuebtn.setDisable(true);
        level1Btn.setSelected(true);

        playbtn.setOnAction(e -> {
            playbtn.setSelected(true);
            hostbtn.setSelected(false);
            continuebtn.setDisable(false);
            level1Btn.setSelected(false);
            level2Btn.setSelected(false);
            level3Btn.setSelected(false);
            level1Btn.setDisable(true);
            level2Btn.setDisable(true);
            level3Btn.setDisable(true);

        });

        level1Btn.setOnAction(e -> {
            level1Btn.setSelected(true);
            level2Btn.setSelected(false);
            level3Btn.setSelected(false);
        });

        level2Btn.setOnAction(e -> {
            level1Btn.setSelected(false);
            level2Btn.setSelected(true);
            level3Btn.setSelected(false);
        });

        level3Btn.setOnAction(e -> {
            level1Btn.setSelected(false);
            level2Btn.setSelected(false);
            level3Btn.setSelected(true);
        });


        hostbtn.setOnAction(e -> {
            hostbtn.setSelected(true);
            playbtn.setSelected(false);
            continuebtn.setDisable(false);
            level1Btn.setDisable(false);
            level2Btn.setDisable(false);
            level3Btn.setDisable(false);
        });

        continuebtn.setOnAction(e -> {
            Game.server = hostbtn.isSelected();
            Game game = new Game();
            try {
                if(level1Btn.isSelected()){
                    Game.level = 1;
                }else if(level2Btn.isSelected()){
                    Game.level = 2;
                }else if(level3Btn.isSelected()){
                    Game.level = 3;
                }else if(!hostbtn.isSelected()){
                    clientSocket = new Socket(Game.address, Game.getPort());
                    Game.in = new DataInputStream(clientSocket.getInputStream());
                    int tempLevel = Game.in.readInt();
                    Game.level = tempLevel;
                }
                game.start(OptionDialog.window);
                OptionDialog.window.centerOnScreen();
            } catch (Exception ex) {
            }
        });

        rankingbtn.setOnAction(e -> {
            recordDAO.getRecords();
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("Records.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Ranking");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch(Exception ex){
                System.out.println(ex);
            }
        });
    }

}

package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordsController implements Initializable{

    private RecordDAO recordDAO;
    private StringBuilder stringBuilder;

    @FXML
    private Text recordsData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordDAO = new RecordDAO();
        stringBuilder = new StringBuilder();
        recordDAO.getRecords().forEach(n -> {
                    stringBuilder.append(n);
                    stringBuilder.append("\n");
                });

        recordsData.setText(stringBuilder.toString());
        recordsData.setTextAlignment(TextAlignment.JUSTIFY);
    }
}
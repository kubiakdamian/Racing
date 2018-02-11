package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class StatusUpdater extends Pane{
    Text status = new Text();
    Label label = new Label("Enter name:");
    TextField textField = new TextField ();
    Button submitBtn = new Button("Submit");
    private RecordDAO recordDAO;

    public StatusUpdater(double x, double y){
        recordDAO = new RecordDAO();
        setTranslateX(x);
        setTranslateY(y - 50);
        setStyle("-fx-background-color: #CCC;" +
                "-fx-background-radius: 20px");
        setPrefHeight(70);
        setPrefWidth(300);
        setOpacity(0);
        status.setTranslateY(40);
        status.setTranslateX(50);
        label.setTranslateX(5);
        label.setTranslateY(100);
        label.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold");
        label.setTextFill(Color.WHITE);
        textField.setTranslateX(125);
        textField.setTranslateY(102);
        submitBtn.setTranslateX(90);
        submitBtn.setTranslateY(150);
        submitBtn.setStyle("-fx-background-color: aquamarine;" + "-fx-cursor: hand");
        submitBtn.setDisable(true);
        submitBtn.setMinSize(100, 30);
        submitBtn.setOnAction((event -> recordDAO.save(textField.getText(), (double) Game.getCurrentTime() / 1000)));

        textField.textProperty().addListener((ov, oldLength, newLength) -> {
            if(newLength.length() < 4 || newLength.length() > 15){
                submitBtn.setDisable(true);
            }
            else{
                submitBtn.setDisable(false);
            }
        });

        getChildren().addAll(status, label, textField, submitBtn);
        status.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 17));
        status.setFill(new Color(107 / 255.0, 162 / 255.0, 252 / 255.0, 1.0));
    }

    public void setStatusText(String message){
        status.setText(message);
        this.setOpacity(1);
    }
}

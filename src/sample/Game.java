package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Game extends Application {
    public static double width = 800, height = 600;
    private static double FPS = 30.0;
    private Pane container;
    private Label timeText;
    static String address;
    static boolean server;
    private DataOutputStream out;
    private DataInputStream in;
    private Car car1;
    private Car car2;
    private double x = 0, y = 0, z = 0;
    private boolean keyPressed = false;
    private KeyCode keyPressedCode = null;
    private StatusUpdater statusUpdater = new StatusUpdater(width / 2 - 150, height / 2 - 35);
    private Timeline gameLoop;
    private long time = 0;
    private long currentTime = 0;
    private Line meta;
    private RecordDAO recordDAO;

    @Override
    public void start(Stage primaryStage) throws Exception{
        recordDAO = new RecordDAO();
        container = new Pane();
        timeText = new Label();
        Scene scene = new Scene(container, width, height);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        loadLevel(container.getChildren());

        car1 = new Car();
        car2 = new Car();
        timeText.setTranslateX(370);
        timeText.setTranslateY(270);
        timeText.setTextFill(Color.WHITE);
        timeText.setStyle("-fx-font-weight: bold;" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 30px");
        container.getChildren().add(timeText);


        if(server){
            setCars(car1, car2);
        }else{
            setCars(car2, car1);
        }

        container.getChildren().addAll(car1.getGraphics(), car2.getGraphics());
        container.setOnKeyPressed(event -> {
            keyPressed = true;
            keyPressedCode = event.getCode();
        });
        container.setOnKeyReleased(event -> keyPressed = false);

        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), event -> {
            currentTime = System.currentTimeMillis() - time;
            if(currentTime != System.currentTimeMillis()){
                timeText.setText(String.valueOf(currentTime / 1000) + "s");
            }
            if(keyPressed){
                if(keyPressedCode == KeyCode.SPACE) {
                    car1.setDirection(car1.direction -= (3));
                }
            }

            car1.translateByRadius(car1.speed);
            checkForCollisions(car1);

            try{
                if(out != null){
                    out.writeDouble(car1.direction);
                    out.writeDouble(car1.locationX.get());
                    out.writeDouble(car1.locationY.get());
                }
            }catch(Exception ex){
            }

            try{
                if(in != null){
                    car2.setDirection(x);
                    car2.locationX.set(y);
                    car2.locationY.set(z);
                }
            }catch (Exception ex){
            }
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

        if(server){
            Rectangle r = new Rectangle(width, height);
            r.setFill(Color.WHITE);
            r.setOpacity(0.6);
            container.getChildren().addAll(r);
            Text t = new Text("Waiting for player 2...");
            ProgressIndicator p = new ProgressIndicator();

            p.setLayoutX(width / 2 + 50);
            p.setLayoutY(height / 2 - 33);
            t.setX(width / 2 - 200);
            t.setY(height / 2);
            t.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 17));
            t.setFill(new Color(107/ 255.0, 162 / 255.0, 252 / 255.0, 1.0));
            container.getChildren().addAll(t, p);
        }

        new Thread(() -> {
            try{
                Socket socket;
                if(server){
                    ServerSocket serverSocket = new ServerSocket(5);
                    socket = serverSocket.accept();
                    Platform.runLater(() -> {
                        container.getChildren().remove(container.getChildren().size() - 3, container.getChildren().size());
                        container.requestFocus();
                        time = System.currentTimeMillis();
                    });
                }else{
                    socket = new Socket(address, 5);
                    Platform.runLater(() -> {
                        container.requestFocus();
                        time = System.currentTimeMillis();
                    });
                }
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                while (true){
                    x = in.readDouble();
                    y = in.readDouble();
                    z = in.readDouble();
                }
            }catch (Exception e){
                System.out.println("Server error " + e.toString());
            }
        }).start();

        primaryStage.setOnCloseRequest(event -> System.exit(1));
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void loadLevel( ObservableList<Node> list) {
        list.add(Level.ellipse);
        list.add(Level.collisionEllipse);

        meta = new Line(Level.meta[0], Level.meta[1], Level.meta[2], Level.meta[3]);
        meta.setStroke(Color.ORANGE);
        meta.setStrokeWidth(3);
        meta.setOpacity(0.8);

        list.add(meta);

        list.addAll(statusUpdater);
    }

    private void checkForCollisions(Car car) {
        if (Collision.DetacteCollision(car.graphics, meta)){
            timeText.setText("");
            time = System.currentTimeMillis() - time;
            statusUpdater.setTextAndAnimate("Finished in: " + String.format("%.3f", time / 1000.0) + " seconds");
            gameLoop.stop();
            recordDAO.save("Nowy", (double) currentTime / 1000);
        }

        if (Collision.DetacteCollision(car.graphics, Level.ellipse)
                || Collision.DetacteCollision(car.graphics, Level.collisionEllipse)){
            if (!car.isColliding) {
                car.setLocationByVector(Level.startAfterCollision[0] - car.w, height - Level.startAfterCollision[1]);
                car.setDirection(90);
            }
        } else {
            car.isColliding = false;
        }
    }

    private void setCars(Car car1, Car car2){
        car1.setLocationByVector(Level.startCar1[0] - car1.w, height - Level.startCar1[1]);
        car1.setDirection(90);
        car1.getGraphics().setFill(Color.MEDIUMPURPLE);
        car2.setLocationByVector(Level.startCar2[0] - car2.w, height - Level.startCar2[1]);
        car2.setDirection(90);
        car2.getGraphics().setFill(Color.ORANGE);
    }

}


package com.example.project;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class MainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    static String newtonSim,girl,wavesPic;

    @Override
    public void start(Stage primaryStage) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object o = jsonParser.parse(new FileReader(this.getClass().getClassLoader().getResource("index.json").getFile()));
            JSONObject jsonObject = (JSONObject) o;
            newtonSim = (String) jsonObject.get("newton");
            girl = (String)  jsonObject.get("girl");
            wavesPic = (String) jsonObject.get("wavesPic");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        Pane root = new Pane();
        Stage newtonStage = new Stage();
        Stage wavesStage = new Stage();
        Stage creditStage = new Stage();

        Label title = new Label("Physics Learning Tool");
        title.setAlignment(Pos.TOP_CENTER);
        title.setLayoutY(20);
        title.setLayoutX(360);
        title.setTextFill(Color.WHITE);


        Image newtonImg = new Image(newtonSim);
        ImageView iView = new ImageView();
        iView.setPreserveRatio(true);
        iView.setFitHeight(250);
        iView.setLayoutX(205);
        iView.setLayoutY(150);

        Color grey = Color.web("#868484");
        Button newton = new Button("Newton Simulator");
        newton.setLayoutX(250);
        newton.setLayoutY(500);

        Rectangle nr = new Rectangle();
        nr.setX(250); nr.setY(500);
        nr.setHeight(35); nr.setWidth(147);
        nr.setFill(Color.TRANSPARENT);
        nr.setArcHeight(7); nr.setArcWidth(7);
        Line nl1 = new Line();
        nl1.setStroke(grey);
        nl1.setStartX(650); nl1.setEndX(675);nl1.setStartY(500); nl1.setEndY(500);
        nl1.setVisible(false);

        Line nl2 = new Line();
        nl2.setStartX(745); nl2.setEndX(720); nl2.setStartY(535); nl2.setEndY(535);
        nl2.setVisible(false);
        nl2.setStroke(grey);

        PathTransition npt1 = new PathTransition(Duration.seconds(2), nr, nl1);
        npt1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        npt1.setCycleCount(Timeline.INDEFINITE);
        npt1.setDelay(Duration.seconds(1));
        npt1.setInterpolator(Interpolator.LINEAR);
        npt1.play();

        PathTransition npt2 = new PathTransition(Duration.seconds(2), nr, nl2);
        npt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        npt2.setCycleCount(Timeline.INDEFINITE);
        npt2.setInterpolator(Interpolator.LINEAR);
        npt2.play();

        Image wavesImg = new Image(wavesPic);

        Button waves = new Button("Waves Simulator");
        waves.setLayoutX(450);
        waves.setLayoutY(500);

        Rectangle wr = new Rectangle();
        wr.setX(450); wr.setY(500);
        wr.setHeight(35); wr.setWidth(147);
        wr.setFill(Color.TRANSPARENT);
        wr.setArcHeight(7); wr.setArcWidth(7);

        Line wl1 = new Line();
        wl1.setStartX(450); wl1.setEndX(475); wl1.setStartY(500); wl1.setEndY(500);
        wl1.setVisible(false);
        wl1.setStroke(grey);

        Line wl2 = new Line();
        wl2.setStartX(545); wl2.setEndX(520); wl2.setStartY(535); wl2.setEndY(535);
        wl2.setVisible(false);
        wl2.setStroke(grey);

        PathTransition wpt1 = new PathTransition(Duration.seconds(2), wr, wl1);
        wpt1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        wpt1.setCycleCount(Timeline.INDEFINITE);
        wpt1.setDelay(Duration.seconds(1));
        wpt1.setInterpolator(Interpolator.LINEAR);
        wpt1.play();

        PathTransition wpt2 = new PathTransition(Duration.seconds(2), wr, wl2);
        wpt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        wpt2.setCycleCount(Timeline.INDEFINITE);
        wpt2.setInterpolator(Interpolator.LINEAR);
        wpt2.play();


        Button authors = new Button("About Us ");
        authors.setLayoutX(650);
        authors.setLayoutY(500);

        Rectangle ar = new Rectangle();
        ar.setX(650); ar.setY(500);
        ar.setHeight(35); ar.setWidth(97);
        ar.setFill(Color.TRANSPARENT);
        ar.setArcHeight(7); ar.setArcWidth(7);

        Line al1 = new Line();
        al1.setStartX(650); al1.setEndX(675); al1.setStartY(500); al1.setEndY(500);
        al1.setVisible(false);
        al1.setStroke(grey);

        Line al2 = new Line();
        al2.setStartX(745); al2.setEndX(720); al2.setStartY(535); al2.setEndY(535);
        al2.setVisible(false);
        al2.setStroke(grey);

        PathTransition apt1 = new PathTransition(Duration.millis(1500), ar, al1);
        apt1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        apt1.setCycleCount(Timeline.INDEFINITE);
        apt1.setDelay(Duration.millis(750));
        apt1.setInterpolator(Interpolator.LINEAR);
        apt1.play();

        PathTransition apt2 = new PathTransition(Duration.millis(1500), ar, al2);
        apt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        apt2.setCycleCount(Timeline.INDEFINITE);
        apt2.setInterpolator(Interpolator.LINEAR);
        apt2.play();
        root.getChildren().addAll(ar,nr,wr,title,waves,newton,authors,iView,al1,al2,nl1,nl2,wl1,wl2);

        Scene s = new Scene(root, 1000, 650);
        root.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #0f0c29, #302b63, #313149FF)");
        s.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        primaryStage.setScene(s);
        primaryStage.setTitle("Physics Learning Tool");
        primaryStage.setResizable(false);
        Image girlImg = new Image(girl);
        ImageView girlView = new ImageView(girlImg);
        primaryStage.show();



        waves.setOnAction(actionEvent -> {
            if (!wavesStage.isShowing() && !newtonStage.isShowing() && !creditStage.isShowing() ) {
                Waves w = new Waves(wavesStage);
                primaryStage.close();
            }
        });

        newton.setOnAction(actionEvent -> {
            if (!newtonStage.isShowing() && !wavesStage.isShowing() && !creditStage.isShowing()) {
                Newton n = new Newton(newtonStage);
                primaryStage.close();
            }
        });

        newton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iView.setImage(newtonImg);
                iView.setX(0);
                nl1.setVisible(true);
                nl2.setVisible(true);
            } else {
                iView.setImage(null);
                nl1.setVisible(false);
                nl2.setVisible(false);
            }
        });

        authors.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iView.setImage(girlImg);
                iView.setX(-60);
                al1.setVisible(true);
                al2.setVisible(true);
            } else {
                iView.setImage(null);
                al1.setVisible(false);
                al2.setVisible(false);
            }
        });

        waves.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iView.setImage(wavesImg);
                wl1.setVisible(true);
                wl2.setVisible(true);
            } else {
                iView.setImage(null);
                wl1.setVisible(false);
                wl2.setVisible(false);
            }
        });

        Pane root2 = new Pane();
        girlView.setX(500);
        girlView.setY(175);
        girlView.setPreserveRatio(true);
        girlView.setFitHeight(350);
        root2.setStyle("-fx-background-color: " +
                "linear-gradient(from 25% 25% to 100% 100%, #0f0c29, #302b63, #313149FF)");
        Scene s2 = new Scene(root2, 1000, 500);
        creditStage.setScene(s2);
        creditStage.setTitle("About us");
        creditStage.setResizable(false);

        Text longtext = new Text("""
                Django Unchained is composed of, Laurenz Marius Gomez, Jatin Kalsi, Guiliano Verdone, and Joshua Vilda.
                We're currently students of Vanier College in the Computer Science and Math in our 4th semester.
                Collectively, we decided to create two simulators in regards to physic principles we learned during our time here.
                Waves Simulator: Laurenz Marius Gomez & Guiliano Verdone
                Newton Simulator: Jatin Kalsi & Joshua Vilda
                Email 419NigerianPrinceEA@hotmail.com if you come across any bugs
                All photos are used with Fair Dealing.
                The fair dealing exception in the Copyright Act allows us to use other people’s
                copyright protected material for the purpose of research, private study,
                education, satire, parody, criticism,review or news reporting,
                provided that what you do with the work is ‘fair’.
                """);
        longtext.setFill(Color.WHITE);
        longtext.setY(100);
        longtext.setX(30);
        longtext.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 17");
        root2.getChildren().addAll(longtext, girlView);


        authors.setOnAction(actionEvent -> {
            if (!wavesStage.isShowing() && !newtonStage.isShowing() && !creditStage.isShowing()) {
                creditStage.show();

            }
        });
    }
}

package com.example.physics;


// TODO help window
// TODO update velocity values
// TODO align text in imperial

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.application.Platform.exit;

public class Newton extends Application {
    static AnimationTimer timer, timer2;
    static double time, decTime, speed, oppSpeed, prevSpeed, prevOppSpeed, distance, prevDeltaX, accDistance, distancePerFrame,
            acceleration, deceleration, accTime, totalTime, accTimeWhenStopped, decTimeWhenStopped, timeWhenStopped, accTotalTimeWhenStopped,decTotalTimeWhenStopped,totalTimeWhenStopped;
    static Line distanceLine, impDistanceLine;
    static Text distanceIndicator, impDistanceIndicator;
    static ArrayList<Line> distanceLineArrayList = new ArrayList<>();
    static ArrayList<Line> impDistanceLineArrayList = new ArrayList<>();
    static ArrayList<Text> distanceIndicatorArrayList = new ArrayList<>();
    static ArrayList<Text> impDistanceIndicatorArrayList = new ArrayList<>();
    static String menuPic, homePic, helpPic, dusk, dayCar, day, nightCar,god,skateboard, nMenuPic;
    static int counter = 0;
    public Newton(Stage newtonStage) {
        start(newtonStage);
    }
    @Override
    public void start(Stage primaryStage) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object o = jsonParser.parse(new FileReader(this.getClass().getClassLoader().getResource("index.json").getFile()));
            JSONObject jsonObject = (JSONObject) o;
            menuPic = (String) jsonObject.get("menuIcon");
            homePic = (String) jsonObject.get("homeIcon");
            helpPic = (String) jsonObject.get("newtonSecondStagePic");
            dusk = (String) jsonObject.get("dusk");
            dayCar = (String) jsonObject.get("dayCar");
            day = (String) jsonObject.get("day");
            god = (String) jsonObject.get("god");
            skateboard = (String) jsonObject.get("board");
            nMenuPic = (String ) jsonObject.get("nMenuIcon");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        FlowPane root = new FlowPane();
        VBox vbox = new VBox(30);
        HBox navigationHBox = new HBox(230);
        navigationHBox.setPadding(new Insets(10, 5, 0, 5));
        Pane menuPane = new Pane();
        HBox controlHBox = new HBox(20);
        controlHBox.prefWidth(1000);
        controlHBox.setPadding(new Insets(0, 0, 0, 115));
        HBox appliedForceHBox = new HBox();
        HBox massHBox = new HBox();
        HBox frictionForceHBox = new HBox();
        VBox tfVbox = new VBox(3);
        tfVbox.setPadding(new Insets(10,0,0,0));
        VBox labelVBox = new VBox(20);
        VBox buttonVbox = new VBox(20);
        buttonVbox.setPadding(new Insets(20, 5, 5, 10));

        Pane pane = new Pane();

        vbox.setPrefHeight(80);
        vbox.setPrefWidth(1000);
        pane.setPrefHeight(450);
        pane.setPrefWidth(1000);
        Line separator = new Line();
        separator.setStartX(0);
        separator.setEndX(1000);
        separator.setEndY(350);
        separator.setStartY(350);
        separator.setStroke(Color.WHITE);


        Menu settings = new Menu("Settings");
        Menu metricSystem = new Menu("Metric System");
        CheckMenuItem imperial = new CheckMenuItem("Imperial");
        CheckMenuItem metric = new CheckMenuItem("Metric");
        metric.setSelected(true);
        metricSystem.getItems().addAll(metric, imperial);
        Menu objects = new Menu("Objects");
        CheckMenuItem carObject = new CheckMenuItem("Car");
        CheckMenuItem skateboardObject = new CheckMenuItem("Skateboard");
        CheckMenuItem godObject = new CheckMenuItem("Motorcycle");
        Menu mode = new Menu("Mode");
        carObject.setSelected(true);
        objects.getItems().addAll(carObject, skateboardObject, godObject);
        CheckMenuItem lightMode = new CheckMenuItem("Light Mode");
        CheckMenuItem darkMode = new CheckMenuItem("Dark Mode");
        lightMode.setSelected(true);
        mode.getItems().addAll(lightMode,darkMode);
        Menu showForces = new Menu("Show Forces");
        CheckMenuItem showForcesOn = new CheckMenuItem("On");
        CheckMenuItem showForcesOff = new CheckMenuItem("Off");
        showForcesOff.setSelected(true);
        showForces.getItems().addAll(showForcesOn, showForcesOff);
        settings.getItems().addAll(metricSystem,mode, objects, showForces);
        MenuItem menuHelp = new MenuItem("Help");
        MenuItem exitProgram = new MenuItem("Exit Program");
        MenuButton menuBtn = new MenuButton("", null,settings, menuHelp, exitProgram);
        Image menuIcon = new Image(menuPic);
        ImageView menuView = new ImageView(menuIcon);
        menuView.setPreserveRatio(true);
        menuView.setFitHeight(25);
        menuBtn.setGraphic(menuView);
        menuBtn.setId("navigation");
        menuPane.getChildren().addAll(menuBtn);
//Menu Settings
        Image homeIcon = new Image(homePic);
        ImageView homeView = new ImageView(homeIcon);
        homeView.setPreserveRatio(true);
        homeView.setFitHeight(80);
        Button homeBtn = new Button();
        homeBtn.setGraphic(homeView);
        homeBtn.setId("navigation");
//home Button settings

        Label warning = new Label();
        warning.setId("warning");
        warning.setVisible(false);
        navigationHBox.getChildren().addAll(menuPane,warning,homeBtn );
        navigationHBox.setAlignment(Pos.CENTER);

        Label forceLabel = new Label("Applied Force (N):");
        TextField forceTextField = new TextField();;
        appliedForceHBox.getChildren().addAll(forceLabel,forceTextField);
//forceHBoxSettings
        Label massLabel = new Label("Mass (kg):             ");
        TextField massTextField = new TextField();
        massHBox.getChildren().addAll(massLabel,massTextField);
//massHBoxSettings
        Label surfaceLabel = new Label("Friction Force (N):");
        TextField frictionForceTextField = new TextField();
        frictionForceHBox.getChildren().addAll(surfaceLabel,frictionForceTextField);
        tfVbox.getChildren().addAll(appliedForceHBox,massHBox,frictionForceHBox);
//tfVBoxSettings
        Label velocityDisplayLabel = new Label("Velocity: 0.00km/h");
        velocityDisplayLabel.setTextFill(Color.RED);
        Label accelerationDisplayLabel = new Label("Acceleration: 0.00m/s²");

        accelerationDisplayLabel.setTextFill(Color.ORANGE);
        Label timeDisplayLabel = new Label("Time: 0.00s");
        Color color = Color.web("#9784ed");
        timeDisplayLabel.setTextFill(color);
        Label distanceDisplayLabel = new Label("Distance: 0.00m");
        Color omori = Color.web("#6c0ffe");
        distanceDisplayLabel.setTextFill(omori);
        labelVBox.getChildren().addAll(velocityDisplayLabel,accelerationDisplayLabel,timeDisplayLabel,distanceDisplayLabel);

//labelVboxSettings
        Button startBtn = new Button("Start");
        startBtn.setId("controlButton");
        startBtn.setStyle("-fx-background-color: #34ad34");
        Button resetBtn = new Button("Reset");
        resetBtn.setId("controlButton");
        resetBtn.setStyle("-fx-background-color: #a3a5a5");
        buttonVbox.getChildren().addAll(startBtn,resetBtn);
//buttonVboxSettings
        controlHBox.getChildren().addAll(tfVbox,labelVBox,buttonVbox);
        vbox.getChildren().addAll(navigationHBox,controlHBox,separator);
        vbox.setAlignment(Pos.TOP_CENTER);


        Image dayCarImg = new Image(dayCar);
        Image godImg = new Image(god);
        Image skateboardImg = new Image(skateboard);

        ImageView objectView = new ImageView(dayCarImg);
        objectView.setPreserveRatio(true);
        objectView.setFitHeight(80);
        objectView.setLayoutY(300);
        objectView.setY(10);
        objectView.setX(50);
        Line line = new Line();
        line.setStroke(Color.color(0,0,0,0));
        line.setStartY(300);
        line.setEndY(300);
        line.setStartX(0);
        line.setEndX(1000);


        Image duskImg = new Image(dusk);
        Image dayImg = new Image(day);
        Image nMenu = new Image(nMenuPic);
        ImageView groundView = new ImageView(dayImg);
        groundView.setY(0);
        groundView.setFitWidth(10000);
        groundView.setFitHeight(400);
        pane.getChildren().addAll(groundView, line, objectView);
        warning.setLayoutX(100);
        warning.setLayoutY(3500);

        for (int i = 0; i < 10000; i+= 500) {
            distanceLine = new Line(i + 200, 200, i + 200, 350);
            distanceIndicator = new Text("" + i);
            distanceIndicator.setX(i + 200);
            distanceIndicator.setY(200);
            distanceIndicator.setStyle("-fx-font-family: Verdana;");
            distanceLine.setStroke(Color.RED);
            distanceLine.getStrokeDashArray().addAll(25d, 10d);
            distanceLineArrayList.add(distanceLine);
            distanceIndicatorArrayList.add(distanceIndicator);
        }

        for (int i = 0; i < 10000; i+= 500) {
            impDistanceLine = new Line((i / 1.09361) + 200, 200, (i / 1.09361) + 200, 350);
            impDistanceIndicator = new Text("" + i);
            impDistanceIndicator.setX((i / 1.09361) + 200);
            impDistanceIndicator.setY(200);
            impDistanceIndicator.setStyle("-fx-font-family: Verdana;");
            impDistanceLine.setStroke(Color.RED);
            impDistanceLine.getStrokeDashArray().addAll(25d, 10d);
            impDistanceLineArrayList.add(impDistanceLine);
            impDistanceIndicatorArrayList.add(impDistanceIndicator);
        }

        for (Line l : distanceLineArrayList) {
            pane.getChildren().addAll(l);
        }

        for (Text t : distanceIndicatorArrayList) {
            pane.getChildren().addAll(t);
        }

        for (Line l : impDistanceLineArrayList) {
            pane.getChildren().addAll(l);
            l.setVisible(false);
        }

        for (Text t : impDistanceIndicatorArrayList) {
            pane.getChildren().addAll(t);
            t.setVisible(false);
        }

        Rectangle arrowTail = new Rectangle(50, 15);
        Polygon arrowHead = new Polygon();
        Label arrowForceLabel = new Label("0N");
        arrowHead.getPoints().addAll(0.0, 0.0, 30.0, 20.0, 0.0, 40.0);
        arrowHead.setFill(Color.LIGHTGREEN);
        arrowTail.setFill(Color.LIGHTGREEN);
        arrowForceLabel.setLayoutX(370);
        arrowForceLabel.setLayoutY(75);
        arrowForceLabel.setVisible(false);
        arrowHead.setLayoutX(500);
        arrowHead.setLayoutY(100);
        arrowHead.setVisible(false);
        arrowTail.setX(450);
        arrowTail.setY(112.5);
        arrowTail.setVisible(false);
        Group g = new Group();
        g.getChildren().addAll(arrowTail, arrowHead, arrowForceLabel);
        pane.getChildren().addAll(g);
        root.getChildren().addAll(vbox,pane);

        Scene s = new Scene(root, 1000, 650);
        s.getStylesheets().add(getClass().getResource("newton.css").toExternalForm());
        primaryStage.setScene(s);
        primaryStage.setTitle("Newton's 2nd Law Simulator");
        primaryStage.setResizable(false);
        primaryStage.show();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (counter > 0 && startBtn.getText() == "Pause") {
                    if (distance < 350) {
                        arrowHead.setFill(Color.LIGHTGREEN);
                        arrowTail.setFill(Color.LIGHTGREEN);
                        arrowForceLabel.setText(forceTextField.getText()+"N");

                        speed = acceleration * (accTime + accTotalTimeWhenStopped);
                        distance = 0.5 * speed * (accTime + accTotalTimeWhenStopped);
                        if (metric.isSelected()) {
                            accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", acceleration) + "m/s²");
                        }
                        if (imperial.isSelected()) {
                            accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", acceleration * 3.28084) + "ft/s²");
                        }

                        objectView.setTranslateX(distance);
                        accDistance = distance;
                        accTime += 1/60d;
                    }
                    else {
                        arrowHead.setFill(Color.RED);
                        arrowTail.setFill(Color.RED);
                        arrowHead.getPoints().setAll(0.0, 20.0, 30.0, 0.0, 30.0, 40.0);
                        arrowHead.setLayoutX(450);
                        arrowTail.setX(480);
                        arrowForceLabel.setText(frictionForceTextField.getText() + "N");
                        oppSpeed = deceleration * (decTime + decTotalTimeWhenStopped);
                        if (metric.isSelected()) {
                            accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", deceleration) + "m/s²");
                        }
                        if (imperial.isSelected()) {
                            accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", deceleration * 3.28084) + "ft/s²");
                        }
                        distancePerFrame = (speed * (decTime + decTotalTimeWhenStopped)) + (0.5 * deceleration * Math.pow((decTime + decTotalTimeWhenStopped), 2));
                        groundView.setTranslateX(-distancePerFrame);

                        for (Line l : distanceLineArrayList) {
                            l.setTranslateX(-(distancePerFrame));
                        }

                        for (Text t : distanceIndicatorArrayList) {
                            t.setTranslateX(-(distancePerFrame));
                        }

                        for (Line l : impDistanceLineArrayList) {
                            l.setTranslateX(-(distancePerFrame));
                        }

                        for (Text t : impDistanceIndicatorArrayList) {
                            t.setTranslateX(-(distancePerFrame));
                        }
                        decTime += 1/60d;
                    }
                    totalTime += 1/60d;
                    if (metric.isSelected()) {
                        velocityDisplayLabel.setText("Velocity: " + String.format("%.2f", ((speed + oppSpeed) * 3.6)) + "km/h");
                        timeDisplayLabel.setText("Time: " + String.format("%.2f", totalTime + totalTimeWhenStopped) + "s");
                        distanceDisplayLabel.setText("Distance: " + String.format("%.2f", distance + distancePerFrame) + "m");
                    }
                    if (imperial.isSelected()){
                        velocityDisplayLabel.setText("Velocity: " + String.format("%.2f", ((speed + oppSpeed) * 3.6 * 0.621371)) + "mi/h");
                        timeDisplayLabel.setText("Time: " + String.format("%.2f", totalTime + totalTimeWhenStopped) + "s");
                        distanceDisplayLabel.setText("Distance: " + String.format("%.2f", (distance + distancePerFrame)* 1.09361) + "yd");
                    }
                    if ((speed + oppSpeed) < 0) {
                        timer.stop();
                        if (imperial.isSelected()) {
                            velocityDisplayLabel.setText("Velocity: 0.00mi/h");
                            accelerationDisplayLabel.setText("Acceleration: 0.00ft/s²");
                        }
                        if (metric.isSelected()) {
                            velocityDisplayLabel.setText("Velocity: 0.00km/h");
                            accelerationDisplayLabel.setText("Acceleration: 0.00m/s²");

                        }
                        resetBtn.setDisable(false);
                        arrowForceLabel.setText("0N");
                    }
                    prevDeltaX = distance;
                    prevSpeed = speed;
                    prevOppSpeed = oppSpeed;
                }
            }
        };

        timer2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                groundView.setTranslateX(0);
                objectView.setTranslateX(0);
                for (Line l : distanceLineArrayList) {
                    l.setTranslateX(0);
                }

                for (Text t : distanceIndicatorArrayList) {
                    t.setTranslateX(0);
                }
                for (Line l : impDistanceLineArrayList) {
                    l.setTranslateX(0);
                }

                for (Text t : impDistanceIndicatorArrayList) {
                    t.setTranslateX(0);
                }
            }
        };
        forceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                forceTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        massTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                massTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        frictionForceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                frictionForceTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        startBtn.setOnAction(actionEvent -> {
            counter++;
            if (counter % 2 == 1) {
                startBtn.setText("Pause");
                timer.start();
                timer2.stop();
                accTime = 0;
                decTime = 0;
                totalTime = 0;
            }
            if (counter % 2 == 0) {
                startBtn.setText("Play");
                timer.stop();
                timeWhenStopped = totalTime;
                accTimeWhenStopped = accTime;
                decTimeWhenStopped = decTime;
                totalTimeWhenStopped += timeWhenStopped;
                accTotalTimeWhenStopped += accTimeWhenStopped;
                decTotalTimeWhenStopped += decTimeWhenStopped;
            }
            String forceInput = forceTextField.getText();
            String massInput = massTextField.getText();
            String frictionForceInput = frictionForceTextField.getText();
            if (forceInput.trim().isEmpty()) {
                forceTextField.setStyle("-fx-background-color: red");
                warning.setText("Input fields cannot be empty");
                warning.setVisible(true);
                if(!(massInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        massTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        massTextField.setStyle("-fx-background-color: white");
                    }
                }
                if(!(frictionForceInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        frictionForceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        frictionForceTextField.setStyle("-fx-background-color: white");
                    }
                }
            }
            if (massInput.trim().isEmpty()){
                massTextField.setStyle("-fx-background-color: red");
                warning.setText("Input fields cannot be empty");
                warning.setVisible(true);
                if(!(forceInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        forceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        forceTextField.setStyle("-fx-background-color: white");
                    }
                }
                if(!(frictionForceInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        frictionForceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        frictionForceTextField.setStyle("-fx-background-color: white");
                    }
                }
            }
            if(frictionForceInput.trim().isEmpty()){
                frictionForceTextField.setStyle("-fx-background-color: red");
                warning.setText("Input fields cannot be empty");
                warning.setVisible(true);
                if(!(massInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        massTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        massTextField.setStyle("-fx-background-color: white");
                    }
                }
                if(!(forceInput.trim().isEmpty())){
                    if(darkMode.isSelected()){
                        forceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        forceTextField.setStyle("-fx-background-color: white");
                    }
                }
            }
            else {
                boolean a = ((Double.parseDouble(forceInput) < 100) || (Double.parseDouble(forceInput) > 1000));
                boolean b = ((Double.parseDouble(massInput) < 1) || (Double.parseDouble(massInput) > 10));
                boolean c = ((Double.parseDouble(frictionForceInput) < 100) || (Double.parseDouble(frictionForceInput) > 1000));
                if (a && b && c) {
                    forceTextField.setStyle("-fx-background-color: red");
                    massTextField.setStyle("-fx-background-color: red");
                    frictionForceTextField.setStyle("-fx-background-color: red");
                    warning.setText("Invalid input");
                    warning.setVisible(true);
                }
                else if (a && b) {
                    forceTextField.setStyle("-fx-background-color: red");
                    massTextField.setStyle("-fx-background-color: red");
                    if(darkMode.isSelected()){
                        frictionForceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        frictionForceTextField.setStyle("-fx-background-color: white");
                    }
                    warning.setText("Invalid input");
                    warning.setVisible(true);
                }
                else if (a && c) {
                    forceTextField.setStyle("-fx-background-color: red");
                    if(darkMode.isSelected()){
                        massTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        massTextField.setStyle("-fx-background-color: white");
                    }
                    frictionForceTextField.setStyle("-fx-background-color: red ");
                    warning.setText("Invalid input");
                    warning.setVisible(true);
                }
                else if (b && c) {
                    if(darkMode.isSelected()){
                        forceTextField.setStyle("-fx-background-color: #303030");

                    }else{
                        forceTextField.setStyle("-fx-background-color: white");
                    }
                    massTextField.setStyle("-fx-background-color: red");
                    frictionForceTextField.setStyle("-fx-background-color: red ");
                    warning.setText("Invalid input");
                    warning.setVisible(true);
                }
                else if (a) {
                    forceTextField.setStyle("-fx-background-color: red");
                    if(darkMode.isSelected()){
                        massTextField.setStyle("-fx-background-color: #303030");
                        frictionForceTextField.setStyle("-fx-background-color: #303030 ");

                    }else{
                        massTextField.setStyle("-fx-background-color: white");
                        frictionForceTextField.setStyle("-fx-background-color: white ");
                    }
                    warning.setText("Force must be between 100-1,000N");
                    warning.setVisible(true);
                }
                else if (b) {
                    massTextField.setStyle("-fx-background-color: red");
                    if(darkMode.isSelected()){
                        forceTextField.setStyle("-fx-background-color: #303030");
                        frictionForceTextField.setStyle("-fx-background-color: #303030 ");

                    }else{
                        forceTextField.setStyle("-fx-background-color: white");
                        frictionForceTextField.setStyle("-fx-background-color: white ");
                    }
                    warning.setText("Mass must be between 1-10kg");
                    warning.setVisible(true);
                }
                else if (c) {
                    frictionForceTextField.setStyle("-fx-background-color: red ");
                    if(darkMode.isSelected()){
                        forceTextField.setStyle("-fx-background-color: #303030");
                        massTextField.setStyle("-fx-background-color: #303030 ");

                    }else{
                        forceTextField.setStyle("-fx-background-color: white");
                        massTextField.setStyle("-fx-background-color: white");
                    }
                    warning.setText("Friction force must be between 100-1,000N");
                    warning.setVisible(true);
                }
                else {
                    if(darkMode.isSelected()) {
                        forceTextField.setStyle("-fx-background-color: #303030");
                        massTextField.setStyle("-fx-background-color: #303030");
                        frictionForceTextField.setStyle("-fx-background-color: #303030");
                    }else{
                        forceTextField.setStyle("-fx-background-color: White");
                        massTextField.setStyle("-fx-background-color: White");
                        frictionForceTextField.setStyle("-fx-background-color: White");
                    }
                    resetBtn.setDisable(true);
                    warning.setVisible(false);
                    double force = Double.parseDouble(forceInput);
                    double mass = Double.parseDouble(massInput);
                    double frictionForce = Double.parseDouble(frictionForceInput);
                    acceleration = force / mass;
                    deceleration = -frictionForce / mass;
                    timer.start();
                }
            }
        });

        resetBtn.setOnAction(actionEvent -> {
            startBtn.setText("Play");
            warning.setVisible(false);
            if(darkMode.isSelected()) {
                forceTextField.setStyle("-fx-background-color: #303030");
                massTextField.setStyle("-fx-background-color: #303030");
                frictionForceTextField.setStyle("-fx-background-color: #303030");
            }else{
                forceTextField.setStyle("-fx-background-color: White");
                massTextField.setStyle("-fx-background-color: White");
                frictionForceTextField.setStyle("-fx-background-color: White");
            }
            arrowHead.setFill(Color.LIGHTGREEN);
            arrowTail.setFill(Color.LIGHTGREEN);
            arrowHead.getPoints().setAll(0.0, 0.0, 30.0, 20.0, 0.0, 40.0);
            arrowHead.setLayoutX(500);
            arrowTail.setX(450);
            arrowForceLabel.setText("0N");
            forceTextField.setText("");
            frictionForceTextField.setText("");
            massTextField.setText("");
            acceleration = 0.0;
            time = 0.0;
            decTime = 0.0;
            deceleration = 0.0;
            speed = 0.0;
            prevSpeed = 0.0;
            distance = 0.0;
            prevDeltaX = 0.0;
            oppSpeed = 0.0;
            prevOppSpeed = 0.0;
            accDistance = 0.0;
            timeWhenStopped = 0.0;
            counter = 0;
            accTime = 0;
            totalTime = 0;
            distancePerFrame = 0;
            totalTimeWhenStopped = 0;
            accTimeWhenStopped = 0;
            decTimeWhenStopped = 0;
            accTotalTimeWhenStopped = 0;
            decTotalTimeWhenStopped = 0;
            if (imperial.isSelected()) {
                accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", acceleration) + "ft/s²");
                timeDisplayLabel.setText("Time: " + String.format("%.2f", time) + "s                   ");
                distanceDisplayLabel.setText("Distance: " + String.format("%.2f", distance) + "yd           ");
            }
            if (metric.isSelected()) {
                accelerationDisplayLabel.setText("Acceleration: " + String.format("%.2f", acceleration) + "m/s²");
                timeDisplayLabel.setText("Time: " + String.format("%.2f", time) + "s                   ");
                distanceDisplayLabel.setText("Distance: " + String.format("%.2f", distance) + "m           ");
            }
            timer2.start();
        });

        imperial.setOnAction(actionEvent -> {
            metric.setSelected(false);
            imperial.setSelected(true);
            velocityDisplayLabel.setText("Velocity: 0.00mi/h        ");
            accelerationDisplayLabel.setText("Acceleration: 0.00ft/s²");
            distanceDisplayLabel.setText("Distance: 0.00yd           ");

            for (Line l : distanceLineArrayList) {
                l.setVisible(false);
            }

            for (Text t : distanceIndicatorArrayList) {
                t.setVisible(false);
            }
            for (Line l : impDistanceLineArrayList) {
                l.setVisible(true);
            }

            for (Text t : impDistanceIndicatorArrayList) {
                t.setVisible(true);
            }
        });

        metric.setOnAction(actionEvent -> {
            metric.setSelected(true);
            imperial.setSelected(false);
            velocityDisplayLabel.setText("Velocity: 0.00km/h       ");
            accelerationDisplayLabel.setText("Acceleration: 0.00m/s²");
            distanceDisplayLabel.setText("Distance: 0.00m           ");
            for (Line l : distanceLineArrayList) {
                l.setVisible(true);
            }

            for (Text t : distanceIndicatorArrayList) {
                t.setVisible(true);
            }
            for (Line l : impDistanceLineArrayList) {
                l.setVisible(false);
            }

            for (Text t : impDistanceIndicatorArrayList) {
                t.setVisible(false);
            }
        });

        lightMode.setOnAction(actionEvent -> {
            root.setStyle("-fx-background-color: #ffffff");
            menuBtn.setStyle("-fx-background-color: #ffffff;-fx-border-color: black;");
            menuView.setImage(menuIcon);
            forceTextField.setStyle("-fx-background-color: white ;-fx-text-fill: black;");
            massTextField.setStyle("-fx-background-color: white ;-fx-text-fill: black;");
            frictionForceTextField.setStyle("-fx-background-color: white ;-fx-text-fill: black;");
            massLabel.setStyle("-fx-text-fill: black");
            forceLabel.setStyle("-fx-text-fill: black");
            surfaceLabel.setStyle("-fx-text-fill: black");
            objectView.setImage(dayCarImg);
            groundView.setImage(dayImg);
            separator.setStyle("-fx-stroke:White;");
            darkMode.setSelected(false);


        });
        darkMode.setOnAction(actionEvent -> {
            root.setStyle("-fx-background-color: #121212");
            menuBtn.setStyle("-fx-background-color: #121212;-fx-border-color: white;");
            menuView.setImage(nMenu);
            forceTextField.setStyle("-fx-background-color: #303030 ;-fx-text-fill: white;");
            frictionForceTextField.setStyle("-fx-background-color: #303030 ;-fx-text-fill: white;");
            massTextField.setStyle("-fx-background-color: #303030 ;-fx-text-fill: white;");
            massLabel.setStyle("-fx-text-fill: white");
            forceLabel.setStyle("-fx-text-fill: white");
            surfaceLabel.setStyle("-fx-text-fill: white");
            separator.setStyle("-fx-stroke:#01014b;");
            groundView.setImage(duskImg);
            lightMode.setSelected(false);

        });
        exitProgram.setOnAction(actionEvent -> exit());
        godObject.setOnAction(actionEvent -> {
            objectView.setX(70);
            objectView.setY(0);
            objectView.setImage(godImg);
            carObject.setSelected(false);
            skateboardObject.setSelected(false);
        });
        carObject.setOnAction(actionEvent -> {
            objectView.setY(10);
            objectView.setX(50);
            objectView.setImage(dayCarImg);

            skateboardObject.setSelected(false);
            godObject.setSelected(false);

        });
        skateboardObject.setOnAction(actionEvent -> {
            objectView.setX(120);
            objectView.setImage(skateboardImg);
            carObject.setSelected(false);
            godObject.setSelected(false);
            objectView.setY(15);
        });
        HBox root2 = new HBox(5);
        Pane textPane = new Pane();
        Scene s2 = new Scene(root2, 1000, 500);
        Stage newtonHelpStage = new Stage();
        newtonHelpStage.setScene(s2);
        newtonHelpStage.setTitle("Help");
        newtonHelpStage.setResizable(false);
        Image helpImage = new Image(helpPic);
        ImageView helpImageView = new ImageView();
        helpImageView.setImage(helpImage);
        helpImageView.setFitHeight(500);

        Text text = new Text("""
                Newton's Second law states:
                Time rate of change of the momentum of a body is equal
                in both magnitude and direction to the force imposed on it.
                The momentum of a body is equal to:
                the product of its mass and its velocity. F=m*a
                
                Furthermore, the First law dictates that:
                If a body is at rest/moving at a constant speed in a straight line
                it will remain at rest or keep moving in a straight line
                at constant speed unless it is acted upon by a force
                The Friction force is what allows the object to come to a stop.
                
                The user can change the force, mass and friction force based on
                their desired values, omitting decimal values and
                within the accepted values within this simulator, by typing them
                into the respective text bubbles.
                
                Upon the clicking the start button, the simulation will apply the
                aforementioned laws and slide the image across the screen forgo any
                unaccepted values.
                
                1% of error is to be expected as Java does a poor job tracking time.
                
                
                Accepted values:
                F: Force (100 to 1000 N)
                m: Mass  (1 to 10 kg)
                Friction Force: (100 to 1000N)
                
                """);
        textPane.getChildren().add(text);
        text.setY(40);
        text.setStyle("-fx-font-family: Verdana");
        root2.getChildren().addAll(helpImageView, textPane);

        menuHelp.setOnAction(actionEvent -> {
            if (!newtonHelpStage.isShowing()) {
                newtonHelpStage.show();
            }
        });
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                startBtn.fire();
            }
        });
        homeBtn.setOnAction(actionEvent -> {
            primaryStage.close();
            Main m = new Main();
            m.start(new Stage());

        });

        showForcesOn.setOnAction(actionEvent -> {
            showForcesOff.setSelected(false);
            arrowTail.setVisible(true);
            arrowHead.setVisible(true);
            arrowHead.setFill(Color.LIGHTGREEN);
            arrowTail.setFill(Color.LIGHTGREEN);
            arrowForceLabel.setVisible(true);
        });
        showForcesOff.setOnAction(actionEvent -> {
            showForcesOn.setSelected(false);
            arrowTail.setVisible(false);
            arrowHead.setVisible(false);
            arrowForceLabel.setVisible(false);
        });
    }
}

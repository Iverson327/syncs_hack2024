package tamagotchi;

import tamagotchi.GameScene;
import tamagotchi.GameEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;

class GameWindow {
    public int width = 414;
    public int height = 896;

    // Home stuff (main menu)
    public Canvas          homeCanvas;
    public Pane            homePane;
    public Scene           homeScene;
    public GraphicsContext homegc;
    public int fadeInElapse = 0;
    public int fadeInEnd = 300;
    Image homeImage = new Image("file:src/main/res/homeBG.png");
    Image homeTxt1 = new Image("file:src/main/res/title_top.png");
    Image homeTxt2 = new Image("file:src/main/res/title_bot.png");
    Rectangle homeBgRect = new Rectangle(width, height);
    Rectangle homeTxtRect1 = new Rectangle(width/2 - 150, height/2 - 230, 200, 100);
    Rectangle homeTxtRect2 = new Rectangle(width/2 - 60, height/2 - 230 + 70, 200, 100);

    // Game stuff
    GameScene gameScene;

    // Credits
    Credits creditsScene;

    // home: 0,
    // game: 1,
    // credits: 2
    int activeScene = 0;

    public Stage activeStage;

    GameWindow(Stage primaryStage, GameEngine engine) {
        activeStage = primaryStage;
        gameScene = new GameScene(this, engine);
        creditsScene = new Credits(this);

        // Set up the home screen
        homeCanvas = new Canvas(width, height);
        homePane = new Pane();
        homeScene = new Scene(homePane, width, height);
        homePane.getChildren().add(homeCanvas);
        homegc = homeCanvas.getGraphicsContext2D();
        homeBgRect.setFill(new ImagePattern(homeImage));
        homeBgRect.setViewOrder(1000);
        homeTxtRect1.setFill(new ImagePattern(homeTxt1));
        homeTxtRect2.setFill(new ImagePattern(homeTxt2));

        // Create a button on the home screen
        Button startButton = new Button("Play");
        startButton.setStyle(
            "-fx-background-color: #FFDE59; " + // Background color
            "-fx-text-fill: black; " +          // Text color
            "-fx-border-color: white; " +     // Border color
            "-fx-border-width: 2px; " +         // Border width
            "-fx-font-family: Impact; " +        // Font family
            "-fx-font-size: 40px; " +           // Font size
            "-fx-font-weight: bold;"            // Font weight
        );
        startButton.setOnMousePressed(e -> {
            startButton.setStyle(
                "-fx-background-color: #F7C600; " + // Darker background color on press
                "-fx-text-fill: black; " +          // Text color remains the same
                "-fx-border-color: white; " +     // Border color remains the same
                "-fx-border-width: 2px; " +         // Border width remains the same
                "-fx-font-family: Impact; " +        // Font family remains the same
                "-fx-font-size: 40px; " +           // Font size remains the same
                "-fx-font-weight: bold;"            // Font weight remains the same
            );
        });

        startButton.setOnMouseReleased(e -> {
            startButton.setStyle(
                "-fx-background-color: #FFDE59; " + // Original background color when released
                "-fx-text-fill: black; " +          // Text color remains the same
                "-fx-border-color: white; " +     // Border color remains the same
                "-fx-border-width: 2px; " +         // Border width remains the same
                "-fx-font-family: Impact; " +        // Font family remains the same
                "-fx-font-size: 40px; " +           // Font size remains the same
                "-fx-font-weight: bold;"            // Font weight remains the same
            );
        });
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);
        startButton.setLayoutX(width / 2 - 100); // Positioning the button (centered horizontally)
        startButton.setLayoutY(height / 2 + 150); // Positioning the button (below the title)
        homePane.getChildren().add(homeBgRect);
        homePane.getChildren().add(homeTxtRect1);
        homePane.getChildren().add(homeTxtRect2);
        homePane.getChildren().add(startButton);

        // Set the action for the button click
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchScene(gameScene.gameScene, 1); // Switch to the game scene
            }
        });
       
        activeStage.setScene(homeScene);
        // activeStage.setScene(gameScene);
    }

    void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void switchScene(Scene scene, int number) {
        activeScene = number;
        activeStage.setScene(scene);
    }

    public void switchToHome() {
        switchScene(homeScene, 0);
        fadeInElapse = 0;
    }

    public void gotoCredits() {
        switchScene(creditsScene.creditsScene, 2);
    }

    private void draw() {
        switch (activeScene) {
        case 0: {
            homegc.clearRect(0, 0, width, height);

            ++fadeInElapse;
            float opacity = 1.0f - (float)fadeInElapse / (float)fadeInEnd;
            if (opacity < 0) {
                opacity = 0.0f;
            }
            homegc.setFill(Color.rgb(255, 255, 255, opacity));
            homegc.fillRect(0, 0, width, height);
            // Draw the home image as the background
    
            // Set font and alignment for the title
            // homegc.setFill(Color.BLACK);
            // homegc.setTextAlign(TextAlignment.CENTER);
            // homegc.setFont(new Font("Impact", 100));

            // // Draw "Tomo" on the first line
            // homegc.fillText("Tomo", width / 2, height / 2 - 230);

            // // Draw "Gotchi" on the second line
            // homegc.fillText("Gotchi", width / 2, height / 2 - 230 + 100);
            // homegc.setFill(Color.TRANSPARENT);

            break;
        }
        case 1: {
            gameScene.draw();
        }
        case 2: {
            creditsScene.draw();
        }
        }
    }
}

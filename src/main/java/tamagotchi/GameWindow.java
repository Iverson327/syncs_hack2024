package tamagotchi;

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
import java.io.File;

class GameWindow {
    public int width = 414;
    public int height = 896;

    // Home stuff (main menu)
    public Canvas          homeCanvas;
    public Pane            homePane;
    public Scene           homeScene;
    public GraphicsContext homegc;
    Image homeImage = new Image(new File("homeBG.png").toURI().toString(), 960, 960, true, true);

    // Game stuff
    public Canvas          gameCanvas;
    public Pane            gamePane;
    public Scene           gameScene;
    public GraphicsContext gamegc;
    Image gameBgImage = new Image("file:src/main/res/summer_haze.png");
    Rectangle gameBgRect = new Rectangle(width, height);
    // Image gamebgimage = new Image("file:src/main/res/summer_haze.png");
    // ImageView gameBackgroundImage = new ImageView(gamebgimage);

    // home: 0,
    // game: 1,
    int activeScene = 0;

    public Stage activeStage;

    GameWindow(Stage primaryStage) {
        activeStage = primaryStage;

        // Set up the home screen
        homeCanvas = new Canvas(width, height);
        homePane = new Pane();
        homeScene = new Scene(homePane, width, height);
        homePane.getChildren().add(homeCanvas);
        homegc = homeCanvas.getGraphicsContext2D();
        ImageView backgroundImageView = new ImageView(homeImage);
        backgroundImageView.setFitWidth(width);  // Set the width of the ImageView
        backgroundImageView.setFitHeight(height); // Set the height of the ImageView
        backgroundImageView.setPreserveRatio(false); // Allow the ImageView to stretch

        // Add the ImageView to the Pane
        homePane.getChildren().add(backgroundImageView);

        // Create a button on the home screen
        Button startButton = new Button("Play");
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);
        startButton.setLayoutX(width / 2 - 100); // Positioning the button (centered horizontally)
        startButton.setLayoutY(height / 2 + 150); // Positioning the button (below the title)
        homePane.getChildren().add(startButton);

        // Set up the game screen
        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBackgroundImage);
        gamegc = gamecanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage));
        
        activeStage.setScene(homeScene);
        activeStage.setScene(gameScene);
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

    private void draw() {
        switch (activeScene) {
        case 0: {
            homegc.clearRect(0, 0, width, height);
        }
        case 1: {
            int boxHeight = 500;
            // Clear
            gamegc.clearRect(0, 0, width, height);
            gamegc.setTextAlign(TextAlignment.LEFT);

            // Draw pixel art
            // gamegc.drawImage(-width / 15, -height / 15, 16 * 13, 9 * 13);
            gamegc.drawRect(gameBgRect);

            // Draw background of base
            gamegc.setFill(Paint.valueOf("#db6c39"));
            gamegc.setFont(new Font(20));
            gamegc.fillRect(0, boxHeight, width, height - boxHeight);

            // Draw text
            int happinessHeight = boxHeight + height / 10;
            int healthHeight = boxHeight + 2 * height / 10;
            gamegc.fillText("Happiness", width / 20, happinessHeight);
            gamegc.fillText("Health", width / 20, healthHeight);

            // Draw happiness bar
            gamegc.setFill(Paint.valueOf("#b85223"));
            gamegc.fillRect(width - 300,
                            happinessHeight,
                            40 * 5 + 4 * 5 + 4,
                            40 + 4 * 2);
            gamegc.setFill(Paint.valueOf("#fac011"));
            for (int i = 0, i < 5; ++i) {
                gamegc.fillRect(300 + 4,
                                happinessHeight + 4,
                                40,
                                40);
            }

            // Draw health bar
            gamegc.setFill(Paint.valueOf("#b85223"));
            gamegc.fillRect(width - 300,
                            healthHeight,
                            40 * 5 + 4 * 5 + 4,
                            40 + 4 * 2);
            gamegc.setFill(Paint.valueOf("#e3380e"));
            for (int i = 0, i < 5; ++i) {
                gamegc.fillRect(300 + 4,
                                healthHeight + 4,
                                40,
                                40);
            }
        }
        }


        // model.tick();

        // gc.clearRect(0, 0, model.getWidth(), model.getHeight());

        // gc.setFill(Paint.valueOf("BLACK"));
        // gc.setTextAlign(TextAlignment.LEFT);
        // gc.setFont(new Font(10));
        // gc.fillText(model.getBalls().get(0).observer.update(), 0, 10);
        // gc.fillText(model.getBalls().get(1).observer.update(), 0, 90);
        // gc.fillText(model.getBalls().get(2).observer.update(), 0, 170);

        // for (Ball ball: model.getBalls()) {
        //     gc.setFill(ball.getColour());
        //     gc.fillOval(ball.getxPos() - ball.getRadius(),
        //                 ball.getyPos() - ball.getRadius(),
        //                 ball.getRadius() * 2,
        //                 ball.getRadius() * 2);
        // }
    }
}

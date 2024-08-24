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
        gamegc = gameCanvas.getGraphicsContext2D();

        // Set the initial scene to home
        activeStage.setScene(homeScene);

        // Set the action for the button click
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchScene(gameScene, 1); // Switch to the game scene
            }
        });
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
                // Clear the home screen
                homegc.clearRect(0, 0, width, height);
    
                // Draw the home image as the background
                homegc.drawImage(homeImage, 0, 0, width, height); // Scale the image to fit the canvas
    
                // Set font and alignment for the title
                homegc.setFill(Color.BLACK);
                homegc.setTextAlign(TextAlignment.CENTER);
                homegc.setFont(new Font("Impact", 100));
    
                // Draw "Tomo" on the first line
                homegc.fillText("Tomo", width / 2, height / 2 - 230);
    
                // Draw "Gotchi" on the second line
                homegc.fillText("Gotchi", width / 2, height / 2 - 230 + 100);
    
                break;
            }
            case 1: {
                // Clear the game screen and start the game logic
                gamegc.clearRect(0, 0, width, height);
                // Add game drawing logic here
                break;
            }
        }
    }
}

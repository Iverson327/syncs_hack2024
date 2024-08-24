package tamagotchi;

import tamagotchi.GameScene;
import tamagotchi.GameEngine;
import tamagotchi.GameWindow;
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

class Credits {
    GameWindow ctx;

    public Canvas          creditsCanvas;
    public Pane            creditsPane;
    public Scene           creditsScene;
    public GraphicsContext creditsgc;
    
    Credits(GameWindow window) {
        ctx = window;
        int width = ctx.width;
        int height = ctx.height;

        creditsCanvas = new Canvas(width, height);
        creditsPane = new Pane();
        creditsScene = new Scene(creditsPane, width, height);
        creditsPane.getChildren().add(creditsCanvas);
        creditsgc = creditsCanvas.getGraphicsContext2D();

        Button homeButton = new Button("Home");
        homeButton.setOnMouseReleased(e -> {
            ctx.switchToHome();
        });

        homeButton.setPrefWidth(200);
        homeButton.setPrefHeight(50);
        homeButton.setLayoutX(width / 2 - 100); // Positioning the button (centered horizontally)
        homeButton.setLayoutY(height / 2 + 150); // Positioning the button (below the title)
        creditsPane.getChildren().add(homeButton);
    }

    public void draw() {
        int width = ctx.width;
        int height = ctx.height;
        creditsgc.clearRect(0, 0, width, height);
        creditsgc.setFont(new Font("Comic Sans MS", 20));
        creditsgc.setFill(Paint.valueOf("#000000"));
        creditsgc.fillText("Credits:\nChun C- Idea boi, Learnt React\nand then we didn't use it\nIverson S- Code, Java lord\nJohnathan M- Code,\nGraphics/Animator"
        + "\nminimoss- Home backgrounds\nNexon, Maplestory- Various\nbackgrounds\nVecteezy- Beach background"
        + "\nSYNCS- Food (No. 1 Contributor)\n\n"
        + "Pop Shop Packs- CATS!\nFind them on Itch.io"
        + "\nMore credits on our\nGithub repository",
        5, 50);
    }
}

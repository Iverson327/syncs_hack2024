package tamagotchi;

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

class GameScene {
    public GameWindow ctx;
    
    // Game stuff
    public Canvas          gameCanvas;
    public Pane            gamePane;
    public Scene           gameScene;
    public GraphicsContext gamegc;
    int width;
    int height;
    Image gameBgImage = new Image("file:src/main/res/summer_haze.png");
    Rectangle gameBgRect;

    GameScene(GameWindow window) {
        ctx = window;
        width = ctx.width;
        height = ctx.height;
        gameBgRect = new Rectangle(-width / 8, -height / 8,
                                   16 * 13, 9 * 13);

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBackgroundImage);
        gamegc = gamecanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage));
        gameBgRect.setViewOrder(1000);
    }

    public void draw() {
        int boxHeight = 460;

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
        int sd = 20; // square dimension
        int leftOffset = 100;
        gamegc.setFill(Paint.valueOf("#b85223"));
        gamegc.fillRect(width - leftOffset,
                        happinessHeight,
                        sd * 5 + (sd / 10) * 5 + sd,
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#fac011"));
        for (int i = 0; i < 5; ++i) {
            gamegc.fillRect(leftOffset + (sd / 10) + (sd * i) + (sd / 10),
                            happinessHeight + (sd / 10),
                            sd,
                            sd);
        }

        // Draw health bar
        gamegc.setFill(Paint.valueOf("#b85223"));
        gamegc.fillRect(width - leftOffset,
                        healthHeight,
                        sd * 5 + (sd / 10) * 5 + sd,
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#e3380e"));
        for (int i = 0; i < 5; ++i) {
            gamegc.fillRect(leftOffset + (sd / 10) + (sd * i) + (sd / 10),
                            healthHeight + (sd / 10),
                            sd,
                            sd);
        }
    }
}

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

    public int happiness = 2;
    public int health = 5;
    
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
        gameBgRect = new Rectangle(-width / 4, 0,
                                   16 * 52, 9 * 52);

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBgRect);
        gamegc = gameCanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage));
        gameBgRect.setViewOrder(1000);
    }

    public void draw() {
        int boxHeight = 460;

        // Clear
        gamegc.clearRect(0, 0, width, height);
        gamegc.setTextAlign(TextAlignment.LEFT);
        gamegc.setFont(new Font(20));

        // Draw background of base
        gamegc.setFill(Paint.valueOf("#800000"));
        gamegc.fillRect(0, boxHeight, width, height - boxHeight);
        gamegc.setFill(Paint.valueOf("#982B1C"));
        gamegc.fillRect(20, boxHeight + 20, width - 40, height - boxHeight - 40);

        // Draw text
        int happinessHeight = boxHeight + 50;
        int healthHeight = boxHeight + 100;
        gamegc.fillText("Happiness", width / 20, happinessHeight);
        gamegc.fillText("Health", width / 20, healthHeight);

        // Draw happiness bar
        int sd = 30; // square dimension
        int leftOffset = 200;
        gamegc.setFill(Paint.valueOf("#800000"));
        gamegc.fillRect(leftOffset,
                        happinessHeight,
                        (sd + 2) * 5 + (sd / 10),
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#FABC3F"));
        for (int i = 0; i < happiness; ++i) {
            gamegc.fillRect(leftOffset + (sd / 10) + ((sd + 2) * i),
                            happinessHeight + (sd / 10),
                            sd,
                            sd);
        }

        // Draw health bar
        gamegc.setFill(Paint.valueOf("#800000"));
        gamegc.fillRect(leftOffset,
                        healthHeight,
                        (sd + 2) * 5 + (sd / 10),
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#C7253E"));
        for (int i = 0; i < health; ++i) {
            gamegc.fillRect(leftOffset + (sd / 10) + ((sd + 2) * i),
                            healthHeight + (sd / 10),
                            sd,
                            sd);
        }
    }
}

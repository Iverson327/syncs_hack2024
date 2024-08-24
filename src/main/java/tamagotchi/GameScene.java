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
import tamagotchi.GameEngine;
import java.util.Random;
import java.util.ArrayList;

class GameScene {
    public GameWindow ctx;
    private GameEngine engine;

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
    Image catImage = new Image("file:src/main/res/cat.png");
    Rectangle gameBgRect;
    Rectangle catRect;
    PetSprite pet;

    ArrayList<String> quests = new ArrayList<String>();

    GameScene(GameWindow window, GameEngine engine) {
        quests.add(new String("go to the beach together"));
        quests.add(new String("take a hike together"));
        quests.add(new String("eat out together"));
        quests.add(new String("watch a movie together"));
        quests.add(new String("go shopping at a mall together"));
        quests.add(new String("take a stroll in a park together"));
        quests.add(new String("go to a cafe together"));
        quests.add(new String("take some goofy selfies together"));
        quests.add(new String("go to a library together"));
        quests.add(new String("go to a karaoke together"));

        Random r = new Random();
        int i = r.nextInt(quests.size());
        String quest1 = quests.get(i);
        i = r.nextInt(quests.size());
        String quest2 = quests.get(i);

        ctx = window;
        this.engine = engine;
        width = ctx.width;
        height = ctx.height;
        gameBgRect = new Rectangle(-width / 4, 0,
                                   16 * 52, 9 * 52);

        int floor = 200;
        catRect = new Rectangle(width / 4, floor,
                                   100, 100);

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBgRect);
        gamePane.getChildren().add(catRect);
        gamegc = gameCanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage));
        gameBgRect.setViewOrder(1000);
        catRect.setFill(new ImagePattern(catImage));
        catRect.setViewOrder(100);

        pet = new PetSprite(catRect, width, (int)catRect.getWidth(), floor);
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
        gamegc.setFill(Paint.valueOf("#f2e8c6"));
        gamegc.setFont(new Font("Comic Sans MS", 20));
        gamegc.fillText("Happiness", width / 20 + 50, happinessHeight + 25);
        gamegc.fillText("Health", width / 20 + 50, healthHeight + 25);

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

        pet.process();
        pet.draw();

        // Debug pet sprite target position
        // gamegc.fillRect(pet.targetX, 200, 5, 5);
    }
}

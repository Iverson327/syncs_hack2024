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
// import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;
import tamagotchi.GameEngine;
import java.util.Random;
import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.lang.Math;

class GameScene {
    public GameWindow ctx;
    private GameEngine engine;

    public int happiness = 2;
    public int health = 5;
    private boolean quest_done = false;
    
    // Game stuff
    public Canvas          gameCanvas;
    public Pane            gamePane;
    public Scene           gameScene;
    public GraphicsContext gamegc;
    
    int width;
    int height;
    Image gameBgImage1 = new Image("file:src/main/res/summer_haze.png");
    Image gameBgImage2 = new Image("file:src/main/res/botanists_window.png");
    Image gameBgImage3 = new Image("file:src/main/res/dusty_lilac.png");
    Image gameBgImage4 = new Image("file:src/main/res/blue_dusk.png");
    Image gameBgImage5 = new Image("file:src/main/res/beach_sunset.jpg");
    Image gameBgImage6 = new Image("file:src/main/res/green_forest.jpg");
    Image gameBgImage7 = new Image("file:src/main/res/night_market.jpg");
    Image gameBgImage8 = new Image("file:src/main/res/go_shanghai.jpg");
    Image deathImage = new Image("file:src/main/res/death.png");
    boolean deathPlaying = false;
    int deathFadeTimer = 0;
    int deathFadeEnd = 300;
    Image catImage = new Image("file:src/main/res/cat.png");
    Image heartImage = new Image("file:src/main/res/heart.png");
    Image happinessImage = new Image("file:src/main/res/happiness.png");
    Image burgerImage = new Image("file:src/main/res/burger.png");
    Image backImage = new Image("file:src/main/res/arrow.png");
    Rectangle[] hearts = new Rectangle[5];
    Rectangle[] happinessHearts = new Rectangle[5];
    Rectangle gameBgRect;
    Rectangle catRect;
    PetSprite pet;

    boolean levelingUp = false;
    int levelUpSequenceElapsed = 0;
    int levelUpSequenceEnd = 300;
    int levelUpX = 0;
    int levelUpY = 0;
    Rectangle[] levelUpHearts = new Rectangle[3];

    String quest1;
    String quest2;
    Random r;

    ArrayList<String> quests = new ArrayList<String>();

    GameScene(GameWindow window, GameEngine engine) {
        quests.add(new String("go to the\nbeach together"));
        quests.add(new String("take a hike\ntogether"));
        quests.add(new String("eat out\ntogether"));
        quests.add(new String("watch a movie\ntogether"));
        quests.add(new String("go shopping at\na mall together"));
        quests.add(new String("take a stroll in\nthe park together"));
        quests.add(new String("go to a cafe\ntogether"));
        quests.add(new String("go to an amusement\npark together"));
        quests.add(new String("go to a library\ntogether"));
        quests.add(new String("go to a karaoke\ntogether"));
        r = new Random();
        int i = r.nextInt(quests.size());
        quest1 = quests.get(i);
        i = r.nextInt(quests.size());
        quest2 = quests.get(i);

        ctx = window;
        this.engine = engine;
        width = ctx.width;
        height = ctx.height;
        gameBgRect = new Rectangle(-width / 4, 0,
                                   16 * 52, 9 * 52);

        int floor = 205;
        catRect = new Rectangle(width / 4, floor,
                                   150, 150);

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBgRect);
        gamePane.getChildren().add(catRect);
        gamegc = gameCanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage1));
        gameBgRect.setViewOrder(1000);
        catRect.setFill(new ImagePattern(catImage));
        catRect.setViewOrder(100);

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.C && !quest_done) {
                    quest_done = true; // Mark quests as completed
                    if(health < 5 && health > 0){
                        health++;
                    }else if(health == 5){
                        happiness++;
                        if(happiness > 5){
                            pet.level++;
                            levelingUp = true;
                        }
                    }
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.R) {
                    quest_done = false;
                    int i = r.nextInt(quests.size()); // refresh quests
                    quest1 = quests.get(i);
                    i = r.nextInt(quests.size());
                    quest2 = quests.get(i);
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.M) {
                    if(!quest_done){
                        happiness--;
                    }
                    quest_done = false;
                    int i = r.nextInt(quests.size()); // refresh quests
                    quest1 = quests.get(i);
                    i = r.nextInt(quests.size());
                    quest2 = quests.get(i);
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.D) {
                    happiness--;
                    draw(); // Redraw the scene to reflect changes
                }
                // home
                if (event.getCode() == KeyCode.DIGIT1) {
                    pet.y = floor;
                    gameBgRect.setFill(new ImagePattern(gameBgImage1));
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.DIGIT2) {
                    pet.y = floor;
                    gameBgRect.setFill(new ImagePattern(gameBgImage2));
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.DIGIT3) {
                    pet.y = floor;
                    gameBgRect.setFill(new ImagePattern(gameBgImage3));
                    draw(); // Redraw the scene to reflect changes
                }
                if (event.getCode() == KeyCode.DIGIT4) {
                    pet.y = floor;
                    gameBgRect.setFill(new ImagePattern(gameBgImage4));
                    draw(); // Redraw the scene to reflect changes
                }

                // beach
                if (event.getCode() == KeyCode.DIGIT5) {
                    pet.y = floor;
                    pet.y += 112;
                    gameBgRect.setFill(new ImagePattern(gameBgImage5));
                    draw(); // Redraw the scene to reflect changes
                }
                // trees
                if (event.getCode() == KeyCode.DIGIT6) {
                    pet.y = floor;
                    pet.y += 40;
                    gameBgRect.setFill(new ImagePattern(gameBgImage6));
                    draw(); // Redraw the scene to reflect changes
                }
                // night market
                if (event.getCode() == KeyCode.DIGIT7) {
                    pet.y = floor;
                    pet.y += 80;
                    gameBgRect.setFill(new ImagePattern(gameBgImage7));
                    draw(); // Redraw the scene to reflect changes
                }
                // go shanghai
                if (event.getCode() == KeyCode.DIGIT8) {
                    pet.y = floor;
                    pet.y += 80;
                    gameBgRect.setFill(new ImagePattern(gameBgImage8));
                    draw(); // Redraw the scene to reflect changes
                }

            }
        });

        // Hearts and happiness
        int sd = 30; // square dimension
        int leftOffset = 200;
        for (int p = 0; p < 5; ++p) {
            hearts[p] = new Rectangle(2000, 2000, sd, sd);
            hearts[p].setFill(new ImagePattern(heartImage));
            hearts[p].setViewOrder(0);
            gamePane.getChildren().add(hearts[p]);

            happinessHearts[p] = new Rectangle(2000, 2000, sd, sd);
            happinessHearts[p].setFill(new ImagePattern(happinessImage));
            happinessHearts[p].setViewOrder(0);
            gamePane.getChildren().add(happinessHearts[p]);
        }

        for (int p = 0; p < 3; ++p) {
            levelUpHearts[p] = new Rectangle(2000, 2000, sd - 6 * p, sd - 6 * p);
            levelUpHearts[p].setFill(new ImagePattern(happinessImage));
            levelUpHearts[p].setViewOrder(0);
            gamePane.getChildren().add(levelUpHearts[p]);
        }

        pet = new PetSprite(catRect, width, (int)catRect.getWidth(), floor);
        pet.setOwner("Shikanokonoko");

        //friends/credit
        Button burger = new Button("");
        ImageView burgerImageView = new ImageView(burgerImage);
        burgerImageView.setFitWidth(30);  // Set the width of the image
        burgerImageView.setFitHeight(30); 
        burger.setGraphic(burgerImageView);
        burger.setPrefHeight(30);
        burger.setPrefWidth(30);
        burger.setStyle(
            "-fx-background-color: #800000; "// Background color
        );
        burger.setLayoutX(width - 50); // Positioning the button (centered horizontally)
        burger.setLayoutY(40);
        gamePane.getChildren().add(burger);
        burger.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctx.gotoCredits(); // Switch to the game scene
            }
        });

        //back button
        Button back = new Button("");
        ImageView backImageView = new ImageView(backImage);
        backImageView.setFitWidth(30);  // Set the width of the image
        backImageView.setFitHeight(30); 
        back.setGraphic(backImageView);
        back.setPrefHeight(30);
        back.setPrefWidth(30);
        back.setStyle(
            "-fx-background-color: #800000; "// Background color
        );
        back.setLayoutX(10); // Positioning the button (centered horizontally)
        back.setLayoutY(40);
        gamePane.getChildren().add(back);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ctx.switchToHome(); // Switch to the game scene
                pet.dead = false;
            }
        });
    }

    public void draw() {
        int boxHeight = 460;
        if(happiness < 0){
            happiness = 0;
            health--;
        }
        if(happiness >= 5){
            happiness = 5;
        }
        if(health >= 5){
            health = 5;
        }
        
        // Clear
        gamegc.clearRect(0, 0, width, height);

        // death
        if (deathPlaying) {
            deathFadeTimer++;
            float opacity = (float)deathFadeTimer / (float)(deathFadeEnd * 0.9);
            if (opacity > 1) {
                opacity = 1.0f;
            }

            gamegc.setFill(Color.rgb(0, 0, 0, opacity));
            gamegc.fillRect(0, 0, width, height);

            gamegc.setFont(new Font("Impact", 80));
            gamegc.setFill(Paint.valueOf("#800000"));
            gamegc.fillText("WASTED", width/2, 300);

            health = 0;
            happiness = 0;
            
            if (deathFadeTimer >= deathFadeEnd) {
                deathPlaying = false;
                deathFadeTimer = 0;
                gameBgRect.setFill(new ImagePattern(gameBgImage1));
                happiness = 2;
                health = 5;
                ctx.switchToHome();
                pet.dead = false;
            }
        }

        gamegc.setTextAlign(TextAlignment.LEFT);

        // Draw background of base
        gamegc.setFill(Paint.valueOf("#800000"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#454545"));
        }
        gamegc.fillRect(0, boxHeight, width, height - boxHeight);
        gamegc.setFill(Paint.valueOf("#982B1C"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#777777"));
        }
        gamegc.fillRect(20, boxHeight + 20, width - 40, height - boxHeight - 40);

        // Draw top name bar
        gamegc.setFill(Paint.valueOf("#800000"));
        gamegc.fillRect(0, 0, width, 80);

        // Draw text
        int happinessHeight = boxHeight + 50;
        int healthHeight = boxHeight + 100;
        gamegc.setFill(Paint.valueOf("#f2e8c6"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#454545"));
        }
        gamegc.setFont(new Font("Comic Sans MS", 20));
        gamegc.fillText("Happiness", width / 20 + 50, happinessHeight + 25);
        gamegc.fillText("Health", width / 20 + 50, healthHeight + 25);

        gamegc.setTextAlign(TextAlignment.CENTER);

        gamegc.fillText(pet.owner + " LV." + pet.level + "", width / 2, height / 13);


        gamegc.setFill(Paint.valueOf("#800000"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#454545"));
        }
        gamegc.fillRect(0, 620, width, 30);
        gamegc.setFill(Paint.valueOf("#f2e8c6"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#AAAAAA"));
        }
        gamegc.setFont(new Font("Comic Sans MS", 20));
        gamegc.fillText("Weekly Quests", width/2, 640);
        if(quest_done && health > 0){
            gamegc.setFont(new Font("Comic Sans MS", 50));
            gamegc.fillText("Completed!!!", width/2, 710);
        }else if(health == 0){
            pet.dead = true;
            pet.stare();
            deathPlaying = true;
            gameBgRect.setFill(new ImagePattern(deathImage));

        }else{
            gamegc.fillText(quest1, width/20 * 5, 700);
            gamegc.fillText(quest2, width/20 * 15, 700);
        }

        // Draw happiness bar
        int sd = 30; // square dimension
        int leftOffset = 200;
        gamegc.setFill(Paint.valueOf("#800000"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#454545"));
        }
        gamegc.fillRect(leftOffset,
                        happinessHeight,
                        (sd + 2) * 5 + (sd / 10),
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#FABC3F"));
        for (int i = 0; i < happiness; ++i) {
            happinessHearts[i].setX(leftOffset + (sd / 10) + ((sd + 2) * i));
            happinessHearts[i].setY(happinessHeight + (sd / 10));
            // gamegc.fillRect(leftOffset + (sd / 10) + ((sd + 2) * i),
            //                 happinessHeight + (sd / 10),
            //                 sd,
            //                 sd);
        }
        for (int i = happiness; i < 5; ++i) {
            happinessHearts[i].setX(2000);
            happinessHearts[i].setY(2000);
        }

        // Draw health bar
        gamegc.setFill(Paint.valueOf("#800000"));
        if (deathPlaying) {
            gamegc.setFill(Paint.valueOf("#454545"));
        }
        gamegc.fillRect(leftOffset,
                        healthHeight,
                        (sd + 2) * 5 + (sd / 10),
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#C7253E"));
        for (int i = 0; i < health; ++i) {
            hearts[i].relocate(leftOffset + (sd / 10) + ((sd + 2) * i),
                               healthHeight + (sd / 10));
            // gamegc.fillRect(leftOffset + (sd / 10) + ((sd + 2) * i),
            //                 healthHeight + (sd / 10),
            //                 sd,
            //                 sd);
        }
        for (int i = health; i < 5; ++i) {
            hearts[i].relocate(2000, 2000);
        }

        pet.process();
        pet.draw();

        // Animate level up hearts
        if (levelingUp == true) {
            // Sequence just started
            if (levelUpSequenceElapsed == 0) {
                levelUpX = (int)pet.x;
                levelUpY = (int)pet.y;

                if (pet.direction == 1) {
                    levelUpX += pet.image.getWidth();
                }

                for (int i = 0; i < 3; ++i) {
                    levelUpHearts[i].relocate(2000, 2000);
                }
            }

            levelUpSequenceElapsed++;
            if (levelUpSequenceElapsed < levelUpSequenceEnd) {
                float completion0 = (float)levelUpSequenceElapsed / (float)levelUpSequenceEnd;
                float completion1 = (float)(levelUpSequenceElapsed - 50) / (float)levelUpSequenceEnd;
                float completion2 = (float)(levelUpSequenceElapsed - 100) / (float)levelUpSequenceEnd;
                levelUpHearts[0].relocate(levelUpX + 20 * completion0 * Math.sin(completion0 * 6 + 0),
                                          levelUpY + 40 * completion0 - 40 * completion0 * completion0);
                if (completion1 > 0) {
                    levelUpHearts[1].relocate(levelUpX + 20 * completion0 * Math.cos(completion0 * 4 + 1),
                                              levelUpY + 60 * completion0 - 80 * completion0 * completion0);
                }
                if (completion2 > 0) {
                    levelUpHearts[2].relocate(levelUpX + 20 * completion0 * Math.sin(completion0 * 8 + 2),
                                              levelUpY + 40 * completion0 - 30 * completion0 * completion0);
                }
            }
            else {
                levelingUp = false;
                levelUpSequenceElapsed = 0;
                for (int i = 0; i < 3; ++i) {
                    levelUpHearts[i].relocate(2000, 2000);
                }
            }
        }

        // Debug pet sprite target position
        // gamegc.fillRect(pet.targetX, 200, 5, 5);
    }
}

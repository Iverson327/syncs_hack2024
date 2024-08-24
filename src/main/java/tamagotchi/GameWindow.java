package tamagotchi;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.util.Duration;

class GameWindow {
    public final GraphicsContext gc;
    public width = 414;
    public height = 896;

    // Home stuff (main menu)
    public Canvas          homeCanvas;
    public Pane            homePane;
    public Scene           homeScene;
    public GraphicsContext homegc;

    // Game stuff
    public Canvas          gameCanvas;
    public Pane            gamePane;
    public Scene           gameScene;
    public GraphicsContext gamegc;

    // home: 0,
    // game: 1,
    // etc : 2
    int activeScene = 0;

    public Stage activeStage;

    GameWindow(Stage primaryStage) {
        activeStage = primaryStage;

        homeCanvas = new Canvas(width, height);
        homePane = new Pane();
        homeScene = new Scene(homePane, width, height);
        homePane.getChildren().add(homeCanvas);
        homegc = homecanvas.getGraphicsContext2D();

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamegc = gamecanvas.getGraphicsContext2D();
        
        activeStage.setScene(homeScene);
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

package tamagotchi;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameWindow window = new GameWindow(new BallPit(640, 400, 1.0/60));
        window.run();

        Scene scene;
        Pane pane;

        primaryStage.setTitle("Tomogotchi");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}

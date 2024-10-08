package tamagotchi;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine engine = new GameEngine();
        GameWindow window = new GameWindow(primaryStage, engine);
        window.run();

        primaryStage.setTitle("Tomogotchi");
        // primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}

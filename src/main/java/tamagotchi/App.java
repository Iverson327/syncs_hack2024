package tamagotchi;

import javafx.application.Application;
import javafx.stage.Stage;
// import tamagotchi.engine.GameEngine;
// import tamagotchi.engine.GameWindow;

import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // GameEngine model = GameEngine.getEasyEngine();
        GameWindow window = new GameWindow(model);
        window.run();

        primaryStage.setTitle("Tomogotchi");
        primaryStage.setScene(window.getScene());
        primaryStage.setWidth(414);
        primaryStage.setHeight(896);
        primaryStage.show();

        // window.run();
    }
}

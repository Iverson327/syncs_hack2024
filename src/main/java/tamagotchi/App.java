package tamagotchi;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.Label; 
// import tamagotchi.engine.GameEngine;
// import tamagotchi.engine.GameWindow;

import java.util.Map;

public class App extends Application {

    public void start(Stage s) 
    { 
        // set title for the stage 
        s.setTitle("creating buttons"); 
  
        // create a button 
        Button b = new Button("button"); 
  
        // create a stack pane 
        Pane r = new Pane(); 
  
        // create a label 
        Label l = new Label("button not selected"); 
  
        // action event 
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                l.setText("   button   selected    "); 
            } 
        }; 
  
        // when button is pressed 
        b.setOnAction(event); 
  
        // add button 
        b.setLayoutX(10); // Sets the X co-ordinate
        b.setLayoutY(10);
        l.setLayoutX(10); // Sets the X co-ordinate
        l.setLayoutY(50);
        r.getChildren().add(b); 
        r.getChildren().add(l); 

        // create a scene 
        Scene sc = new Scene(r, 200, 200); 
  
        // set the scene 
        s.setScene(sc); 
  
        s.show(); 
    } 
  
    public static void main(String args[]) 
    { 
        // launch the application 
        launch(args); 
    }
}

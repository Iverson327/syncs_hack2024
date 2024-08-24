// package invaders.engine;

// import java.util.Collection;
// import java.util.List;
// import java.util.ArrayList;

// import invaders.ConfigReader;
// import invaders.entities.EntityViewImpl;
// import invaders.entities.SpaceBackground;
// import javafx.scene.control.Alert;
// import javafx.util.Duration;
// import javafx.scene.text.Text;
// import javafx.scene.text.Font;
// import javafx.scene.paint.Color;
// import invaders.entities.EntityView;
// import invaders.memento.Memento;
// import invaders.memento.Organiser;
// import invaders.observer.ScoreObserver;
// import invaders.rendering.Renderable;
// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.scene.Scene;
// import javafx.scene.layout.Pane;
// import org.json.simple.JSONObject;

// public class GameWindow {
// 	private int width;
//     private int height;
//     private Text time;
//     private Text score;
// 	private Scene scene;
//     private Pane pane;
//     private GameEngine model;
//     private List<EntityView> entityViews =  new ArrayList<EntityView>();
//     private Renderable background;
//     private Organiser organiser = new Organiser();
//     private ScoreObserver observer;

//     private double xViewportOffset = 0.0;
//     private double yViewportOffset = 0.0;
//     // private static final double VIEWPORT_MARGIN = 280.0;

// 	public GameWindow(GameEngine model){
//         this.model = model;
// 		this.width =  model.getGameWidth();
//         this.height = model.getGameHeight();
//         this.observer = new ScoreObserver(model);

//         this.score = new Text(5, 20, "");
//         this.time = new Text(this.width - 55, 20, "");
//         time.setFont(new Font(20));
//         score.setFont(new Font(20));
//         time.setFill(Color.WHITE);
//         score.setFill(Color.WHITE);
//         this.pane = new Pane();
//         this.pane.getChildren().add(time);
//         this.pane.getChildren().add(score);
//         this.scene = new Scene(this.pane, this.width, this.height);
//         this.background = new SpaceBackground(model, pane);

//         KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model, this);

//         this.scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
//         this.scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

//     }

// 	public void run() {
//          Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));
//          timeline.setCycleCount(Timeline.INDEFINITE);
//          timeline.play();
//     }

//     public void resetView(){
//         this.model.getGameObjects().removeAll(this.model.getGameObjects());
//         this.model.getRenderables().removeAll(this.model.getRenderables());
//         this.model.getPendingToAddGameObject().removeAll(this.model.getPendingToAddGameObject());
//         this.model.getPendingToAddRenderable().removeAll(this.model.getPendingToAddRenderable());
//         this.model.getPendingToRemoveGameObject().removeAll(this.model.getPendingToRemoveGameObject());
//         this.model.getPendingToRemoveRenderable().removeAll(this.model.getPendingToRemoveRenderable());
//         for (EntityView entityView : entityViews){
//             entityView.markForDelete();
//         }
//         for (EntityView entityView : entityViews) {
//             if (entityView.isMarkedForDelete()) {
//                 pane.getChildren().remove(entityView.getNode());
//             }
//         }
//         entityViews.removeIf(EntityView::isMarkedForDelete);
//     }


//     private void draw(){
//         model.update();
//         time.setText(model.getRuntime());
//         score.setText(Integer.toString(observer.getScore()));

//         List<Renderable> renderables = model.getRenderables();
//         for (Renderable entity : renderables) {
//             boolean notFound = true;
//             for (EntityView view : entityViews) {
//                 if (view.matchesEntity(entity)) {
//                     notFound = false;
//                     view.update(xViewportOffset, yViewportOffset);
//                     break;
//                 }
//             }
//             if (notFound) {
//                 EntityView entityView = new EntityViewImpl(entity);
//                 entityViews.add(entityView);
//                 pane.getChildren().add(entityView.getNode());
//             }
//         }

//         for (Renderable entity : renderables){
//             if (!entity.isAlive()){
//                 for (EntityView entityView : entityViews){
//                     if (entityView.matchesEntity(entity)){
//                         entityView.markForDelete();
//                     }
//                 }
//             }
//         }

//         for (EntityView entityView : entityViews) {
//             if (entityView.isMarkedForDelete()) {
//                 pane.getChildren().remove(entityView.getNode());
//             }
//         }


//         model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
//         model.getGameObjects().addAll(model.getPendingToAddGameObject());
//         model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
//         model.getRenderables().addAll(model.getPendingToAddRenderable());

//         model.getPendingToAddGameObject().clear();
//         model.getPendingToRemoveGameObject().clear();
//         model.getPendingToAddRenderable().clear();
//         model.getPendingToRemoveRenderable().clear();

//         entityViews.removeIf(EntityView::isMarkedForDelete);

//     }

// 	public Scene getScene() {
//         return scene;
//     }

//     //For the Memento design pattern
//     public void record(){
// 		organiser.save(this.model);
// 	}

// 	public void undo(){
//         if(this.model.ended()){return;}
//         try{
//             Memento memento = organiser.restore();
//             resetView();
//             this.model.setGameObjects(memento.getGameObjects());
//             this.model.setRenderables(memento.getRenderables());
//             this.model.setPendingToAddGameObject(memento.getPendingToAddGameObject());
//             this.model.setPendingToAddRenderable(memento.getPendingToAddRenderable());
//             this.model.setPendingToRemoveGameObject(memento.getPendingToRemoveGameObject());
//             this.model.setPendingToRemoveRenderable(memento.getPendingToRemoveRenderable());
//             this.model.setPlayer(memento.getPlayer());
//             this.model.setTime(memento.getTime());
//             this.model.setScore(memento.getScore());
//         }catch(Exception e){
//             System.out.println("There are no history available");
//         }
// 	}

//     public void setEngine(GameEngine model){
//         if(this.model.equals(model)){
//             return;
//         }

//         try{
//             organiser.restore();
//         }catch(Exception e){
            
//         }

//         for (EntityView entityView : entityViews){
//             entityView.markForDelete();
//         }
//         for (EntityView entityView : entityViews) {
//             if (entityView.isMarkedForDelete()) {
//                 pane.getChildren().remove(entityView.getNode());
//             }
//         }
//         this.model = model;
// 		this.width =  model.getGameWidth();
//         this.height = model.getGameHeight();
//         this.observer = new ScoreObserver(model);
//         while(pane.getChildren().size() > 0){
//             pane.getChildren().remove(0);
//         }
//         pane.setPrefSize (this.width, this.height);
//         this.score = new Text(5, 20, "");
//         this.time = new Text(this.width - 55, 20, "");
//         time.setFont(new Font(20));
//         score.setFont(new Font(20));
//         time.setFill(Color.WHITE);
//         score.setFill(Color.WHITE);
//         pane.getChildren().add(time);
//         pane.getChildren().add(score);
//         this.background = new SpaceBackground(this.model, pane);
//     }

//     public int getWidth(){
//         return this.width;
//     }

//     public int getHeight(){
//         return this.height;
//     }
// }

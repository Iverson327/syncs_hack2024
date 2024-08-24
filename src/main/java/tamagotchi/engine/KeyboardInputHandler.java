// package invaders.engine;

// import javafx.scene.input.KeyCode;
// import javafx.scene.input.KeyEvent;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

// import java.net.URL;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;

// class KeyboardInputHandler {
//     private GameEngine model;
//     private final GameWindow window;
//     private boolean left = false;
//     private boolean right = false;
//     private boolean cheating = false;
//     private Set<KeyCode> pressedKeys = new HashSet<>();

//     private Map<String, MediaPlayer> sounds = new HashMap<>();

//     KeyboardInputHandler(GameEngine model, GameWindow window) {
//         this.model = model;
//         this.window = window;

//         // TODO (longGoneUser): Is there a better place for this code?
//         URL mediaUrl = getClass().getResource("/shoot.wav");
//         String jumpURL = mediaUrl.toExternalForm();

//         Media sound = new Media(jumpURL);
//         MediaPlayer mediaPlayer = new MediaPlayer(sound);
//         sounds.put("shoot", mediaPlayer);
//     }

//     void handlePressed(KeyEvent keyEvent) {
//         if (pressedKeys.contains(keyEvent.getCode())) {
//             return;
//         }
//         pressedKeys.add(keyEvent.getCode());

//         if (keyEvent.getCode().equals(KeyCode.SPACE)) {
//             window.record();
//             if (model.shootPressed()) {
//                 MediaPlayer shoot = sounds.get("shoot");
//                 shoot.stop();
//                 shoot.play();
//             }
//         }

//         if (keyEvent.getCode().equals(KeyCode.LEFT)) {
//             left = true;
//         }
//         if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
//             right = true;
//         }
//         if (keyEvent.getCode().equals(KeyCode.Z)) {
//             window.undo();
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT1)) {
//             this.model = GameEngine.getEasyEngine();
//             this.window.setEngine(this.model);
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
//             this.model = GameEngine.getMediumEngine();
//             this.window.setEngine(this.model);
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT3)) {
//             this.model = GameEngine.getHardEngine();
//             this.window.setEngine(this.model);
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT4) && !cheating) {
//             model.removeAllSlowProjectile();
//             cheating = true;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT5) && !cheating) {
//             model.removeAllFastProjectile();
//             cheating = true;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT6) && !cheating) {
//             model.removeAllSlowEnemy();
//             cheating = true;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT7) && !cheating) {
//             model.removeAllFastEnemy();
//             cheating = true;
//         }

//         if (left) {
//             model.leftPressed();
//         }

//         if(right){
//             model.rightPressed();
//         }
//     }

//     void handleReleased(KeyEvent keyEvent) {
//         pressedKeys.remove(keyEvent.getCode());

//         if (keyEvent.getCode().equals(KeyCode.LEFT)) {
//             left = false;
//             model.leftReleased();
//         }
//         if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
//             model.rightReleased();
//             right = false;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT4)) {
//             cheating = false;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT5)) {
//             cheating = false;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT6)) {
//             cheating = false;
//         }
//         if (keyEvent.getCode().equals(KeyCode.DIGIT7)) {
//             cheating = false;
//         }
//     }
// }

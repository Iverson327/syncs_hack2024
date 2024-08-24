// package invaders.engine;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.HashMap;

// import org.json.simple.JSONObject;

// /**
//  * This class manages the main loop and logic of the game
//  */
public class GameEngine implements Subject{
// 	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
// 	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
// 	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

// 	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
// 	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

// 	private List<Renderable> renderables =  new ArrayList<>();
	
// 	private HashMap<String, Integer> scoretable = new HashMap<String, Integer>();

// 	private ArrayList<Observer> observers = new ArrayList<Observer>();

// 	private Player player;

// 	private boolean left;
// 	private boolean right;
// 	private int gameWidth;
// 	private int gameHeight;
// 	private int timer = 45;
// 	private int score = 0;
// 	private double runtime = 0;
// 	private static GameEngine model1 = null;
// 	private static GameEngine model2 = null;
// 	private static GameEngine model3 = null;
// 	private boolean ended = false;
	
// 	private GameEngine(String config){
// 		// Read the config here
// 		ConfigReader.parse(config);

// 		// Get game width and height
// 		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
// 		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

// 	}

// 	/**
// 	 * Updates the game/simulation
// 	 */
// 	public void update(){
// 		timer+=1;
// 		if(!ended){
// 			runtime+=0.0084;
// 		}


// 		// ensure that renderable foreground objects don't go off-screen
// 		int offset = 1;
// 		for(Renderable ro: renderables){
// 			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
// 				continue;
// 			}
// 			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
// 				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
// 			}

// 			if(ro.getPosition().getX() <= 0) {
// 				ro.getPosition().setX(offset);
// 			}

// 			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
// 				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
// 			}

// 			if(ro.getPosition().getY() <= 0) {
// 				ro.getPosition().setY(offset);
// 			}
// 		}

// 		int count = 0;
// 		for(Renderable ro: renderables){
// 			if(ro.getRenderableObjectName().contains("Enemy")){
// 				if(ro.isAlive()){
// 					count++;
// 				}
// 			}
// 		}
// 		if(count == 0 || !player.isAlive()){
// 			ended = true;
// 		}

// 	}

// 	public String getRuntime(){
// 		int min = (int) Math.floor(runtime / 60);
// 		String minute = Integer.toString(min);
// 		if(minute.length() < 2){
// 			minute = "0" + minute;
// 		}
// 		int sec = (int) Math.floor(runtime % 60);
// 		String second = Integer.toString(sec);
// 		if(second.length() < 2){
// 			second = "0" + second;
// 		}
// 		return minute + ":" + second;
// 	}

// 	public double getTime(){
// 		return this.runtime;
// 	}

// 	public void setTime(double time){
// 		this.runtime = time;
// 	}

}

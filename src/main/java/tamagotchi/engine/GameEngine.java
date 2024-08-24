package invaders.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import invaders.ConfigReader;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.rendering.Renderable;
import invaders.memento.Memento;
import invaders.memento.Organiser;
import invaders.observer.Subject;
import invaders.observer.Observer;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine implements Subject{
	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables =  new ArrayList<>();
	
	private HashMap<String, Integer> scoretable = new HashMap<String, Integer>();

	private ArrayList<Observer> observers = new ArrayList<Observer>();

	private Player player;

	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;
	private int score = 0;
	private double runtime = 0;
	private static GameEngine model1 = null;
	private static GameEngine model2 = null;
	private static GameEngine model3 = null;
	private boolean ended = false;

	public static GameEngine getEasyEngine(){
		if(model1 == null){
			model1 = new GameEngine("src/main/resources/config_easy.json");
			synchronized(GameEngine.class){
				if(model1 == null){
					model1 = new GameEngine("src/main/resources/config_easy.json");
				}
			}
		}
		return model1;
	}
	
	public static GameEngine getMediumEngine(){
		if(model2 == null){
			model2 = new GameEngine("src/main/resources/config_medium.json");
			synchronized(GameEngine.class){
				if(model2 == null){
					model2 = new GameEngine("src/main/resources/config_medium.json");
				}
			}
		}
		return model2;
	}

	public static GameEngine getHardEngine(){
		if(model3 == null){
			model3 = new GameEngine("src/main/resources/config_hard.json");
			synchronized(GameEngine.class){
				if(model3 == null){
					model3 = new GameEngine("src/main/resources/config_hard.json");
				}
			}
		}
		return model3;
	}
	
	private GameEngine(String config){
		// Read the config here
		ConfigReader.parse(config);

		// Get game width and height
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);


		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}


		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			gameObjects.add(enemy);
			renderables.add(enemy);
		}

		scoretable.put("SlowEnemy", 3);
		scoretable.put("FastEnemy", 4);
		scoretable.put("SlowProjectile", 1);
		scoretable.put("FastProjectile", 2);
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		timer+=1;
		if(!ended){
			runtime+=0.0084;
		}

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().contains("Enemy") && renderableB.getRenderableObjectName().contains("Projectile") && !renderableB.getRenderableObjectName().contains("Player"))
						||(renderableA.getRenderableObjectName().contains("Projectile") && !renderableA.getRenderableObjectName().contains("Player") && renderableB.getRenderableObjectName().contains("Enemy"))||
						(renderableA.getRenderableObjectName().contains("Projectile") && !renderableA.getRenderableObjectName().contains("Player") && renderableB.getRenderableObjectName().contains("Projectile") && !renderableB.getRenderableObjectName().contains("Player"))){
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
						if(renderableA.getRenderableObjectName().equals("PlayerProjectile") && !renderableB.getRenderableObjectName().equals("Bunker")){
							this.score += scoretable.get(renderableB.getRenderableObjectName());
							notifyObserver();
						}else if(renderableB.getRenderableObjectName().equals("PlayerProjectile") && !renderableA.getRenderableObjectName().equals("Bunker")){
							this.score += scoretable.get(renderableA.getRenderableObjectName());
							notifyObserver();
						}
					}
				}
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

		int count = 0;
		for(Renderable ro: renderables){
			if(ro.getRenderableObjectName().contains("Enemy")){
				if(ro.isAlive()){
					count++;
				}
			}
		}
		if(count == 0 || !player.isAlive()){
			ended = true;
		}

	}

	public void setRenderables(List<Renderable> l){
		this.renderables = l;
	}

	public void setGameObjects(List<GameObject> l) {
		this.gameObjects = l;
	}
	public void setPendingToAddGameObject(List<GameObject> l) {
		this.pendingToAddGameObject = l;
	}

	public void setPendingToRemoveGameObject(List<GameObject> l) {
		this.pendingToRemoveGameObject = l;
	}

	public void setPendingToAddRenderable(List<Renderable> l) {
		this.pendingToAddRenderable = l;
	}

	public void setPendingToRemoveRenderable(List<Renderable> l) {
		this.pendingToRemoveRenderable = l;
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player){
		this.player = player;
		this.renderables.add(player);
	}

	public String getRuntime(){
		int min = (int) Math.floor(runtime / 60);
		String minute = Integer.toString(min);
		if(minute.length() < 2){
			minute = "0" + minute;
		}
		int sec = (int) Math.floor(runtime % 60);
		String second = Integer.toString(sec);
		if(second.length() < 2){
			second = "0" + second;
		}
		return minute + ":" + second;
	}

	public double getTime(){
		return this.runtime;
	}

	public void setTime(double time){
		this.runtime = time;
	}

	// For the Observer design pattern
	public void setScore(int score){
		this.score = score;
		notifyObserver();
	}
	
    public void register(Observer observer){
        this.observers.add(observer);
    }

    public void unregister(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObserver(){
        for(Observer o: this.observers){
            o.update(this.score);
        }
    }

	//For removing same type of enemy and projectile
	public void removeAllSlowProjectile(){
		if(ended){return;}
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderable = renderables.get(i);
			if(renderable.getRenderableObjectName().equals("SlowProjectile") && renderable.isAlive()){
				renderable.takeDamage(Integer.MAX_VALUE);
				this.score += scoretable.get(renderable.getRenderableObjectName());
			}
		}
		notifyObserver();
	}

	public void removeAllFastProjectile(){
		if(ended){return;}
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderable = renderables.get(i);
			if(renderable.getRenderableObjectName().equals("FastProjectile") && renderable.isAlive()){
				renderable.takeDamage(Integer.MAX_VALUE);
				this.score += scoretable.get(renderable.getRenderableObjectName());
			}
		}
		notifyObserver();
	}

	public void removeAllSlowEnemy(){
		if(ended){return;}
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderable = renderables.get(i);
			if(renderable.getRenderableObjectName().equals("SlowEnemy") && renderable.isAlive()){
				renderable.takeDamage(Integer.MAX_VALUE);
				this.score += scoretable.get(renderable.getRenderableObjectName());
			}
		}
		notifyObserver();
	}

	public void removeAllFastEnemy(){
		if(ended){return;}
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderable = renderables.get(i);
			if(renderable.getRenderableObjectName().equals("FastEnemy") && renderable.isAlive()){
				renderable.takeDamage(Integer.MAX_VALUE);
				this.score += scoretable.get(renderable.getRenderableObjectName());
			}
		}
		notifyObserver();
	}

	public boolean ended(){
		return this.ended;
	}
}

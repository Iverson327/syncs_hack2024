package tamagotchi;

import javafx.scene.image.ImageView;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class PetSprite extends Sprite {
    public static Random random = new Random();
    public int direction = 1; // 1 forward, -1 backwards
    public int sceneWidth = 0;
    public float petSpeed = 24;
    public float velX = 0;
    public boolean dead = false;

    public int framesElapsed = 0;
    public int currentFrame = 0;
    public int ticksPerFrameChange = 60;

    public boolean passed = false; // Pet has passed target
    public float targetX = 0; // x location the pet wants to move to
    public float remainingIdleTime = 0; // How long the pet remain at the
                                        // target location after reaching it
    public ArrayList<Image> right_movement = new ArrayList<>();
    public ArrayList<Image> left_movement = new ArrayList<>();
    public ArrayList<Image> right_special = new ArrayList<>();
    public ArrayList<Image> left_special = new ArrayList<>();

    public PetSprite(Rectangle petImage, int sceneWidth, int petWidth, int floorPosition) {
        this.image = petImage;
        // Randomise position
        
        x = sceneWidth * random.nextFloat() - petWidth;
        y = floorPosition;
        velX = 0;
        this.sceneWidth = sceneWidth - petWidth;

        String res = "file:src/main/res/";
        for (int i = 1; i < 5; ++i) {
            right_movement.add(new Image(res + "right_cat_walk" + Integer.toString(i) + ".png"));
            left_movement.add(new Image(res + "left_cat_walk" + Integer.toString(i) + ".png"));
        }

        right_special.add(new Image(res + "right_cat_crouch.png"));
        right_special.add(new Image(res + "right_cat_stare.png"));
        left_special.add(new Image(res + "left_cat_crouch.png"));
        left_special.add(new Image(res + "left_cat_stare.png"));

        stare();
    }

    public void stare() {
        image.setFill(new ImagePattern(right_special.get(1)));
    }

    public void process() {
        framesElapsed++;
        framesElapsed %= ticksPerFrameChange;

        if (dead == true) {
            return;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(4);
        }
        catch(Exception e) {
            
        }
        // The pet starts walking to the target position
        if (passed == false) {
            velX = petSpeed * direction;
            // animation stuff
            if (framesElapsed == 0) {
                ++currentFrame;
                currentFrame %= 4;
            }

            // animation depending on direction
            if (direction == 1) {
                  image.setFill(new ImagePattern(right_movement.get(currentFrame)));
            }
            else {
                  image.setFill(new ImagePattern(left_movement.get(currentFrame)));
            }
        }
        else {
            velX = 0;
        }

        x += (velX / 120.0);
        // System.out.println("x: " + Float.toString(x));
        // System.out.println("Idle: " + Float.toString(remainingIdleTime));
        // System.out.println("target: " + Integer.toString((int)targetX));
        // System.out.println("passed: " + Boolean.toString(passed));

        // After the pet passes the target position, start a timer
        // Pet is to the right of the target
        if (((targetX < x) && direction == 1) || ((targetX > x) && direction == -1)) {
          passed = true;
          image.setFill(new ImagePattern(right_special.get(random.nextInt(2))));
          targetX = x;
          remainingIdleTime = 1 + 3 * random.nextFloat();
        }

        // The pet is idling
        if (remainingIdleTime > 0 && passed == true) {
            remainingIdleTime -= 1.0 / 120.0; // Assume 120 fps
        }

        // The pet has stopped idling
        if (remainingIdleTime <= 0 && passed == true) {
            // Set a new target in a random direction, probability determined
            // by how far across the screen it is (closer to right means
            // left direction is more likely)
            float percentageAcrossScreen = (float)x / (float)sceneWidth;
            float choice = random.nextFloat();

            // Flip direction
            if ((choice < percentageAcrossScreen) && direction == 1) {
                direction *= -1;
            }
            if ((choice > percentageAcrossScreen) && direction == -1) {
                direction *= -1;
            }

            // Move target, reset passed
            float distance = 0;
            // Distance is right distance, left distance
            if (direction == 1) { distance = sceneWidth - x; }
            else { distance = x; }

            // Move between 25% to 100% in that direction
            targetX =  (float) distance * 0.5f + distance * 0.45f * random.nextFloat();
            passed = false;
        }
    }

}

package tamagotchi;;

import javafx.scene.image.ImageView;
import java.util.Random;
import javafx.scene.image.Image;

public class PetSprite extends Sprite {
    public Random random = new Random();
    public int direction = 1; // 1 forward, -1 backwards
    public int sceneWidth = 0;
    public float petSpeed = 20;

    public boolean passed = false; // Pet has passed target
    public float targetX = 0; // x location the pet wants to move to
    public float remainingIdleTime = 0; // How long the pet remain at the
                                        // target location after reaching it

    public PetSprite(Image petImage, int sceneWidth, int petWidth, int floorPosition) {
        this.image = petImage;
        // Randomise position
        
        x = sceneWidth * random.nextFloat() - petWidth;
        y = floorPosition;
        this.sceneWidth = sceneWidth;
    }

    public void process() {
        // The pet starts walking to the target position
        if (passed == false) {
            // velocity[0] = petSpeed * 1.0 / 120.0 * direction;
        }

        // After the pet passes the target position, start a timer
        // Pet is to the right of the target
        if ((targetX > x) && direction == 1) {
          passed = true;
          remainingIdleTime = 5 * random.nextFloat();
        }
        if ((targetX < x) && direction == -1) {
          passed = true;
          remainingIdleTime = 5 * random.nextFloat();
        }

        // The pet is idling
        if (remainingIdleTime > 0) {
            remainingIdleTime -= 1.0 / 120.0; // Assume 120 fps
        }

        // The pet has stopped idling
        if (remainingIdleTime <= 0) {
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
            targetX =  (float) distance * 0.25f + distance * 0.75f * random.nextFloat();
            passed = false;
        }
    }

}

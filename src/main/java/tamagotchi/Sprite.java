package tamagotchi;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

public class Sprite {
    public float x;
    public float y;
    public Rectangle image;

    public void draw() {
        // Draw the sprite at the position
        image.relocate((int)x, (int)y);

        // System.out.println("relocating to " + Integer.toString((int)x));
    }
}

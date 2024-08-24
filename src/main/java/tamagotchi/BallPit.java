package tamagotchi;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

class BallPit {
    private final double height;
    private final double width;
    private final double g;
    public Label label;
    private long tickCount = 0;

    BallPit(double width, double height, double frameDuration) {
        this.height = height;
        this.width = width;

        g = 1.0 * frameDuration;

        this.label = new Label("");
    }

    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }

    void tick() {
        tickCount++;

    }
}

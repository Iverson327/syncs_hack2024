package tamagotchi;
import javafx.scene.paint.Paint;

import java.util.Random;

public class Ball {
    private double xPos;
    private double yPos;
    private double radius;
    private double xVel;
    private double yVel;
    private Paint colour;
    public Observer observer;


    Ball(double startX, double startY, double startRadius, Paint colour) {
        this.xPos = startX;
        this.yPos = startY;
        this.radius = startRadius;
        this.colour = colour;
        xVel = new Random().nextInt(5);
        yVel = new Random().nextInt(5);
    }

    void tick() {
        xPos += xVel;
        yPos += yVel;
    }

    void setxVel(double xVel) {
        this.xVel = xVel;
    }

    void setyVel(double yVel) {
        this.yVel = yVel;
    }

    double getRadius() {
        return radius;
    }

    double getxPos() {
        return xPos;
    }

    double getyPos() {
        return yPos;
    }

    Paint getColour() {
        return colour;
    }

    double getxVel() {
        return xVel;
    }

    double getyVel() {
        return yVel;
    }

    void setxPos(double xPos) {
        this.xPos = xPos;
    }

    void setyPos(double yPos) {
        this.yPos = yPos;
    }

    void think() {
        // Here is where the strategy should have some effect.
        // You can add parameters to the think method so the ball knows something about its
        // world to make decisions with, or you can inject things upon construction for it to query
        if(this.colour == Paint.valueOf("RED")){
            this.observer = new ConcreteObserver("Red", this.xPos, this.yPos, this.xVel, this.yVel);
        }else if(this.colour == Paint.valueOf("BLUE")){
            this.observer = new ConcreteObserver("Blue", this.xPos, this.yPos, this.xVel, this.yVel);
        }else if(this.colour == Paint.valueOf("BLACK")){
            this.observer = new ConcreteObserver("Black", this.xPos, this.yPos, this.xVel, this.yVel);
        }

    }
}

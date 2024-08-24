package tamagotchi;

public class ConcreteObserver implements Observer{
    private double xVel;
    private double yVel;
    private double xPos;
    private double yPos;
    private String name;

    public ConcreteObserver(String name, double xPos, double yPos, double xVel, double yVel){
        this.xVel = xVel;
        this.yVel = yVel;
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
    }

    @Override
    public String update(){
        return "Name: " + this.name + "\nxPos: " + Double.toString(xPos) + "\nyPos: " + Double.toString(yPos) + "\nxVel: " + Double.toString(xVel) + "\nyVel: " + Double.toString(yVel);
    }
}
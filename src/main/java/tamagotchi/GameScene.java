package tamagotchi;

class GameScene {
    public GameWindow ctx;
    
    // Game stuff
    public Canvas          gameCanvas;
    public Pane            gamePane;
    public Scene           gameScene;
    public GraphicsContext gamegc;
    Image gameBgImage = new Image("file:src/main/res/summer_haze.png");
    Rectangle gameBgRect = new Rectangle(width, height);

    GameScene(GameWindow window) {
        ctx = window;

        gameCanvas = new Canvas(width, height);
        gamePane = new Pane();
        gameScene = new Scene(gamePane, width, height);
        gamePane.getChildren().add(gameCanvas);
        gamePane.getChildren().add(gameBackgroundImage);
        gamegc = gamecanvas.getGraphicsContext2D();
        gameBgRect.setFill(new ImagePattern(gameBgImage));
    }

    public void draw() {
        int boxHeight = 500;
        // Clear
        gamegc.clearRect(0, 0, width, height);
        gamegc.setTextAlign(TextAlignment.LEFT);

        // Draw pixel art
        // gamegc.drawImage(-width / 15, -height / 15, 16 * 13, 9 * 13);
        gamegc.drawRect(gameBgRect);

        // Draw background of base
        gamegc.setFill(Paint.valueOf("#db6c39"));
        gamegc.setFont(new Font(20));
        gamegc.fillRect(0, boxHeight, width, height - boxHeight);

        // Draw text
        int happinessHeight = boxHeight + height / 10;
        int healthHeight = boxHeight + 2 * height / 10;
        gamegc.fillText("Happiness", width / 20, happinessHeight);
        gamegc.fillText("Health", width / 20, healthHeight);

        // Draw happiness bar
        int sd = 20; // square dimension
        gamegc.setFill(Paint.valueOf("#b85223"));
        gamegc.fillRect(width - 300,
                        happinessHeight,
                        sd * 5 + (sd / 10) * 5 + sd,
                        sd + (sd / 10) * 2);
        gamegc.setFill(Paint.valueOf("#fac011"));
        for (int i = 0, i < 5; ++i) {
            gamegc.fillRect(300 + 4,
                            happinessHeight + 4,
                            40,
                            40);
        }

        // Draw health bar
        gamegc.setFill(Paint.valueOf("#b85223"));
        gamegc.fillRect(width - 300,
                        healthHeight,
                        40 * 5 + 4 * 5 + 4,
                        40 + 4 * 2);
        gamegc.setFill(Paint.valueOf("#e3380e"));
        for (int i = 0, i < 5; ++i) {
            gamegc.fillRect(300 + 4,
                            healthHeight + 4,
                            40,
                            40);
        }
    }
}

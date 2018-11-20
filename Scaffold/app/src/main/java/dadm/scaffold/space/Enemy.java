package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Enemy extends Sprite {
    private final GameController mController;
    private final double speed;
    private double speedX;
    private double speedY;
    private double rotationSpeed;
    private Random rnd;
    public Enemy(GameController gameController, GameEngine
            gameEngine) {
        super(gameEngine, R.drawable.enemy);
        rnd = new Random();
        speed = 200d*pixelFactor/1000d;
        mController = gameController;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine)
    {
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        // Check of the sprite goes out of the screen
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            mController.returnToPool(this);
        }

        this.x = positionX;
        this.y = positionY;
        this.width = imageWidth;
        this.height = imageHeight;
        onPostUpdate(gameEngine);
    }
    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle =
                rnd.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speed* Math.sin(angle);
        speedY = speed* Math.cos(angle);
        // Asteroids initialize in the central 50% of the screen
        positionX = rnd.nextInt(gameEngine.width/2)+
                gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -imageHeight;
        rotation = 180;
        rotationSpeed = angle*(180d / Math.PI)/250d;

    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {

        if (otherObject instanceof Bullet || otherObject instanceof  BulletPro) {
            // Remove both from the game (and return them to their pools)
            mController.addScore(100);
            
        }
    }
}

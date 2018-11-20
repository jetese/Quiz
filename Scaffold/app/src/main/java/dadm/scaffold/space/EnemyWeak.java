package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;

public class EnemyWeak extends Enemy{
    private double speedX;
    private double speedY;
    private int life;
    public EnemyWeak(GameController gameController, GameEngine
            gameEngine) {
        super(gameEngine, R.drawable.enemy, gameController);
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

    @Override
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
        life = 1;
    }



    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {

        if (otherObject instanceof Bullet || otherObject instanceof BulletPro) {
            // Remove both from the game (and return them to their pools)
            life --;
            if (life <=0){
                mController.addScore(100);
                removeObject(gameEngine);
            }

        }
    }
}

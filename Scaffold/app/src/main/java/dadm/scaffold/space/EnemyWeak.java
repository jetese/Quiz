package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;

//Enemigo debil con 1 de vida y no dispara
public class EnemyWeak extends Enemy{

    public EnemyWeak(GameController gameController, GameEngine
            gameEngine) {
        super(gameEngine, R.drawable.enemy, gameController);
        lives = 1;
        points = 100;
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
        lives = 1;
    }
}

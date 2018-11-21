package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

//Clase abstracta que define los enemigos (débiles y fuertes que disparan)
public abstract class Enemy extends Sprite {
    protected GameController mController;
    protected double speed;
    protected Random rnd;
    protected int points;
    protected int lives;
    protected double speedX;
    protected double speedY;

    protected Enemy(GameEngine gameEngine, int drawableRes, GameController gameController) {
        super(gameEngine, drawableRes);
        speed =  200d*pixelFactor/1000d;
        mController = gameController;
        rnd = new Random();
        points = 100;
        lives = 1;
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }

    public abstract void init(GameEngine gameEngine);

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {

        if (otherObject instanceof Bullet ) {
            // Remove both from the game (and return them to their pools)
            lives --;
            if (lives <=0){
                mController.addScore(points);
                removeObject(gameEngine);
            }

        }
    }
}

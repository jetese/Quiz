package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public abstract class Enemy extends Sprite {
    protected GameController mController;
    protected double speed;
    protected Random rnd;
    protected Enemy(GameEngine gameEngine, int drawableRes, GameController gameController) {
        super(gameEngine, drawableRes);
        speed =  200d*pixelFactor/1000d;
        mController = gameController;
        rnd = new Random();
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }

    public abstract void init(GameEngine gameEngine);
}

package dadm.scaffold.space;

import android.graphics.Canvas;
import android.graphics.Paint;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Lives extends Sprite {
    private final GameController mController;
    private final int MARGIN = 0;
    
    public Lives(GameController gameController, GameEngine engine, int posx, int posy) {
        super(engine, R.drawable.heart3);
        mController = gameController;

        positionX = posx + MARGIN;
        // They initialize outside of the screen vertically
        positionY = posy + MARGIN;
        rotation = 0;
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    public void init(GameEngine gameEngine,int posx, int posy) {
        // Asteroids initialize in the central 50% of the screen
        positionX = posx + MARGIN;
        // They initialize outside of the screen vertically
        positionY = posy + MARGIN;
        rotation = 0;

    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
    }
    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }
}

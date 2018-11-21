package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

//Balas de los enemigos fuertes
public class EnemyBullet extends Sprite {

    private double speedFactor;

    private EnemyPro parent;

    public EnemyBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY -= speedFactor * elapsedMillis;
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
        this.x = positionX;
        this.y = positionY;
        this.width = imageWidth;
        this.height = imageHeight;
        onPostUpdate(gameEngine);
    }


    public void init(EnemyPro parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY + 2*imageHeight;
        parent = parentPlayer;
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {
    }
}

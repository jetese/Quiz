package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class BulletPro extends Sprite {
    private double speedFactor;
    private double speedX;
    private double speedY;
    private SpaceShipPlayer parent;

    protected BulletPro(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet);
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Enemy a = (Enemy) otherObject;
            a.removeObject(gameEngine);
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBulletPro(this);
        }
        this.x = positionX;
        this.y = positionY+imageHeight/2;
        this.width = imageWidth;
        this.height = imageHeight-70;
        onPostUpdate(gameEngine);
    }

    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int rot) {
        if(rot == 0){
            speedX = speedFactor*0.5;
            speedY = speedFactor*0.5;
            rotation = 315;
        }
        else{
            speedX = speedFactor*-0.5;
            speedY = speedFactor*0.5;
            rotation = 45;
        }

        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        parent.releaseBulletPro(this);
    }
}

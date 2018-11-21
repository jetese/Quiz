package dadm.scaffold.space;

import android.widget.GridLayout;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

//Clase bala para los dos modelos de balas del jugador
//No he añadido una clase abstracta bala para las balas del enemigo junto con las del jugador
//ya que difieren más de lo que las asemeja
public class Bullet extends Sprite {
    protected double speedFactor;
    protected SpaceShipPlayer parent;
    protected double speedX;
    protected double speedY;

    protected Bullet(GameEngine gameEngine, SpaceShipPlayer parentPlayer) {
        super(gameEngine, R.drawable.bulletgood);
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
        parent = parentPlayer;
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        parent.releaseBullet(this);
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
            parent.releaseBullet(this);
        }
        this.x = positionX;
        this.y = positionY;
        this.width = imageWidth;
        this.height = imageHeight;
        onPostUpdate(gameEngine);
    }

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
        }
    }

    //Inicialización para las balas de 45º en 0 y 1, default para las balas que suben recto
    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int rot) {
        switch (rot) {
            case 0:
                speedX = speedFactor * 0.5;
                speedY = speedFactor * 0.5;
                rotation = 315;
                break;
            case 1:
                speedX = speedFactor * -0.5;
                speedY = speedFactor * 0.5;
                rotation = 45;
                break;
            default:
                speedX = 0;
                speedY = speedFactor;
                break;
        }

        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        //parent = parentPlayer;
    }
}

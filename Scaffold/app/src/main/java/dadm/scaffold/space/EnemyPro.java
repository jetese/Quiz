package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class EnemyPro extends Sprite {
    private final GameController mController;
    private final double speed;
    private double speedX;
    private double speedY;
    private Random rnd;
    private int life;
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 500;
    List<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    private long timeSinceLastFire;
    public EnemyPro(GameController gameController, GameEngine
            gameEngine) {
        super(gameEngine, R.drawable.enemypro);
        rnd = new Random();
        speed = 200d*pixelFactor/1000d;
        mController = gameController;
        //initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new EnemyBullet(gameEngine));
        }
    }
    private EnemyBullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine)
    {
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        normalFiring(elapsedMillis,gameEngine);
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
        initBulletPool(gameEngine);
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
        life = 2;

    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        mController.returnToPool(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {

        if (otherObject instanceof Bullet ) {
            // Remove both from the game (and return them to their pools)
            Bullet b = (Bullet) otherObject;
            b.removeObject(gameEngine);
            life --;
            if (life <=0){
                mController.addScore(300);
                removeObject(gameEngine);
            }

        }
        else if(otherObject instanceof  BulletPro){
            BulletPro b = (BulletPro) otherObject;
            b.removeObject(gameEngine);
            life --;
            if (life <=0){
                mController.addScore(300);
                removeObject(gameEngine);
            }
        }
    }

    private void normalFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            EnemyBullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }
}

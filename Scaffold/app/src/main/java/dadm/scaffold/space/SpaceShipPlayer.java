package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final int INITIAL_BULLETPRO_POOL_AMOUNT = 12;
    private static final long TIME_BETWEEN_BULLETS = 500;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<BulletPro> bulletPros = new ArrayList<BulletPro>();
    private long timeSinceLastFire;
    private long timeSinceLastFirePro;
    private GameController gameController;
    private int maxX;
    private int maxY;
    private double speedFactor;
    private int ship;


    public SpaceShipPlayer(GameEngine gameEngine, GameController gameCont, int d){

        super(gameEngine, d);
        ship = d;
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        startGame();
        gameController = gameCont;
        initBulletPool(gameEngine);
        initBulleProtPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private void initBulleProtPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLETPRO_POOL_AMOUNT; i++) {
            bulletPros.add(new BulletPro(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    private BulletPro getBulletPro(){
        if(bulletPros.isEmpty()){
            return null;
        }
        return bulletPros.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    void releaseBulletPro(BulletPro bullet) {
        bulletPros.add(bullet);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        normalFiring(elapsedMillis,gameEngine);
        checkFiring(elapsedMillis, gameEngine);


        this.x = positionX;
        this.y = positionY;
        this.width = imageWidth;
        this.height = imageHeight;
        onPostUpdate(gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }

    }

    private void normalFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
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

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiring && timeSinceLastFirePro > TIME_BETWEEN_BULLETS) {
            BulletPro bullet = getBulletPro();
            if (bullet == null) {
                return;
            }
            BulletPro bullet2 = getBulletPro();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY,0);
            bullet2.init(this, positionX + imageWidth/2, positionY,1);
            gameEngine.addGameObject(bullet);
            gameEngine.addGameObject(bullet2);
            timeSinceLastFirePro = 0;
        }
        else {
            timeSinceLastFirePro += elapsedMillis;
        }
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        gameEngine.addGameObject(new SpaceShipPlayer(gameEngine,gameController,ship));
    }

    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {
        if (otherObject instanceof Enemy ) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Enemy a = (Enemy) otherObject;
            a.removeObject(gameEngine);
            gameController.decreaseLives(gameEngine);
        }
        else if (otherObject instanceof EnemyPro){
            removeObject(gameEngine);
            EnemyPro b = (EnemyPro) otherObject;
            b.removeObject(gameEngine);
            gameController.decreaseLives(gameEngine);
        }
        else if (otherObject instanceof EnemyBullet){
            removeObject(gameEngine);
            EnemyBullet c = (EnemyBullet) otherObject;
            c.removeObject(gameEngine);
            gameController.decreaseLives(gameEngine);
        }
    }
}

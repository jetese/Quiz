package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameController;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

//Enemigo fuerte que dispara
public class EnemyPro extends Enemy {

    //Constantes para balas
    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 500;

    //Lista de balas
    List<EnemyBullet> bullets = new ArrayList<EnemyBullet>();

    //Tiempo entre balas
    private long timeSinceLastFire;

    public EnemyPro(GameController gameController, GameEngine
            gameEngine) {
        super(gameEngine, R.drawable.enemypro,gameController);
        //Inicializamos las vidas del enemigo duro y los puntos
        lives = 2;
        points = 300;
    }

    //Inicialización de las balas de los enemigos
    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new EnemyBullet(gameEngine));
        }
    }

    //Obtener balas
    private EnemyBullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    //Añadir balas
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

        //Disparo de enemigo
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

    @Override //Inicialización del enemigo
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
        lives = 2;
    }

    // Disparo
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

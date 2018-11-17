package dadm.scaffold.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.space.Asteroid;

public class GameController extends GameObject{
    protected int currentMillis;
    protected int enemiesSpawned;

    private List<Asteroid> asteroidPool = new ArrayList<>();
    private final long TIME_BETWEEN_ENEMIES = 500;

    public GameController(GameEngine engine){
        currentMillis = 0;
        enemiesSpawned = 20;
        for (int i=0; i<enemiesSpawned; i++) {
            asteroidPool.add(new Asteroid(this,engine ));
        }
    }
    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;
        long waveTimestamp = enemiesSpawned*TIME_BETWEEN_ENEMIES;
        if (currentMillis > waveTimestamp) {
// Spawn a new enemy
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    public void returnToPool(Asteroid a){
        asteroidPool.add(a);
    }
}

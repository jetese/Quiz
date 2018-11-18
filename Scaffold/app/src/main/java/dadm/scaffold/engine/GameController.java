package dadm.scaffold.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.space.Asteroid;

public class GameController extends GameObject{
    protected int currentMillis;
    protected int enemiesSpawned;
    protected int nLives = 3;
    private final int MARGIN = 100;

    private List<Asteroid> asteroidPool = new ArrayList<>();
    private Lives[] lives = new Lives[nLives];
    private final long TIME_BETWEEN_ENEMIES = 500;

    public GameController(GameEngine engine){
        currentMillis = 0;
        enemiesSpawned = 20;
        for (int i=0; i<enemiesSpawned; i++) {
            asteroidPool.add(new Asteroid(this,engine ));
        }
        for(int i=0; i<lives.length; i++){
            lives[i] = new Lives(this,engine,i*MARGIN,i);
            engine.addGameObject(lives[i]);
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

    public void decreaseLives(GameEngine engine){
        nLives--;
        if(nLives>=0){
            engine.removeGameObject(lives[nLives]);
        }
        else{
                engine.finishGame(0);
        }
    }
}

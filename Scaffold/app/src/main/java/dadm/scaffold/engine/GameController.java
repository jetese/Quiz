package dadm.scaffold.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyPro;

public class GameController extends GameObject{
    protected int currentMillis;
    protected int enemiesSpawned;
    protected int enemiesProSpawned;
    protected int nLives = 3;
    protected int points;
    private final int MARGIN = 100;

    private Score score;
    private List<Enemy> enemyPool = new ArrayList<>();
    private List<EnemyPro> enemyProPool = new ArrayList<>();
    private Lives[] lives = new Lives[nLives];
    private final long TIME_BETWEEN_ENEMIES = 500;
    private final long TIME_BETWEEN_ENEMIES_PRO = 2500;

    public GameController(GameEngine engine){
        currentMillis = 0;
        enemiesSpawned = 10;
        enemiesProSpawned = 5;
        points = 0;
        score = new Score(engine);
        engine.addGameObject(score);
        for (int i=0; i<enemiesSpawned; i++) {
            enemyPool.add(new Enemy(this,engine ));
        }
        for (int i=0; i<enemiesProSpawned; i++) {
            enemyProPool.add(new EnemyPro(this,engine ));
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
            Enemy a = enemyPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
        }
        waveTimestamp = enemiesProSpawned * TIME_BETWEEN_ENEMIES_PRO;
        if(currentMillis > waveTimestamp){
            EnemyPro b = enemyProPool.remove(0);
            b.init(gameEngine);
            gameEngine.addGameObject(b);
            enemiesProSpawned++;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    public void returnToPool(Enemy a){
        enemyPool.add(a);
    }
    public void returnToPool(EnemyPro a){
        enemyProPool.add(a);
    }

    public void decreaseLives(GameEngine engine){
        nLives--;
        if(nLives>0){
            engine.removeGameObject(lives[nLives]);
        }
        else{
                engine.finishGame(points);
        }
    }

    public void addScore(int p){
        points += p;
        score.updatePoints(points);
    }
}

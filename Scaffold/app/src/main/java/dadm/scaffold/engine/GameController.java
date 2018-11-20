package dadm.scaffold.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyPro;
import dadm.scaffold.space.EnemyWeak;

public class GameController extends GameObject{
    protected int currentMillis;
    protected int enemiesSpawned;
    protected int nLives = 3;
    protected int points;
    private final int MARGIN = 100;
    private final int POINTS_TO_FINISH = 5000;

    private WinMessage winmessage;

    private Score score;
    private List<Enemy> enemyPool = new ArrayList<>();
    private Lives[] lives = new Lives[nLives];
    private final long TIME_BETWEEN_ENEMIES = 500;

    public GameController(GameEngine engine){
        winmessage = new WinMessage(engine);
        currentMillis = 0;
        enemiesSpawned = 15;
        points = 0;
        score = new Score(engine);
        engine.addGameObject(score);
        for (int i=0; i<enemiesSpawned; i++) {
            if(i%4!=0){
                enemyPool.add(new EnemyWeak(this,engine ));
            }
            else{
                enemyPool.add(new EnemyPro(this,engine ));
            }
            enemyPool.get(i).init(engine);
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
        if(points >= POINTS_TO_FINISH){
            gameEngine.addGameObject(winmessage);
            gameEngine.winGame(points);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    public void returnToPool(Enemy a){
        enemyPool.add(a);
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

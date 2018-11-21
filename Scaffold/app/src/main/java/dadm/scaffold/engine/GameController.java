package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.space.Lives;
import dadm.scaffold.space.ParallaxBackground;
import dadm.scaffold.space.Score;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyPro;
import dadm.scaffold.space.EnemyWeak;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.WinMessage;

//Clase que controla los objetos interactuables del juego
public class GameController extends GameObject{
    protected int currentMillis;
    protected int enemiesSpawned;

    protected int nLives = 3;   //Número de vidas del jugador
    protected int points;       //Puntos del jugador en la partida

    private final int MARGIN = 100;     //Margen entre los sprites de vidas
    private final int POINTS_TO_FINISH = 5000;  //Número de puntos para finalizar la partida
    private final long TIME_BETWEEN_ENEMIES = 500; //Tiempo entre enemigos

    private WinMessage winmessage;  //Mensaje de ganar la partida
    private ParallaxBackground background;  //Background con movimiento
    private Score score;                    //Texto de Score de la partida
    private SpaceShipPlayer player;         //Nave del jugador
    private List<Enemy> enemyPool = new ArrayList<>();  //Lista de enemigos
    private Lives[] lives = new Lives[nLives];  //Lista de sprites de vidas


    public GameController(GameEngine engine, int shipDrawable, int backGroundVel, int BackgroundDrawable, Typeface font){
        //Inicialización de variables
        currentMillis = 0;
        enemiesSpawned = 15;
        points = 0;

        //Inicialización de gameobjects del juego
        winmessage = new WinMessage(engine,font);
        background = new ParallaxBackground(engine,backGroundVel,BackgroundDrawable);
        player = new SpaceShipPlayer(engine,this,shipDrawable);
        score = new Score(engine,font);

        //Addición de los objetos al engine
        engine.addGameObject(background);
        engine.addGameObject(player);
        engine.addGameObject(score);

        //Inicializamos la lista de enemigos
        for (int i=0; i<enemiesSpawned; i++) {
            if(i%4!=0){
                enemyPool.add(new EnemyWeak(this,engine ));
            }
            else{
                enemyPool.add(new EnemyPro(this,engine ));
            }
            enemyPool.get(i).init(engine);
        }

        //Inicializamos la lista de viidas
        for(int i=0; i<lives.length; i++){
            lives[i] = new Lives(
                    this,engine,i*MARGIN,i);
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

        //Si llegamos a los puntos de fin de partida
        if(points >= POINTS_TO_FINISH){
            //Mostramos mensaje de ganar
            gameEngine.addGameObject(winmessage);
            //Pasamos a finalizas la partida
            gameEngine.winGame(points);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    public void returnToPool(Enemy a){
        enemyPool.add(a);
    }

    //Función que decrementa las vidas y acaba la partida si son 0
    public void decreaseLives(GameEngine engine){
        nLives--;
        if(nLives>0){
            engine.removeGameObject(lives[nLives]);
        }
        else{
                engine.finishGame(points);
        }
    }

    //Función que añade el score a la cantidad actual y actualiza el Sprite
    public void addScore(int p){
        points += p;
        score.updatePoints(points);
    }
}

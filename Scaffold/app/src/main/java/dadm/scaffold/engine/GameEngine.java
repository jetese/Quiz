package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Bullet;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyPro;
import dadm.scaffold.space.SpaceShipPlayer;

public class GameEngine {

    //Listas de gameObjects, y de Objetos colisionables
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<ScreenGameObject> collisionableObjects = new ArrayList<>();


    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    //Puntuación de la partida
    private int punt;

    public boolean pause;

    public int width;
    public int height;
    public double pixelFactor;

    private Activity mainActivity;

    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;
        this.pause = true;

    }

    //Función que comprueba si dos objetos colisionables están colisionando
    private void checkCollisions() {
        int numObjects = collisionableObjects.size();
        for (int i = 0; i < numObjects; i++) {
            ScreenGameObject objectA = collisionableObjects.get(i);
            for (int j = i + 1; j < numObjects; j++) {
                ScreenGameObject objectB = collisionableObjects.get(j);
                if (objectA.checkCollision(objectB)) {
                    objectA.onCollision(this, objectB);
                    objectB.onCollision(this, objectA);
                }
            }
        }
    }


    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();
    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            //Dado que la nave la inicializamos ahora en el GameController tenemos que
            //Añadir la primera vez a la lista de colisionables
            if(gameObject instanceof SpaceShipPlayer){
                collisionableObjects.add((ScreenGameObject) gameObject);
            }
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).onUpdate(elapsedMillis, this);
        }

        //Comprobamos las colisiones en cada update
        checkCollisions();

        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                GameObject objectToRemove = objectsToRemove.remove(0);
                gameObjects.remove(objectToRemove);

                //Eliminamos los objetos colisionables a la lista
                if(objectToRemove instanceof ScreenGameObject)
                collisionableObjects.remove(objectToRemove);
            }
            while (!objectsToAdd.isEmpty()) {
                GameObject gameObjecToAdd = objectsToAdd.remove(0);
                gameObjects.add(gameObjecToAdd);

                //Añadimos los objetos colisionables de la lista
                if(gameObjecToAdd instanceof ScreenGameObject)
                collisionableObjects.add((ScreenGameObject) gameObjecToAdd);
            }
        }
    }

    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }

    //Función para finalizar la partida cuando ganas o te matan 3 veces
    public void finishGame(int points){
        pause = false;
        ((ScaffoldActivity)mainActivity).finishGame(points);

    }

    //Función que permite que podamos ver el texto de You Win durante tres segundos
    //antes de pasar al fin de partida
    public void winGame(int points){
        stopGame();
        punt = points;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        finishGame(punt);
                    }
                },
                3000
        );

    }
}

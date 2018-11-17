package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.input.InputController;
import dadm.scaffold.space.Asteroid;
import dadm.scaffold.space.SpaceShipPlayer;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<ScreenGameObject> collisionableObjects = new ArrayList<>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;
    private int lives;

    public int width;
    public int height;
    public double pixelFactor;

    private Activity mainActivity;

    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;
        lives = 3;

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;


    }

    private void checkCollisions() {
        int numObjects = collisionableObjects.size();
        for (int i = 0; i < numObjects; i++) {
            ScreenGameObject objectA = collisionableObjects.get(i);
            for (int j = i + 1; j < numObjects; j++) {
                ScreenGameObject objectB = collisionableObjects.get(j);
                if (objectA.checkCollision(objectB)) {
                    System.out.println(objectA+" collide with "+objectB);
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
        checkCollisions();
        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                //gameObjects.remove(objectsToRemove.remove(0));
                GameObject objectToRemove = objectsToRemove.remove(0);
                gameObjects.remove(objectToRemove);
                collisionableObjects.remove(objectToRemove);
            }
            while (!objectsToAdd.isEmpty()) {
                //gameObjects.add(objectsToAdd.remove(0));
                GameObject gameObjecToAdd = objectsToAdd.remove(0);
                gameObjects.add(gameObjecToAdd);
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

    public void addCollisionable(GameObject object){
        collisionableObjects.add((ScreenGameObject)object);
    }
    public void removeLive(){
        if(lives > 0){
            lives--;
        }
        else{
            System.out.println("Gameover-------------------");
        }
    }
}

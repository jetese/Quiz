package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class ScreenGameObject extends GameObject{

    protected double x;
    protected double y;

    protected int height;
    protected int width;

    public Rect boundingRect = new Rect(-1, -1, -1, -1);

    public abstract void onCollision(GameEngine gameEngine, ScreenGameObject otherObject);

    public boolean checkCollision(ScreenGameObject otherObject) {
        return checkRectangularCollision(otherObject);
    }

    //public void onCollision(GameEngine gameEngine, ScreenGameObject sgo) {
    //}

    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) x,
                (int) y,
                (int) x + width,
                (int) y + height);
    }

    private boolean checkRectangularCollision(ScreenGameObject other) {
        return Rect.intersects(boundingRect, other.boundingRect);
    }
}

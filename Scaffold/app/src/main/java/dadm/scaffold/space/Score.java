package dadm.scaffold.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

//Clase del texto del score
public class Score extends GameObject {
    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private Typeface plain;

    private String points = "Points: 0";

    public Score(GameEngine gameEngine, Typeface font) {
        plain = font;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (50 * gameEngine.pixelFactor);
        textWidth = (float) (100 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {

        paint.setColor(Color.WHITE);
        paint.setTypeface(plain);;
        canvas.drawText(points, canvas.getWidth()/2, textHeight/2 + 20, paint);
    }

    public void updatePoints(int p){
        points = "Points: "+p;
    }
}

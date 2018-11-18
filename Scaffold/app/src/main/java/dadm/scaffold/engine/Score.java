package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Score extends GameObject{
    private final float textWidth;
    private final float textHeight;

    private Paint paint;


    private String points = "Points: 0";

    public Score(GameEngine gameEngine) {
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
        canvas.drawText(points, canvas.getWidth()/2, textHeight/2 + 20, paint);
    }

    public void updatePoints(int p){
        points = "Points: "+p;
    }
}

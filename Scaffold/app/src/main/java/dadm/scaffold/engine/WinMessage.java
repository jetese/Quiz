package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class WinMessage extends GameObject{
    private final float textWidth;
    private final float textHeight;

    private Paint paint;


    private String points = "Points: 0";

    public WinMessage(GameEngine gameEngine) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (100 * gameEngine.pixelFactor);
        textWidth = (float) (200 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {

        paint.setColor(Color.YELLOW);
        canvas.drawText("YOU WIN!", canvas.getWidth()/2, canvas.getHeight()/2, paint);
    }
}

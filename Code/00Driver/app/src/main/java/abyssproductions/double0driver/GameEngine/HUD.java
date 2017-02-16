package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Mandip Sangha on 2/15/2017.
 */

public class HUD {
    private Rect healthBar;
    private Rect fireButtonDim;
    private Rect switchButtonDim;
    private int score;
    private Paint paint;

    public HUD(){
        score = 0;
        healthBar = new Rect(0,50,800,100);
        fireButtonDim = new Rect(0,0,300,100);
        fireButtonDim.offset(100,1500);
        switchButtonDim = new Rect(0,0,300,100);
        switchButtonDim.offset(600,1500);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
    }

    public void draw(Canvas canvas){
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + score,0,40,paint);
        paint.setColor(Color.RED);
        canvas.drawRect(healthBar,paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(fireButtonDim,paint);
        canvas.drawRect(switchButtonDim,paint);
    }

    public void setHealthLevels(int curHealth, int maxHealth){
        healthBar.right = (curHealth/maxHealth)*800;
    }

    public void updateScore(){
        score++;
    }

    public boolean buttonPressed(int button, float x, float y){
        if(button == 0 && fireButtonDim.contains((int)x,(int)y))return true;
        else if(button == 1 && switchButtonDim.contains((int)x,(int)y))return true;
        return false;
    }
}

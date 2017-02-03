package abyssproductions.double0driver.GameObjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import abyssproductions.double0driver.R;


/**
 * Created by Mandip Sangha on 1/31/2017.
 */

public class GameObject {
    //  PURPOSE:    Holds the object's left, top, right, bottom coordinates
    private RectF myDimensions;
    //  PURPOSE:    Holds the object's image style and color information
    private Paint myPaint;
    //  PURPOSE:    Holds the object's current frame image location
    private Rect myCurFrameLoc;
    //  PURPOSE:    Holds the object's current frame image number
    private int myCurFrameNum;
    //  PURPOSE:    Holds the object's images
    protected Bitmap myImage;
    //  PURPOSE:    Holds the object's movement velocity
    protected Point myVelocity;
    //  PURPOSE:    Hold the object’s current animate state
    protected AnimateState myCurAniState;
    //  PURPOSE:    The different states for the animation
    public enum AnimateState{Normal,Destroyed}

    /*  PURPOSE:    Constructor for the Game Object that set the default value for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public GameObject(/*Resources myImageResources*/){
        myDimensions = new RectF(0,0,0,0);
        myCurFrameLoc = new Rect(0,0,0,0);
        myVelocity = new Point(0,0);
        myPaint = new Paint();
        myCurFrameNum = 0;
        myCurAniState = AnimateState.Normal;
        //myImage = BitmapFactory.decodeResource(myImageResources,R.mipmap.ic_launcher);

    }

    /*  PURPOSE:    Draws the game object's image to the screen
        INPUT:      canvas              - Pointer to the surface screen's canvas
        OUTPUT:     NONE
    */
    public void draw(Canvas canvas){
        canvas.drawBitmap(myImage,myCurFrameLoc,myDimensions,myPaint);
    }

    /*  PURPOSE:    Updates the game object's logic
        INPUT:      NONE
        OUTPUT:     NONE
    */
    public void update(){
        animate();
    }

    /*  PURPOSE:    Set's the game object's current position and size
        INPUT:      newDimension        - The new position and size to set
        OUTPUT:     NONE
    */
    public void setMyDimensions(RectF newDimension){
        myDimensions.set(newDimension);
    }

    /*  PURPOSE:    Return's the game object's left, top, right, bottom coordinates
        INPUT:      NONE
        OUTPUT:     A Rect object with the data
    */
    public RectF getDimensions(){
        return myDimensions;
    }

    /*  PURPOSE:    Move's the game object vertically by the amount given
        INPUT:      moveBy              - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveVertical(float moveBy){
        myDimensions.offset(0,moveBy);
    }

    /*  PURPOSE:    Move's the game object horizontally by the amount given
        INPUT:      moveBy              - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveHorizontal(float moveBy){
        myDimensions.offset(moveBy,0);
    }

    /*  PURPOSE:    Runs the animation for the game object
                        (NOTE: Image Frame must be in right order of Normal state row 0 and
                        Destroyed state row 1 and only four frames per row and follow all guidelines
                        for image frames)
        INPUT:      NONE
        OUTPUT:     NONE
    */
    protected void animate(){
        switch (myCurAniState){
            case Normal:
                myCurFrameLoc.set(50*myCurFrameNum,0,50*(myCurFrameNum+1),50);
                break;
            case Destroyed:
                myCurFrameLoc.set(50*myCurFrameNum,50,50*(myCurFrameNum+1),100);
                break;
        }
        myCurFrameNum++;
        if(myCurFrameNum==4)myCurFrameNum=0;
    }
}

package abyssproductions.double0driver.GameObjects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;


/**
 * Created by Mandip Sangha on 1/31/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class GameObject {

    //  PURPOSE:    Hold the Width and Height values for the game object
    private int myWidth, myHeight;
    //  PURPOSE:    Holds the object's left, top, right, bottom coordinates
    private RectF myDimensions;
    //  PURPOSE:    Holds the object's image style and color information
    private Paint myPaint;
    //  PURPOSE:    Holds the object's current frame image location
    private Rect myCurFrameLoc;
    //  PURPOSE:    Holds the object's current frame image number
    private int myCurFrameNum;
    //  PURPOSE:    Holds the object's images
    private Bitmap myImage;
    //  PURPOSE:    Holds the object's movement velocity
    protected Point myVelocity;
    //  PURPOSE:    Hold the object’s current animate state
    protected AnimateState myCurAniState;
    //  PURPOSE:    The different states for the animation
    public enum AnimateState{Normal,Destroyed}

    /*  PURPOSE:    Constructor for the Game Object that take as input the image reference, width, and height
        INPUT:      int, int, int       -Image Reference, Image Width, Image Height
        OUTPUT:     NONE
     */
    public GameObject(int imageReference, int width, int height){
        myWidth = width;
        myHeight = height;
        myDimensions = new RectF(0,0,0,0);
        myCurFrameLoc = new Rect(0,0,50,50);
        myVelocity = new Point(0,0);
        myPaint = new Paint();
        myCurFrameNum = 0;
        myCurAniState = AnimateState.Normal;
        setMyImage( BitmapFactory.decodeResource(GameGlobals.getInstance().getImageResources(),
                imageReference));

    }

    /*  PURPOSE:    Constructor for the Game Object that set the default value for the object
    INPUT:      NONE
    OUTPUT:     NONE
    */
    public GameObject () {
        this(R.mipmap.ic_launcher, 50, 50);
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

    /*  PURPOSE:    Set's the game object's image and  proper scaling
        INPUT:      image               - The image to set myImage too
        OUTPUT:     NONE
    */
    public void setMyImage(Bitmap image){
        Matrix tempMatrix = new Matrix();
        tempMatrix.setRectToRect(new RectF(0, 0, image.getWidth(), image.getHeight()),
                new RectF(0, 0, 200, 100), Matrix.ScaleToFit.CENTER);
        this.myImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
                tempMatrix, true);
    }

    /*  PURPOSE:    Set's the game object's image and  proper scaling
        INPUT:      image               - The image to set myImage too
                    row                 - The number of row in image frame
                    column              - The number of column in image frame
        OUTPUT:     NONE
    */
    public void setMyImage(Bitmap image, int row, int column){
        Matrix tempMatrix = new Matrix();
        tempMatrix.setRectToRect(new RectF(0, 0, image.getWidth(), image.getHeight()),
                new RectF(0, 0, 50*row, 50*column), Matrix.ScaleToFit.CENTER);
        this.myImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
                tempMatrix, true);
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

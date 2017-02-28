package abyssproductions.double0driver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;


/**
 * Created by Mandip Sangha on 1/31/2017.
 * Lasted Edited by Mandip Sangha on 2/26/17
 */

public class GameObject {

    //  PURPOSE:    Hold the image sheets Width and Height values for the game object
    private int myImageWidth, myImageHeight;
    //  PURPOSE:    Hold the number of row and column in the image sheet
    private int myRow, myColumn;
    //  PURPOSE:    Holds the object's left, top, right, bottom coordinates
    private RectF myDimensions;
    //  PURPOSE:    Holds the object's collision bounds
    private Rect myCollisionBox;
    //  PURPOSE:    Holds the object's image style and color information
    private Paint myPaint;
    //  PURPOSE:    Holds the object's current frame image location
    private Rect myCurFrameLoc;
    //  PURPOSE:    Holds the object's current frame image number
    private int myCurFrameNum;
    //  PURPOSE:    Holds the object's images
    private Bitmap myImage;
    //  PURPOSE:    Holds whether to flip the image or not
    private boolean flipped;
    //  PURPOSE:    Holds the object's movement velocity
    protected Point myVelocity;
    //  PURPOSE:    Hold the object’s current animate state
    protected int myCurAniState;

    /*  PURPOSE:    Constructor for the Game Object that take as input the image reference, width, and height
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    imageSheetRow       - The number of rows in the image sheet
                    imageSheetColumn    - The number of columns in the image sheet
        OUTPUT:     NONE
     */
    public GameObject(Bitmap image, int imageWidth, int imageHeight, int imageSheetRow,
                      int imageSheetColumn){
        myImageWidth = imageWidth;
        myImageHeight = imageHeight;
        myRow = imageSheetRow;
        myColumn = imageSheetColumn;
        myDimensions = new RectF(0,0,imageWidth,imageHeight);
        myCollisionBox = new Rect(0,0,imageWidth,imageHeight);
        myCurFrameLoc = new Rect(0,0,imageWidth,imageHeight);
        myVelocity = new Point(0,0);
        myPaint = new Paint();
        myCurFrameNum = 0;
        myCurAniState = R.integer.NormalAnimateState;
        flipped = false;
        myImage = image;

    }

    /*  PURPOSE:    Constructor for the Game Object that take as input the image reference, width, and height
        INPUT:      image               - The image of the object
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public GameObject(Bitmap image, int imageWidth, int imageHeight){
        this(image,imageWidth,imageHeight,4,2);
    }

    /*  PURPOSE:    Draws the game object's image to the screen
        INPUT:      canvas              - Pointer to the surface screen's canvas
        OUTPUT:     NONE
    */
    public void draw(Canvas canvas){
        if(!flipped)canvas.drawBitmap(myImage,myCurFrameLoc,myDimensions,myPaint);
        else{
            Matrix tempMatrix = new Matrix();
            tempMatrix.setScale(1,-1);
            Bitmap flippedImage = Bitmap.createBitmap(myImage,myCurFrameLoc.left,myCurFrameLoc.top,
                    myImageWidth,myImageHeight,tempMatrix,false);
            Rect tempCurFrameLoc = new Rect(0,0,myImageWidth,myImageHeight);
            //canvas.drawBitmap(flippedImage,myDimensions.left,myDimensions.top,myPaint);
            canvas.drawBitmap(flippedImage,tempCurFrameLoc,myDimensions,myPaint);
        }
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
        myCollisionBox.offsetTo((int)myDimensions.left,(int)myDimensions.top);
    }

    /*  PURPOSE:    Set's the game object's collision bounding box
        INPUT:      newCollisionBox        - The new collision bounding box
        OUTPUT:     NONE
    */
    public void setMyCollisionBounds(Rect newCollisionBox ){
        myCollisionBox.set(newCollisionBox);
        myCollisionBox.offsetTo((int)myDimensions.left,(int)myDimensions.top);
    }

    /*  PURPOSE:    Resets the objects width and height if it is greater than 0
        INPUT:      width               - The new width to set
                    height              - The new height to set
        OUTPUT:     NONE
     */
    public void resetWidthAndHeight(int width, int height){
        if(width > 0)myDimensions.right = myDimensions.left + width;
        if(height > 0)myDimensions.bottom = myDimensions.top + height;
    }

    /*  PURPOSE:    Return's the game object's collision bounds box
        INPUT:      NONE
        OUTPUT:     A Rect object with the data
    */
    public Rect getCollisionBounds(){
        return myCollisionBox;
    }

    /*  PURPOSE:    Return's the game object's left, top, right, bottom coordinates
        INPUT:      NONE
        OUTPUT:     A RectF object with the data
    */
    public RectF getDimensions(){
        return myDimensions;
    }

    /*  PURPOSE:    Set the flipped variable to true so that the image is flipped
        INPUT:      NONE
        OUTPUT:     NONE
     */
    protected void setImageFlip(){
        flipped = true;
    }

    /*  PURPOSE:    Move's the game object vertically by the amount given
        INPUT:      moveBy              - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveVertical(float moveBy){
        myDimensions.offset(0,moveBy);
        myCollisionBox.offset(0,(int)moveBy);
    }

    /*  PURPOSE:    Move's the game object horizontally by the amount given
        INPUT:      moveBy              - The amount to move the game object by
        OUTPUT:     NONE
    */
    protected void moveHorizontal(float moveBy){
        myDimensions.offset(moveBy,0);
        myCollisionBox.offset((int)moveBy,0);
    }

    /*  PURPOSE:    Runs the animation for the game object
                        (NOTE: Image Frame must be in right order of Normal state row 0 and
                        Destroyed state row 1 and only four frames per row and follow all guidelines
                        for image frames)
        INPUT:      NONE
        OUTPUT:     NONE
    */
    protected void animate(){
        if(myCurAniState >= myColumn)myCurAniState=0;
        myCurFrameLoc.set(myImageWidth *myCurFrameNum,myCurAniState, myImageWidth *(myCurFrameNum+1),
                myImageHeight *(myCurAniState+1));
        myCurFrameNum++;
        if(myCurFrameNum==myRow)myCurFrameNum=0;
    }
}

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
    //  PURPOSE:    Hold the number of row and column in the image sheet
    private int myRow, myColumn;
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
    protected int myCurAniState;

    /*  PURPOSE:    Constructor for the Game Object that take as input the image reference, width, and height
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    imageSheetRow       - The number of rows in the image sheet
                    imageSheetColumn    - The number of columns in the image sheet
        OUTPUT:     NONE
     */
    public GameObject(int imageReference, int imageWidth, int imageHeight, int imageSheetRow,
                      int imageSheetColumn){
        myWidth = imageWidth;
        myHeight = imageHeight;
        myRow = imageSheetRow;
        myColumn = imageSheetColumn;
        myDimensions = new RectF(0,0,imageWidth,imageHeight);
        myCurFrameLoc = new Rect(0,0,50,50);
        myVelocity = new Point(0,0);
        myPaint = new Paint();
        myCurFrameNum = 0;
        myCurAniState = R.integer.NormalAnimateState;
        setMyImage( BitmapFactory.decodeResource(GameGlobals.getInstance().getImageResources(),
                imageReference), imageSheetRow, imageSheetColumn);

    }

    /*  PURPOSE:    Constructor for the Game Object that take as input the image reference, width, and height
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
        OUTPUT:     NONE
     */
    public GameObject(int imageReference, int imageWidth, int imageHeight){
        this(imageReference,imageWidth,imageHeight,4,2);
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
                    row                 - The number of row in image frame
                    column              - The number of column in image frame
        OUTPUT:     NONE
    */
    public void setMyImage(Bitmap image, int row, int column){
        Matrix tempMatrix = new Matrix();
        tempMatrix.setRectToRect(new RectF(0, 0, image.getWidth(), image.getHeight()),
                new RectF(0, 0, myWidth*row, myHeight*column), Matrix.ScaleToFit.CENTER);
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
        if(myCurAniState >= myColumn)myCurAniState=0;
        myCurFrameLoc.set(myWidth*myCurFrameNum,myCurAniState,myWidth*(myCurFrameNum+1),
                myHeight*(myCurAniState+1));
        myCurFrameNum++;
        if(myCurFrameNum==myRow)myCurFrameNum=0;
    }
}

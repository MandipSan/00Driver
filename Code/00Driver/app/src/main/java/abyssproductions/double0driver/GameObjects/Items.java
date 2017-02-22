package abyssproductions.double0driver.GameObjects;

import android.graphics.Rect;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/1/2017.
 * Edited by Mark Reffel on 2/9/2017
 */

public class Items extends GameObject {
    //  PURPOSE:    Holds the item’s type
    private ItemTypes myType;
    //  PURPOSE:    Holds the different type of items
    public enum ItemTypes{HealthBox, AmmoBox, MysteryBox}

    /*  PURPOSE:    Constructor for the items that sets the default values for the object
        INPUT:      imageReference      - Reference's the image to be load
                    imageWidth          - The width of a single image in the image sheet
                    imageHeight         - The height of a single image in the image sheet
                    itemType            - The type of item to spawn
                    x                   - The x location to spawn the item from
                    y                   - The y location to spawn the item from
                    objDim              - The dimension of the object
        OUTPUT:     NONE
     */
    public Items(int imageReference, int imageWidth, int imageHeight, ItemTypes itemType,
                 float x, float y, RectF objDim){
        super(imageReference, imageWidth, imageHeight);
        myType = itemType;
        objDim.offset(x,y);
        setMyDimensions(objDim);
        myVelocity.set(0, GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.ItemYVelocity));
    }

    /*  PURPOSE:    Updates the item's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        moveVertical(myVelocity.y);
    }

    /*  PURPOSE:    Returns the type of item the item is
        INPUT:      NONE
        OUTPUT:     Return a ItemType var that hold the item’s type
     */
    public ItemTypes getItemType(){
        return myType;
    }
}

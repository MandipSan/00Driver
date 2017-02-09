package abyssproductions.double0driver.GameObjects;

import android.graphics.Rect;
import android.graphics.RectF;

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
        OUTPUT:     NONE
     */
    public Items(int imageReference, int width, int height){
        super(imageReference, width, height);
        myVelocity.set(0,1);
        myType = ItemTypes.HealthBox;
    }

    /*  PURPOSE:    Updates the item's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){
        super.update();
        moveVertical(myVelocity.y);
    }

    /*  PURPOSE:    Spawn the item of the type given at the location given
        INPUT:      itemType            - The type of item to spawn
                    x                   - The x location to spawn the item from
                    y                   - The y location to spawn the item from
        OUTPUT:     NONE
     */
    public void spawn(ItemTypes itemType, float x, float y){
        myType = itemType;
        RectF temp = new RectF(getDimensions());
        temp.offset(x,y);
        setMyDimensions(temp);
    }

    /*  PURPOSE:    Returns the type of item the item is
        INPUT:      NONE
        OUTPUT:     Return a ItemType var that hold the item’s type
     */
    public ItemTypes getItemType(){
        return myType;
    }
}

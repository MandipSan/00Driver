package abyssproductions.double0driver.GameObjects;

/**
 * Created by Mandip Sangha on 2/1/2017.
 */

public class Items extends GameObject {
    //  PURPOSE:    Holds the item’s type
    private ItemTypes myType;
    //  PURPOSE:    Holds the different type of items
    public enum ItemTypes{HealthBox}

    /*  PURPOSE:    Constructor for the items that sets the default values for the object
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public Items(){

    }

    /*  PURPOSE:    Updates the item's logic
        INPUT:      NONE
        OUTPUT:     NONE
     */
    public void update(){

    }

    /*  PURPOSE:    Spawn the item of the type given at the location given
        INPUT:      itemType            - The type of item to spawn
                    x                   - The x location to spawn the item from
                    y                   - The y location to spawn the item from
        OUTPUT:     NONE
     */
    public void spawn(ItemTypes itemType, float x, float y){

    }

    /*  PURPOSE:    Returns the type of item the item is
        INPUT:      NONE
        OUTPUT:     Return a ItemType var that hold the item’s type
     */
    public ItemTypes getItemType(){
        return myType;
    }
}

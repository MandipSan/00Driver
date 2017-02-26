package abyssproductions.double0driver.Utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

import abyssproductions.double0driver.GameObjects.ProjectileObjects.FlameThrowerProjectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/26/2017.
 */

public class GameImages {
    //  PURPOSE:    Hold's the player's image sheet
    private static Bitmap playerImage;
    //  PURPOSE:    Hold's the ambulance's image sheet
    private static Bitmap ambulanceImage;
    //  PURPOSE:    Hold's the ammo truck's image sheet
    private static Bitmap ammoTruckImage;
    //  PURPOSE:    Hold's the pickup's image sheet
    private static Bitmap pickupImage;
    //  PURPOSE:    Hold's the upgrade truck's image sheet
    private static Bitmap upgradeTruckImage;
    //  PURPOSE:    Hold's the van's image sheet
    private static Bitmap vanImage;
    //  PURPOSE:    Hold's the machine gun projectile's image sheet
    private static Bitmap machineGunProImage;
    //  PURPOSE:    Hold's the flame projectile's image sheet
    private static Bitmap flameProImage;
    //  PURPOSE:    Hold's the missile projectile's image sheet
    private static Bitmap missileProImage;
    //  PURPOSE:    Hold's the laser projectile's image sheet
    private static Bitmap laserProImage;

    /** PURPOSE:    Constructor for the object that loads all the games images
     *  INPUT:      resources           - Apps resource decoder
     *  OUTPUT:     NONE
     */
    public GameImages(Resources resources){

        playerImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.playersheet),
                4, 2, 309, 364);
        ambulanceImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.ambulancesheet), 4, 2, 309, 487);
        ammoTruckImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.ammotrucksheet), 4, 2, 309, 703);
        pickupImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.pickupsheet),
                4, 2, 309, 445);
        upgradeTruckImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.upgradetrucksheet), 4, 2, 309, 721);
        vanImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.vansheet),
                4, 2, 309, 445);

    }

    /** PURPOSE:    Returns player's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing player's image sheet
     */
    public Bitmap getPlayerImage(){
        return playerImage;
    }

    /** PURPOSE:    Returns ambulance's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing ambulance's image sheet
     */
    public Bitmap getAmbulanceImage(){
        return ambulanceImage;
    }

    /** PURPOSE:    Returns ammo truck's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing ammo truck's image sheet
     */
    public Bitmap getAmmoTruckImage(){
        return ammoTruckImage;
    }

    /** PURPOSE:    Returns pickup's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing pickup's image sheet
     */
    public Bitmap getPickupImage(){
        return pickupImage;
    }

    /** PURPOSE:    Returns upgrade truck's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing upgrade truck's image sheet
     */
    public Bitmap getUpgradeTruckImage() {
        return upgradeTruckImage;
    }

    /** PURPOSE:    Returns van's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing van's image sheet
     */
    public Bitmap getVanImage(){
        return vanImage;
    }

    /** PURPOSE:    Returns machine gun projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing machine gun projectile's image sheet
     */
    public Bitmap getMachineGunProImage(){
        return machineGunProImage;
    }

    /** PURPOSE:    Returns flame projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing flame projectile's image sheet
     */
    public Bitmap getFlameProImage(){
        return flameProImage;
    }

    /** PURPOSE:    Returns missile projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing missile projectile's image sheet
     */
    public Bitmap getMissileProImage(){
        return missileProImage;
    }

    /** PURPOSE:    Returns laser projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing laser projectile's image sheet
     */
    public Bitmap getLaserProImage(){
        return laserProImage;
    }

    /** PURPOSE:    Set's the image to the proper scaling and returns it
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing image given scaled to right size
     */
    private Bitmap setMyImage(Bitmap image, int row, int column, int imageWidth, int imageHeight){
        int sheetW = imageWidth *row;
        int sheetH = imageHeight *column;
        Matrix tempMatrix = new Matrix();
        tempMatrix.setRectToRect(new RectF(0, 0, image.getWidth(), image.getHeight()),
                new RectF(0, 0, sheetW, sheetH), Matrix.ScaleToFit.FILL);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(),
                tempMatrix, true);
    }
}

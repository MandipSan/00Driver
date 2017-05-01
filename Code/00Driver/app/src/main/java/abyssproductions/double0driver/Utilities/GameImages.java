package abyssproductions.double0driver.Utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/26/2017.
 */

public class GameImages {
    //  PURPOSE:    Hold's the player's image sheet
    private static Bitmap playerImage;
    //  PURPOSE:    Hold's the basic car A's image sheet
    private static Bitmap basicCarAImage;
    //  PURPOSE:    Hold's the basic car B's image sheet
    private static Bitmap basicCarBImage;
    //  PURPOSE:    Hold's the basic car C's image sheet
    private static Bitmap basicCarCImage;
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
    //  PURPOSE:    Hold's the sport car's image sheet
    private static Bitmap sportCarImage;
    //  PURPOSE:    Hold's the machine gun's image
    private static Bitmap machineGunImage;
    //  PURPOSE:    Hold's the missile launcher's image
    private static Bitmap missileLauncherImage;
    //  PURPOSE:    Hold's the laser cannon's image
    private static Bitmap laserCannonImage;
    //  PURPOSE:    Hold's the flame thrower's image
    private static Bitmap flameThrowerImage;
    //  PURPOSE:    Hold's the machine gun projectile's image sheet
    private static Bitmap machineGunProImage;
    //  PURPOSE:    Hold's the flame projectile's image sheet
    private static Bitmap flameProImage;
    //  PURPOSE:    Hold's the missile projectile's image sheet
    private static Bitmap missileProImage;
    //  PURPOSE:    Hold's the laser projectile's image sheet
    private static Bitmap laserProImage;
    //  PURPOSE:    Hold's the spike projectile's image sheet
    private static Bitmap spikeProImage;
    //  PURPOSE:    Hold's the health box's image sheet
    private static Bitmap healthBoxImage;
    //  PURPOSE:    Hold's the ammo box's image sheet
    private static Bitmap ammoBoxImage;
    //  PURPOSE:    Hold's the mystery box's image sheet
    private static Bitmap mysteryBoxImage;
    //  PURPOSE:    Hold's the upgrade pad's image sheet
    private static Bitmap upgradePadImage;
    //  PURPOSE:    Hold's the machine gun button's image
    private static Bitmap machineGunButtonImage;
    //  PURPOSE:    Hold's the missile launcher button's image
    private static Bitmap missileLauncherButtonImage;
    //  PURPOSE:    Hold's the laser cannon button's image
    private static Bitmap laserCannonButtonImage;
    //  PURPOSE:    Hold's the flame thrower button's image
    private static Bitmap flameThrowerButtonImage;

    /** PURPOSE:    Constructor for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameImages(){

    }

    /** PURPOSE:    Loads all the game images
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void loadGameImages(){
        GameGlobals instance =GameGlobals.getInstance();
        Resources resources = instance.getImageResources();
        //Vehicle Image Sheets
        int vehicleWidth = instance.getImageResources().getInteger(R.integer.VehicleImageWidth);
        playerImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.playersheet),
                vehicleWidth, instance.getImageResources().getInteger(R.integer.PlayerImageHeight));
        basicCarAImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.basiccarasheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.BasicCarImageHeight));
        basicCarBImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.basiccarbsheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.BasicCarImageHeight));
        basicCarCImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.basiccarcsheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.BasicCarImageHeight));
        ambulanceImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.ambulancesheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.AmbulanceImageHeight));
        ammoTruckImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.ammotrucksheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.AmmoTruckImageHeight));
        upgradeTruckImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.upgradetrucksheet), vehicleWidth, instance.getImageResources().
                getInteger(R.integer.UpgradeTruckImageHeight));
        vanImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.vansheet),
                vehicleWidth, instance.getImageResources().getInteger(R.integer.VanImageHeight));
        pickupImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.pickupsheet),
                vehicleWidth, instance.getImageResources().getInteger(R.integer.PickUpImageHeight));
        sportCarImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.sportcarsheet),
                vehicleWidth, instance.getImageResources().getInteger(R.integer.SportCarImageHeight));

        //Weapon Images
        machineGunImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.machinegun),
                1,1,vehicleWidth,
                instance.getImageResources().getInteger(R.integer.MachineGunImageHeight));
        missileLauncherImage = setMyImage(
                BitmapFactory.decodeResource(resources, R.drawable.missilelauncher), 1,1,
                vehicleWidth,
                instance.getImageResources().getInteger(R.integer.MissileLauncherImageHeight));
        laserCannonImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.lasercannon),
                1,1,vehicleWidth,
                instance.getImageResources().getInteger(R.integer.LaserCannonImageHeight));
        flameThrowerImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.flamethrower),
                1,1,vehicleWidth,
                instance.getImageResources().getInteger(R.integer.FlameThrowerImageHeight));

        //Project Image Sheets
        machineGunProImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.bulletsheet), 2, 1, instance.getImageResources().
                getInteger(R.integer.MachineGunProImageWidth), instance.getImageResources().
                getInteger(R.integer.MachineGunProImageHeight));
        laserProImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.lasersheet),
                2, 1, instance.getImageResources().getInteger(R.integer.LaserProImageWidth),
                instance.getImageResources().getInteger(R.integer.LaserProImageHeight));
        missileProImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.missilesheet), 3, 1, instance.getImageResources().
                getInteger(R.integer.MissileProImageWidth), instance.getImageResources().
                getInteger(R.integer.MissileProImageHeight));
        spikeProImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.spikesheet), 2, 1, instance.getImageResources().
                getInteger(R.integer.SpikeProImageWidth), instance.getImageResources().
                getInteger(R.integer.SpikeProImageHeight));

        //Items Image
        int iWidth = instance.getImageResources().getInteger(R.integer.ItemBoxImageWidth);
        int iHeight = instance.getImageResources().getInteger(R.integer.ItemBoxImageHeight);
        healthBoxImage = setMyImage(BitmapFactory.decodeResource(resources,
                R.drawable.healthbox), 1, 1, iWidth, iHeight);
        ammoBoxImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.ammobox),
                1, 1, iWidth, iHeight);
        mysteryBoxImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.mysterybox),
                1, 1, iWidth, iHeight);
        upgradePadImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.upgradepad),
                1, 1, instance.getImageResources().getInteger(R.integer.UpgradePadImageWidth),
                instance.getImageResources().getInteger(R.integer.UpgradePadImageHeight));

        //HUD Button Images
        int buttonSize = instance.getImageResources().getInteger(R.integer.ButtonImageSize);
        machineGunButtonImage = setMyImage(
                BitmapFactory.decodeResource(resources, R.drawable.machinegunbutton), 1, 1,
                buttonSize, buttonSize);
        missileLauncherButtonImage = setMyImage(
                BitmapFactory.decodeResource(resources, R.drawable.missilelauncherbutton), 1, 1,
                buttonSize, buttonSize);
        laserCannonButtonImage = setMyImage(
                BitmapFactory.decodeResource(resources, R.drawable.lasercannonbutton), 1, 1,
                buttonSize, buttonSize);
        /*flameThrowerButtonImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.flamethrowerbutton),
                1,1,vehicleWidth,
                instance.getImageResources().getInteger(R.integer.FlameThrowerImageHeight));*/
    }

    /** PURPOSE:    Unloads all the images
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void unloadGameImages(){
        //Vehicle Image Sheets
        playerImage = null;
        basicCarAImage = null;
        basicCarBImage = null;
        basicCarCImage = null;
        ambulanceImage = null;
        ammoTruckImage = null;
        upgradeTruckImage = null;
        vanImage = null;
        pickupImage = null;
        sportCarImage = null;

        //Weapon Images
        machineGunImage = null;
        missileLauncherImage = null;
        laserCannonImage = null;
        flameThrowerImage = null;

        //Project Image Sheets
        machineGunProImage = null;
        laserProImage = null;
        missileProImage = null;
        spikeProImage = null;

        //Items Image
        healthBoxImage = null;
        ammoBoxImage = null;
        mysteryBoxImage = null;
        upgradePadImage = null;

        //HUD Button Images
        machineGunButtonImage = null;
        missileLauncherButtonImage = null;
        laserCannonButtonImage = null;
        /*flameThrowerButtonImage = setMyImage(BitmapFactory.decodeResource(resources, R.drawable.flamethrowerbutton),
                1,1,vehicleWidth,
                instance.getImageResources().getInteger(R.integer.FlameThrowerImageHeight));*/
    }

    /** PURPOSE:    Returns player's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing player's image sheet
     */
    public Bitmap getPlayerImage(){
        return playerImage;
    }

    /** PURPOSE:    Returns basic car's image sheet based on the value given
     *  INPUT:      imageNum            -Holds the basic car image that is wanted
     *  OUTPUT:     Return's a bitmap containing basic car's image sheet
     */
    public Bitmap getBasicCarImage(int imageNum){
        if(imageNum == 0)return basicCarAImage;
        else if(imageNum == 1)return basicCarBImage;
        return basicCarCImage;
    }

    /** PURPOSE:    Returns sport car's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing sport car's image sheet
     */
    public Bitmap getSportCarImage(){
        return sportCarImage;
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

    /** PURPOSE:    Returns machine gun's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing machine gun's image
     */
    public Bitmap getMachineGunImage(){
        return machineGunImage;
    }

    /** PURPOSE:    Returns missile launcher's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing missile launcher's image
     */
    public Bitmap getMissileLauncherImage(){
        return missileLauncherImage;
    }

    /** PURPOSE:    Returns laser cannon's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing laser cannon's image
     */
    public Bitmap getLaserCannonImage(){
        return laserCannonImage;
    }

    /** PURPOSE:    Returns flame thrower's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing flame thrower's image
     */
    public Bitmap getFlameThrowerImage(){
        return flameThrowerImage;
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

    /** PURPOSE:    Returns spike projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing spike projectile's image sheet
     */
    public Bitmap getSpikeProImage(){
        return spikeProImage;
    }

    /** PURPOSE:    Returns laser projectile's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing laser projectile's image sheet
     */
    public Bitmap getLaserProImage(){
        return laserProImage;
    }

    /** PURPOSE:    Returns health box's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing health box's image sheet
     */
    public Bitmap getHealthBoxImage(){
        return healthBoxImage;
    }

    /** PURPOSE:    Returns ammo box's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing ammo box's image sheet
     */
    public Bitmap getAmmoBoxImage(){
        return ammoBoxImage;
    }

    /** PURPOSE:    Returns mystery box's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing mystery box's image sheet
     */
    public Bitmap getMysteryBoxImage(){
        return mysteryBoxImage;
    }

    /** PURPOSE:    Returns upgrade pad's image sheet
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing upgrade pad's image sheet
     */
    public Bitmap getUpgradePadImage(){
        return upgradePadImage;
    }

    /** PURPOSE:    Returns machine gun button's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing machine gun button's image
     */
    public Bitmap getMachineGunButtonImage(){
        return machineGunButtonImage;
    }

    /** PURPOSE:    Returns missile launcher button's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing missile launcher button's image
     */
    public Bitmap getMissileLauncherButtonImage(){
        return missileLauncherButtonImage;
    }

    /** PURPOSE:    Returns laser cannon button's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing laser cannon button's image
     */
    public Bitmap getLaserCannonButtonImage(){
        return laserCannonButtonImage;
    }

    /** PURPOSE:    Returns flame thrower button's image
     *  INPUT:      NONE
     *  OUTPUT:     Return's a bitmap containing flame thrower button's image
     */
    public Bitmap getFlameThrowerButtonImage(){
        return flameThrowerButtonImage;
    }

    /** PURPOSE:    Set's the image to the proper scaling and returns it
     *  INPUT:      image               - Image to set
     *              row                 - The number of rows in the image sheet
     *              column              - The number of columns in the image sheet
     *              imageWidth          - The image width to scale too
     *              imageHeight         - The image height to scale too
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

    /** PURPOSE:    Set's the image to the proper scaling and returns it using standard row and
     *                  column of 2 and 4
     *  INPUT:      image               - Image to set
     *              imageWidth          - The image width to scale too
     *              imageHeight         - The image height to scale too
     *  OUTPUT:     Return's a bitmap containing image given scaled to right size
     */
    private Bitmap setMyImage(Bitmap image, int imageWidth, int imageHeight){
        return this.setMyImage(image,4,2,imageWidth,imageHeight);
    }
}

package abyssproductions.double0driver.GameEngine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Random;

import abyssproductions.double0driver.GameObjects.Sprite;
import abyssproductions.double0driver.Utilities.Background;
import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Enemy;
import abyssproductions.double0driver.GameObjects.Items;
import abyssproductions.double0driver.GameObjects.Player;
import abyssproductions.double0driver.GameObjects.Projectile;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/14/2017.
 */

public class GameEngine {
    //  PURPOSE:    Holds the pointer to the game dynamic background
    private Background gameBackground;
    //  PURPOSE:    Holds the pointer to the player object
    private Player player;
    //  PURPOSE:    Holds whether the fire button is pressed down
    private boolean playerFire;
    //  PURPOSE:    Holds whether the game is over
    private boolean gameOver;
    //  PURPOSE:    Used to trigger upgrade screen
    private boolean upgradeScreenActivated;
    //  PURPOSE:    Holds the array of the active items in the game
    private Items [] gameItems;
    //  PURPOSE:    Holds an array of the enemies
    private Enemy [] myEnemies;
    //  PURPOSE:    Used to delay enemy spawn time
    private int enemySpawnDelay;
    //  PURPOSE:    Currently max delay enemy spawn time
    private int enemySpawnDelayMax;
    //  PURPOSE:    Holds the level that the enemies should spawn at
    private int enemyLevel;
    //  PURPOSE:    Used to delay the random mystery item spawn
    private int randomItemSpawnDelay;
    //  PURPOSE:    Used get random values
    private Random random;
    //  PURPOSE:    Holds the pointer to the HUD object
    private HUD gHUD;
    //  PURPOSE:    Pointer to GameGlobals
    private GameGlobals gGInstance;
    //  PURPOSE:    Holds the distance to wait before another vehicle spawn in the same lane
    private int [] laneLastSpawnSpace;

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameEngine(){
        gGInstance = GameGlobals.getInstance();
        gGInstance.loadPointers();
        GameGlobals.getInstance().getImages().loadGameImages();
        gameBackground = new Background();
        int tempWidth = gGInstance.getImageResources().getInteger(R.integer.VehicleImageWidth);
        int tempHeight = gGInstance.getImageResources().getInteger(R.integer.PlayerImageHeight);
        player = new Player(gGInstance.getImages().getPlayerImage(),tempWidth,tempHeight);
        player.setLaneTransitionMax(gameBackground.getLaneSize());
        //Rescales image to screen size (eliminates the need to call resetWidthAndHeight method in
        //  the GameObject since it is adjusted by passing the temp rect object to the
        //  setMyDimensions method call
        RectF temp = new RectF(0,0,gameBackground.getLaneSize(),
                (int)(gameBackground.getLaneSize()*((float)tempHeight/(float)tempWidth)));
        //Offset the player to always start in right middle lane
        temp.offset(((gameBackground.getNumLanes()/2)*gameBackground.getLaneSize())+
                gameBackground.getGrassSize(),gGInstance.getScreenHeight()/2);
        player.setMyDimensions(temp);
        //Sets the player collision box that is use for detecting collisions
        player.setMyCollisionBounds(new Rect(0,0,
                (int)(71*((float)gameBackground.getLaneSize()/(float)tempWidth)),
                (int)(gameBackground.getLaneSize()*((float)tempHeight/(float)tempWidth))));

        gGInstance.setStoppingDistance((int)(temp.width()/2));
        gGInstance.setFiringDistance((int)(temp.width()*3));

        myEnemies = new Enemy[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.EnemyArraySize)];
        for(int i =0; i < myEnemies.length; i++){
            myEnemies[i] = null;
        }

        gameItems = new Items[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.ItemsArraySize)];
        for(int i = 0; i < gameItems.length; i++){
            gameItems[i] = null;
        }

        laneLastSpawnSpace = new int[(gameBackground.getNumLanes()-2)];
        for (int i = 0; i < (gameBackground.getNumLanes()-2); i++){
            laneLastSpawnSpace[i] = 0;
        }

        randomItemSpawnDelay = gGInstance.getImageResources().getInteger(R.integer.ItemRandomSpawnDelayMax);;
        enemySpawnDelay = 0;
        enemySpawnDelayMax = GameGlobals.getInstance().getImageResources()
                .getInteger(R.integer.EnemyDefaultSpawnDelayMax);
        enemyLevel = 1;
        random = new Random();
        gHUD = new HUD(player.getMyPrimaryWeapon(),player.getMySecondaryWeapon());
        playerFire = false;
        gameOver = false;
        upgradeScreenActivated = false;
    }

    /** PURPOSE:    Updates the logic for the game
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        gHUD.update();
        gHUD.lifeLost(player.revivePlayer());
        gameOver = (gHUD.getNumLives() <= 0);
        gHUD.setCurrentWeaponAmmo(player.getAmmo(player.getMyPrimaryWeapon()));
        randomItemSpawn();
        checkCollision();

        //Updates the projectiles on the screen and checks out bound
        for(int i = 0; i < gGInstance.myProjectiles.length; i++){
            if(gGInstance.myProjectiles[i]!=null){
                gGInstance.myProjectiles[i].update();
                if(gGInstance.myProjectiles[i].getDimensions().top >=
                        gGInstance.getScreenHeight() ||
                        gGInstance.myProjectiles[i].getDimensions().bottom <=0 ||
                        !gGInstance.myProjectiles[i].getLife()){
                    gGInstance.myProjectiles[i] = null;
                }
            }
        }

        //Used to make sure the last vehicle that spawn in the lane is out of over lap distances
        int velocity = GameGlobals.getInstance().getImageResources().getInteger(R.integer.EnemyYVelocity);
        for (int j = 0; j < (gameBackground.getNumLanes()-2); j++){
            if(laneLastSpawnSpace[j] > 0)laneLastSpawnSpace[j]-=velocity;
        }

        enemyUpdateLogic();

        // Updates the items on the screen and checks out bound
        for(int m = 0; m < gameItems.length; m++){
            if(gameItems[m] != null) {
                gameItems[m].update();
                if(gameItems[m].getDimensions().top >= gGInstance.getScreenHeight()||
                        gameItems[m].getDimensions().bottom <= 0 ){
                    gameItems[m] = null;
                }
            }
        }

        if(playerFire)player.fireWeapon();
        player.update();
        player.displayHealthBar(gGInstance.getDisplayMiniHealthBar());

        //Checks if player is on the dirt road and decrease the health
        float pCX = player.getDimensions().centerX();
        int gBGS = gameBackground.getGrassSize();
        int gBLS = gameBackground.getLaneSize();
        int gW = gGInstance.getScreenWidth();
        if(( pCX > gBGS && pCX < (gBGS + gBLS)) || (pCX < (gW - gBGS) && pCX > ((gW - gBGS) - gBLS))){
            player.decreaseHealth(1);
        }

        gHUD.setHealthLevels(player.getHealth(),player.getMaxHealth());
        gHUD.setPopTextPos((int)player.getDimensions().left,(int)player.getDimensions().top);
    }

    /** PURPOSE:    Draws the whole game world
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        gameBackground.draw(canvas);
        for (Projectile p:gGInstance.myProjectiles) if(p != null)p.draw(canvas);

        for (Enemy e: myEnemies) if(e != null)e.draw(canvas);

        for (Items i: gameItems) if (i != null)i.draw(canvas);

        player.draw(canvas);

        gHUD.draw(canvas);
    }

    /** PURPOSE:    Reset the game objects to the default value
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void resetGame(){
        player.reset();
        player.getDimensions().offsetTo((
                (gameBackground.getNumLanes()/2)*gameBackground.getLaneSize())+
                gameBackground.getGrassSize(),gGInstance.getScreenHeight()/2);
        for(int i =0; i < myEnemies.length; i++){
            if(myEnemies[i] != null)myEnemies[i].killSoundEffect();
            myEnemies[i] = null;
        }
        for(int i = 0; i < gameItems.length; i++){
            gameItems[i] = null;
        }
        for (int i = 0; i < (gameBackground.getNumLanes()-2); i++){
            laneLastSpawnSpace[i] = 0;
        }
        enemySpawnDelay = 0;
        playerFire = false;
        gHUD.reset(player.getMyPrimaryWeapon(),player.getMySecondaryWeapon());
        gameOver = false;
        upgradeScreenActivated = false;
    }

    /** PURPOSE:    Sets the upgrade data received
     *  INPUT:      bundle              - The data for the upgrades
     *  OUTPUT:     NONE
     */
    public void setUpgradeData(Bundle bundle){
        Resources res = gGInstance.getImageResources();
        gHUD.reduceScoreBy(gHUD.getScore() - bundle.getInt(res.getString(R.string.Score)));
        player.increaseMaxHealth(bundle.getInt(res.getString(R.string.MaxHealth)) - player.getMaxHealth());
        int temp = bundle.getInt(res.getString(R.string.MGDamage))/player.getWeaponDamage(Sprite.WeaponTypes.MachineGun);
        if( temp != 1){
            player.increaseDamageLevel(Sprite.WeaponTypes.MachineGun,temp);
        }
        temp = bundle.getInt(res.getString(R.string.MLDamage))/player.getWeaponDamage(Sprite.WeaponTypes.Missile);
        if( temp != 1){
            player.increaseDamageLevel(Sprite.WeaponTypes.Missile,temp);
        }
        temp = bundle.getInt(res.getString(R.string.LBDamage))/player.getWeaponDamage(Sprite.WeaponTypes.Laser);
        if( temp != 1){
            player.increaseDamageLevel(Sprite.WeaponTypes.Laser,temp);
        }
        temp = bundle.getInt(res.getString(R.string.FTDamage))/player.getWeaponDamage(Sprite.WeaponTypes.Flamethrower);
        if( temp != 1){
            player.increaseDamageLevel(Sprite.WeaponTypes.Flamethrower,temp);
        }
        player.increaseMaxAmmo(Sprite.WeaponTypes.MachineGun,
                bundle.getInt(res.getString(R.string.MGMaxAmmo))-
                        player.getMaxAmmo(Sprite.WeaponTypes.MachineGun));
        player.increaseMaxAmmo(Sprite.WeaponTypes.Missile,
                bundle.getInt(res.getString(R.string.MLMaxAmmo))-
                        player.getMaxAmmo(Sprite.WeaponTypes.Missile));
        player.increaseMaxAmmo(Sprite.WeaponTypes.Laser,
                bundle.getInt(res.getString(R.string.LBMaxAmmo))-
                        player.getMaxAmmo(Sprite.WeaponTypes.Laser));
        player.increaseMaxAmmo(Sprite.WeaponTypes.Flamethrower,
                bundle.getInt(res.getString(R.string.FTMaxAmmo))-
                        player.getMaxAmmo(Sprite.WeaponTypes.Flamethrower));
        player.increaseAmmo(Sprite.WeaponTypes.MachineGun,
                bundle.getInt(res.getString(R.string.MGAmmo))-
                        player.getAmmo(Sprite.WeaponTypes.MachineGun));
        player.increaseAmmo(Sprite.WeaponTypes.Missile,
                bundle.getInt(res.getString(R.string.MLAmmo))-
                        player.getAmmo(Sprite.WeaponTypes.Missile));
        player.increaseAmmo(Sprite.WeaponTypes.Laser,
                bundle.getInt(res.getString(R.string.LBAmmo))-
                        player.getAmmo(Sprite.WeaponTypes.Laser));
        player.increaseAmmo(Sprite.WeaponTypes.Flamethrower,
                bundle.getInt(res.getString(R.string.FTAmmo))-
                        player.getAmmo(Sprite.WeaponTypes.Flamethrower));
        gHUD.setNumLives(bundle.getInt(res.getString(R.string.NumLife)));
        player.changeWeaponLoadOut(1, Sprite.WeaponTypes.values()
                [bundle.getInt(res.getString(R.string.PrimaryWeapon))]);
        player.changeWeaponLoadOut(2, Sprite.WeaponTypes.values()
                [bundle.getInt(res.getString(R.string.SecondaryWeapon))]);
        gHUD.currentWeaponTypes(player.getMyPrimaryWeapon(),player.getMySecondaryWeapon());
        upgradeScreenActivated = false;
        //gGInstance.mySoundEffects.resumeAllSoundEffect();
    }

    /** PURPOSE:    Calls the players fire when the pressed is set true
     *  INPUT:      pressed             - Holds whether the screen is pressed
     *              x                   - The x point that was pressed
     *              y                   - The y point that was pressed
     *  OUTPUT:     NONE
     */
    public void isPressed(boolean pressed, float x, float y){
        playerFire = pressed && gHUD.buttonPressed(0,x,y);
        if(gHUD.buttonPressed(1,x,y) && !pressed){
            player.switchWeapon();
            gHUD.currentWeaponTypes(player.getMyPrimaryWeapon(),player.getMySecondaryWeapon());
        }
    }

    //  PURPOSE:    Class used to detected if the fling gesture occurred
    public class GameGestureListener extends GestureDetector.SimpleOnGestureListener{

        /** PURPOSE:    Calls the players movement method when fling
         *  INPUT:      event1          -
         *              event2          -
         *              velocityX       -
         *              velocityY       -
         *  OUTPUT:     Returns true
         */
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
                               float velocityY){
            //The max difference in the swipe need
            int swipeMinDiff = 200;
            float rightSwipeDiff = event1.getX() - event2.getX();
            float leftSwipeDiff = event2.getX() - event1.getX();

            if(rightSwipeDiff >= swipeMinDiff){
                player.moveLeft();
                Log.d("Swiped","Left");
            }else if(leftSwipeDiff >= swipeMinDiff){
                player.moveRight();
                Log.d("Swiped","Right");
            }

            return true;
        }
    }

    /** PURPOSE:    Return if the game is over
     *  INPUT:      NONE
     *  OUTPUT:     Return a boolean of the game status
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /** PURPOSE:    Return if the upgrade screen needs to be activated
     *  INPUT:      NONE
     *  OUTPUT:     Return a boolean of the upgradeScreenActivated
     */
    public boolean getUpgradeScreenActivated(){
        return upgradeScreenActivated;
    }

    /** PURPOSE:    Return the game score
     *  INPUT:      NONE
     *  OUTPUT:     Return a int containing the score
     */
    public int getScore(){
        return gHUD.getScore();
    }

    /** PURPOSE:    Return the bundle that contains all the values that the upgrade uses
     *  INPUT:      NONE
     *  OUTPUT:     Return a bundle object contain the upgrade data
     */
    public Bundle getUpgradeData(){
        Resources res = gGInstance.getImageResources();
        Bundle bundle = new Bundle();
        bundle.putInt(res.getString(R.string.Score),gHUD.getScore());
        bundle.putInt(res.getString(R.string.MaxHealth),player.getMaxHealth());
        bundle.putInt(res.getString(R.string.MGDamage),player.getWeaponDamage(Sprite.WeaponTypes.MachineGun));
        bundle.putInt(res.getString(R.string.MLDamage),player.getWeaponDamage(Sprite.WeaponTypes.Missile));
        bundle.putInt(res.getString(R.string.LBDamage),player.getWeaponDamage(Sprite.WeaponTypes.Laser));
        bundle.putInt(res.getString(R.string.FTDamage),player.getWeaponDamage(Sprite.WeaponTypes.Flamethrower));
        bundle.putInt(res.getString(R.string.MGMaxAmmo),player.getMaxAmmo(Sprite.WeaponTypes.MachineGun));
        bundle.putInt(res.getString(R.string.MLMaxAmmo),player.getMaxAmmo(Sprite.WeaponTypes.Missile));
        bundle.putInt(res.getString(R.string.LBMaxAmmo),player.getMaxAmmo(Sprite.WeaponTypes.Laser));
        bundle.putInt(res.getString(R.string.FTMaxAmmo),player.getMaxAmmo(Sprite.WeaponTypes.Flamethrower));
        bundle.putInt(res.getString(R.string.MGAmmo),player.getAmmo(Sprite.WeaponTypes.MachineGun));
        bundle.putInt(res.getString(R.string.MLAmmo),player.getAmmo(Sprite.WeaponTypes.Missile));
        bundle.putInt(res.getString(R.string.LBAmmo),player.getAmmo(Sprite.WeaponTypes.Laser));
        bundle.putInt(res.getString(R.string.FTAmmo),player.getAmmo(Sprite.WeaponTypes.Flamethrower));
        bundle.putInt(res.getString(R.string.NumLife),gHUD.getNumLives());
        bundle.putInt(res.getString(R.string.PrimaryWeapon),player.getMyPrimaryWeapon().ordinal());
        bundle.putInt(res.getString(R.string.SecondaryWeapon),player.getMySecondaryWeapon().ordinal());
        //gGInstance.mySoundEffects.pauseAllSoundEffect();
        return bundle;
    }

    /** PURPOSE:    Checks the collision of the various game objects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void checkCollision(){
        int myLane;
        int playerLane = calculateInLane(player.getDimensions().centerX());
        Rect tempDim;
        Rect tempDimP = player.getCollisionBounds();
        //Checks if the player or projectiles collide with an enemy
        for(int i = 0; i < myEnemies.length; i++) {
            if(myEnemies[i] != null && !myEnemies[i].isInDestroyState()){
                //Stop enemies from colliding into player when in a lane that goes with the traffic
                myLane = calculateInLane(myEnemies[i].getDimensions().centerX());
                if (myLane >= (gameBackground.getNumLanes() / 2) && myEnemies[i].carRunning()) {
                    if (myLane == playerLane &&
                            (myEnemies[i].getMyType() == Enemy.EnemyType.MachineGunCar ||
                            myEnemies[i].getMyType() == Enemy.EnemyType.DronePickup)) {
                        if (myEnemies[i].getDimensions().top > player.getDimensions().top) {
                            if (myEnemies[i].getDimensions().top - player.getDimensions().bottom <=
                                    gGInstance.getStoppingDistance()) {
                                myEnemies[i].stopMovement();
                            }
                        }
                    }
                    //Stop enemies from colliding into enemies when in a lane that goes with the
                    //  traffic
                    for (int j = 0; j != myEnemies.length; j++) {
                        if (myEnemies[j] != null && i != j && !myEnemies[j].isInDestroyState()) {
                            //Check if the enemies are in the same lane or not
                            if (myLane == calculateInLane(myEnemies[j].getDimensions().centerX())) {
                                //Checks for which enemy is higher than the other
                                if (myEnemies[i].getDimensions().top <
                                        myEnemies[j].getDimensions().top) {
                                    if (myEnemies[j].getDimensions().top -
                                            myEnemies[i].getDimensions().bottom <=
                                            gGInstance.getStoppingDistance()) {
                                        myEnemies[j].stopMovement();
                                        if (myEnemies[j].getDimensions().bottom >
                                                (gGInstance.getScreenHeight() - 50)) {
                                            laneLastSpawnSpace[myLane - 1] =
                                                    (int) myEnemies[j].getDimensions().height();
                                        }
                                    }
                                } else {
                                    if (myEnemies[i].getDimensions().top -
                                            myEnemies[j].getDimensions().bottom <=
                                            gGInstance.getStoppingDistance()) {
                                        myEnemies[i].stopMovement();
                                        if (myEnemies[i].getDimensions().bottom >
                                                (gGInstance.getScreenHeight() - 50)) {
                                            laneLastSpawnSpace[myLane - 1] =
                                                    (int) myEnemies[i].getDimensions().height();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //Checks if enemy collides with a projectile
                for (int j = 0; j < gGInstance.myProjectiles.length; j++){
                    if(gGInstance.myProjectiles[j] != null){
                        tempDim = gGInstance.myProjectiles[j].getCollisionBounds();
                        if(myEnemies[i].getCollisionBounds().intersects(tempDim.left,tempDim.top,
                                tempDim.right,tempDim.bottom)) {
                            myEnemies[i].decreaseHealth(gGInstance.myProjectiles[j].getMyDamage());
                            gGInstance.myProjectiles[j] = null;
                        }
                    }
                }
                //Checks if enemy collides with the player
                if(myEnemies[i].getCollisionBounds().intersects(tempDimP.left,tempDimP.top,
                        tempDimP.right,tempDimP.bottom)){
                    myEnemies[i].killSoundEffect();
                    myEnemies[i] = null;
                    player.decreaseHealth((int)(player.getMaxHealth()*.25));
                }
            }
        }

        //Checks if a projectile collides with the player or items
        for(int k = 0; k < gGInstance.myProjectiles.length;k++){
            //Checks if a projectile collides with the player
            if(gGInstance.myProjectiles[k]!=null && gGInstance.myProjectiles[k].getCollisionBounds().
                    intersects(tempDimP.left,tempDimP.top,tempDimP.right,tempDimP.bottom)){
                player.decreaseHealth(gGInstance.myProjectiles[k].getMyDamage());
                gGInstance.myProjectiles[k] = null;
            }
//TODO: Separate into two parts for now need to discuss
            //Checks if the projectile collides with item boxes
            for(int m = 0; m < gameItems.length; m++){
                if(gameItems[m] != null){
                    tempDim = gameItems[m].getCollisionBounds();
                    if(gGInstance.myProjectiles[k]!=null && gGInstance.myProjectiles[k].getCollisionBounds().
                            intersects(tempDim.left,tempDim.top,tempDim.right,tempDim.bottom)){
                        //itemAffect(gameItems[m].getItemType());
                        gameItems[m] = null;
                        gGInstance.myProjectiles[k] = null;
                    }
                }
            }
        }

        //Checks for if player collide with item
        for(int m = 0; m < gameItems.length; m++) {
            if(gameItems[m] != null) {
                tempDim = gameItems[m].getCollisionBounds();
                if (player.getCollisionBounds().intersects(tempDim.left, tempDim.top,
                        tempDim.right, tempDim.bottom)) {
                    switch (gameItems[m].getItemType()) {
                        case UpgradePad:
                            upgradeScreenActivated = true;
                            break;
                        default:
                            itemAffect(gameItems[m].getItemType());
                            break;
                    }
                    gameItems[m] = null;
                }
            }
        }
    }

    /** PURPOSE:    Spawns the enemies
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void spawnEnemies(){
        //Holds the width size of the enemy vehicle images
        int tempWidth = gGInstance.getImageResources().getInteger(R.integer.VehicleImageWidth);
        //Holds the height size of the enemy vehicle images
        int tempHeight;
        //Holds the new enemy vehicle's image
        Bitmap tempImage;
        //Holds the new enemy's type
        Enemy.EnemyType tempType;
        //Holds the current spawn percents;
        int [] percent = getEnemySpawnPercents();
        //Holds the collision boxes width
        int tempColWidth = tempWidth;
        //Holds the collision boxes height
        int tempColHeight;
        //Holds the lane size to be used in various calculations
        int tempGameLaneSize = gameBackground.getLaneSize();

        //Calculation to determine new enemy type and position
        //Randomly picks value between 1 to 72
        int value = random.nextInt(100)+1;
        //Randomly picks lane value between 1 and the number of lanes minus 2 for the dirt lanes
        int lane = random.nextInt((gameBackground.getNumLanes()-2))+1;
        //Calculates the X position for the enemy based on the lane it is going to be in
        int x = 5+gameBackground.getGrassSize()+(tempGameLaneSize*lane);
        //Calculates the Y position for the enemy based on the lane it is going to be in
        int y = (lane <=(gameBackground.getNumLanes()/2)-1) ? 0 : gGInstance.getScreenHeight();

        if(laneLastSpawnSpace[lane-1] <= 0) {
            for (int i = 0; i < myEnemies.length; i++) {
                if (myEnemies[i] == null) {
                    if (value <= percent[0]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.BasicCarImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (71 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getBasicCarImage(random.nextInt(2));
                        tempType = Enemy.EnemyType.BasicCar;
                        //Log.d("spawnEnemies: ", "BC ");
                    } else if (value <= percent[0] + percent[1]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.SportCarImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (71 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getSportCarImage();
                        tempType = Enemy.EnemyType.MachineGunCar;
                        //Log.d("spawnEnemies: ", "MGC ");
                        //break;
                    } else if (value <= percent[0] + percent[1] + percent[2]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.PickUpImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (72 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getPickupImage();
                        tempType = Enemy.EnemyType.DronePickup;
                        //Log.d("spawnEnemies: ", "DP ");
                    } else if (value <= percent[0] + percent[1] + percent[2] + percent[3]) {
                        tempHeight = gGInstance.getImageResources().getInteger(R.integer.VanImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (72 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getVanImage();
                        tempType = Enemy.EnemyType.SpikeVan;
                    } else if (value <= percent[0] + percent[1] + percent[2] + percent[3] + percent[4]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.AmbulanceImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (84 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getAmbulanceImage();
                        tempType = Enemy.EnemyType.Ambulance;
                        //Log.d("spawnEnemies: ", "A ");
                    } else if (value <= percent[0] + percent[1] + percent[2] + percent[3] + percent[4] + percent[5]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.AmmoTruckImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (102 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getAmmoTruckImage();
                        tempType = Enemy.EnemyType.AmmoTruck;
                        //Log.d("spawnEnemies: ", "AT ");
                    } else if (value <= percent[0] + percent[1] + percent[2] + percent[3] + percent[4] + percent[5] + percent[6]) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.UpgradeTruckImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (88 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getUpgradeTruckImage();
                        tempType = Enemy.EnemyType.UpgradeTruck;
                        //Log.d("spawnEnemies: ", "UT ");
                    } else {
                        //myEnemies[i] = new Enemy(R.drawable.test,50,50,
                        //        Enemy.EnemyType.Helicopter,x,y);
                        //Log.d("spawnEnemies: ", "H ");
                        break;
                    }
                    //Last two values passed rescaled the image to appropriate size for the
                    //  different displays
                    myEnemies[i] = new Enemy(tempImage, tempWidth, tempHeight, tempType, x, y,
                            tempGameLaneSize-10,(int) (tempGameLaneSize *
                            ((float) tempHeight / (float) tempWidth)),enemyLevel);
                    myEnemies[i].setMyCollisionBounds(new Rect(0, 0, tempColWidth-10, tempColHeight));
                    laneLastSpawnSpace[lane-1] = (int)(tempGameLaneSize *
                            ((float) tempHeight / (float) tempWidth));
                    break;
                }
            }
        }
    }

    /** PURPOSE:    Updates the enemy logic
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void enemyUpdateLogic(){
        //The height and width size of the drop items
        int itemSize = (int)(gameBackground.getLaneSize() * .5);
        //Calls the enemy spawn method when the delay is up
        if(enemySpawnDelay == 0) {
            spawnEnemies();
            enemySpawnDelay = enemySpawnDelayMax;
        }
        enemySpawnDelay--;

        // Updates the enemies on the screen and checks out bound or if they are dead and spawn item
        //  drop if need
        for(int j = 0; j < myEnemies.length; j++){
            if(myEnemies[j]!=null){
                myEnemies[j].update((int)player.getDimensions().centerX(),
                        (int)player.getDimensions().centerY());
                myEnemies[j].displayHealthBar(gGInstance.getDisplayMiniHealthBar());
                if(myEnemies[j].getDimensions().top >= gGInstance.getScreenHeight() +
                                myEnemies[j].getDimensions().height()+1||
                        myEnemies[j].getDimensions().bottom <= -10 ){
                    myEnemies[j] = null;
                }
                //Second null check is for in case object was null for out of bounds
                if(myEnemies[j]!=null && myEnemies[j].isDead()) {
                    //TODO:Remove hard coded value
                    int w = gGInstance.getImageResources().
                            getInteger(R.integer.ItemBoxImageWidth);
                    int h = gGInstance.getImageResources().
                            getInteger(R.integer.ItemBoxImageHeight);
                    int spawnMBox = random.nextInt(10)+1;
                    if(spawnMBox == 1) {
                        for (int m = 0; m < gameItems.length; m++) {
                            if(gameItems[m]==null){
                                gameItems[m] = new Items(gGInstance.getImages().
                                        getMysteryBoxImage(), w, h,
                                        Items.ItemTypes.MysteryBox, myEnemies[j].getDimensions().
                                        centerX(), myEnemies[j].getDimensions().
                                        top, new RectF(0, 0, itemSize, itemSize));
                                gameItems[m].setMyCollisionBounds(new Rect(0,0,itemSize,itemSize));
                                break;
                            }
                        }
                    }
                    //TODO:Set correct enemy value for all enemies when defeat
                    //Spawns the items if an item drop vehicle was destroy
                    boolean set;
                    for (int k = 0; k < gameItems.length; k++){
                        set = false;
                        if(gameItems[k] == null) {
                            switch (myEnemies[j].getMyType()) {
                                case Ambulance:
                                    gameItems[k] = new Items(gGInstance.getImages().
                                            getHealthBoxImage(), w, h,
                                            Items.ItemTypes.HealthBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, itemSize, itemSize));
                                    gameItems[k].setMyCollisionBounds(new Rect(0,0,itemSize,itemSize));
                                    set = true;
                                    break;
                                case AmmoTruck:
                                    gameItems[k] = new Items(gGInstance.getImages().
                                            getAmmoBoxImage(), w, h,
                                            Items.ItemTypes.AmmoBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, itemSize, itemSize));
                                    gameItems[k].setMyCollisionBounds(new Rect(0,0,itemSize,itemSize));
                                    set = true;
                                    break;
                                case UpgradeTruck:
                                    //Sets up the upgrade pad that scale with vehicles sizes
                                    int uPWidth = (int) (gGInstance.getImageResources().
                                            getInteger(R.integer.UpgradePadImageWidth) *
                                            ((float) gameBackground.getLaneSize() /
                                            (float) gGInstance.getImageResources().
                                                    getInteger(R.integer.VehicleImageWidth)));
                                    gameItems[k] = new Items(gGInstance.getImages().
                                            getUpgradePadImage(), gGInstance.getImageResources().
                                            getInteger(R.integer.UpgradePadImageWidth),
                                            gGInstance.getImageResources().
                                                    getInteger(R.integer.UpgradePadImageHeight),
                                            Items.ItemTypes.UpgradePad, myEnemies[j].getDimensions().
                                            left+1, myEnemies[j].getDimensions().centerY(),
                                            new RectF(0, 0, uPWidth, gGInstance.getImageResources().
                                            getInteger(R.integer.UpgradePadImageHeight)));
                                    gameItems[k].setMyCollisionBounds(new Rect(0,0,uPWidth,gGInstance.getImageResources().
                                            getInteger(R.integer.UpgradePadImageHeight)));
                                    set = true;
                                    break;
                                case BasicCar:
                                    gHUD.scoreIncreaseBy(10);
                                    set = true;
                                    break;
                                case MachineGunCar:
                                    gHUD.scoreIncreaseBy(10);
                                    set = true;
                                    break;
                                case DronePickup:
                                    gHUD.scoreIncreaseBy(10);
                                    set = true;
                                    break;
                                case SpikeVan:
                                    gHUD.scoreIncreaseBy(10);
                                    set = true;
                                    break;
                                case Helicopter:
                                    gHUD.scoreIncreaseBy(10);
                                    set = true;
                                    break;
                                default:
                                    set = true;
                                    break;
                            }
                        }
                        if (set) {
                            myEnemies[j] = null;
                            break;
                        }
                    }
                }
            }
        }

        //Calculate the new level the enemies should spawn at
        if(gHUD.getScore() % gGInstance.getImageResources().
                getInteger(R.integer.EnemyLevelIncreaseMod) == 0){
            enemyLevel++;
            if(enemySpawnDelayMax > gGInstance.getImageResources().
                    getInteger(R.integer.EnemyLowestSpawnDelayMax)){
                enemySpawnDelayMax -= gGInstance.getImageResources().
                        getInteger(R.integer.EnemySpawnDelayMaxDecrease);
            }
        }
    }

    /** PURPOSE:    Does the resulting affect for the item type given
     *  INPUT:      itemTypes           - The item type whose affect should happen
     *  OUTPUT:     NONE
     */
    private void itemAffect(Items.ItemTypes itemTypes){
        switch (itemTypes){
            case HealthBox:
                player.increaseHealth(gGInstance.getImageResources().
                        getInteger(R.integer.ItemsHealthIncreaseVal));
                gHUD.setHealthIncreasePopText(gGInstance.getImageResources().
                        getInteger(R.integer.ItemsHealthIncreaseVal));
                break;
            case AmmoBox:
                int weaponPos = random.nextInt(2);
                if(weaponPos == 0) {
                    player.increaseAmmo(player.getMyPrimaryWeapon(), gGInstance.
                            getImageResources().getInteger(R.integer.ItemsAmmoIncreaseVal));
                    gHUD.setAmmoIncreasePopText(player.getMyPrimaryWeapon(), gGInstance.
                            getImageResources().getInteger(R.integer.ItemsAmmoIncreaseVal));
                }else{
                    player.increaseAmmo(player.getMySecondaryWeapon(), gGInstance.
                            getImageResources().getInteger(R.integer.ItemsAmmoIncreaseVal));
                    gHUD.setAmmoIncreasePopText(player.getMySecondaryWeapon(), gGInstance.
                            getImageResources().getInteger(R.integer.ItemsAmmoIncreaseVal));
                }
                break;
            case MysteryBox:
                int box = random.nextInt(Items.ItemTypes.values().length-2);
                itemAffect(Items.ItemTypes.values()[box]);
                break;
        }
    }

    /** PURPOSE:    Randomly spawns a mystery item
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void randomItemSpawn(){
        //Randomly picks a number
        float ranSpawn = random.nextInt(gGInstance.getImageResources().
                getInteger(R.integer.ItemRandomSpawnMaxVal))+1;

        if(randomItemSpawnDelay <= 0) {
            if (gGInstance.getImageResources().getInteger(R.integer.ItemRandomSpawnEqualVal) <= ranSpawn) {
                //Randomly pick which lane to spawn in
                int ranLane = random.nextInt(4) + 1;
                //The height and width size of the drop items
                int itemSize = (int) (gameBackground.getLaneSize() * .5);

                //Calculates the x position for the item
                int x = (int) (gameBackground.getLaneSize() * .33) + gameBackground.getGrassSize() +
                        (gameBackground.getLaneSize() * ranLane);
                int w = gGInstance.getImageResources().
                        getInteger(R.integer.ItemBoxImageWidth);
                int h = gGInstance.getImageResources().
                        getInteger(R.integer.ItemBoxImageHeight);
                for (int m = 0; m < gameItems.length; m++) {
                    if (gameItems[m] == null) {
                        gameItems[m] = new Items(gGInstance.getImages().getMysteryBoxImage(), w, h,
                                Items.ItemTypes.MysteryBox, x, 0, new RectF(0, 0, itemSize, itemSize));
                        gameItems[m].setMyCollisionBounds(new Rect(0, 0, itemSize, itemSize));

                        break;
                    }
                }
            }
            randomItemSpawnDelay = gGInstance.getImageResources().getInteger(R.integer.ItemRandomSpawnDelayMax);
        }
        randomItemSpawnDelay--;
    }

    /** PURPOSE:    Calculate's the lane the x provided is in and return the lane
     *  INPUT:      x                   - Value to use to check for the lane
     *  OUTPUT:     Return an int contain the lane the x is in
     */
    private int calculateInLane(float x){
        int laneSize = gameBackground.getLaneSize();
        int laneStart = gameBackground.getGrassSize();
        for(int i = 1; i < gameBackground.getNumLanes(); i++){
            if(x > (laneStart+(laneSize*i)) && x < (laneStart+(laneSize*(i+1)))){
                return i;
            }
        }
        return 0;
    }

    /** PURPOSE:    Returns the current percent array
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private int [] getEnemySpawnPercents(){
        int [] percentArray;
        switch(enemyLevel){
            case 1:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level1SpawnPercent);
                break;
            case 2:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level2SpawnPercent);
                break;
            case 3:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level3SpawnPercent);
                break;
            case 4:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level4SpawnPercent);
                break;
            case 5:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level5SpawnPercent);
                break;
            default:
                percentArray = gGInstance.getImageResources().getIntArray(R.array.Level1SpawnPercent);
                break;
        }
        return percentArray;
    }
}

package abyssproductions.double0driver.GameEngine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Random;

import abyssproductions.double0driver.Background;
import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Enemy;
import abyssproductions.double0driver.GameObjects.Items;
import abyssproductions.double0driver.GameObjects.Player;
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
    //  PURPOSE:    Holds the array of the active items in the game
    private Items [] gameItems;
    //  PURPOSE:    Holds an array of the enemies
    private Enemy [] myEnemies;
    //  PURPOSE:    Used to delay enemy spawn time
    private int enemySpawnDelay;
    //  PURPOSE:    Used get random values
    private Random random;
    //  PURPOSE:    Holds the pointer to the HUD object
    private HUD gHUD;
    //  PURPOSE:    Pointer to GameGlobals
    private GameGlobals gGInstance;

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameEngine(){
        gGInstance = GameGlobals.getInstance();
        gGInstance.loadPointers();
        gameBackground = new Background();
        //TODO:Value need to be changed
        int tempWidth = gGInstance.getImageResources().getInteger(R.integer.VehicleImageWidth);
        int tempHeight = gGInstance.getImageResources().getInteger(R.integer.PlayerImageHeight);
        player = new Player(gGInstance.getImages().getPlayerImage(),tempWidth,tempHeight);
        player.setLaneTransitionMax(gameBackground.getLaneSize());
        //Rescales image to screen size
        RectF temp = new RectF(0,0,gameBackground.getLaneSize(),
                (int)(gameBackground.getLaneSize()*((float)tempHeight/(float)tempWidth)));
        //Offset the player to always start in right middle lane
        temp.offset(((gameBackground.getNumLanes()/2)*gameBackground.getLaneSize())+
                gameBackground.getGrassSize(),1000);
        player.setMyDimensions(temp);
        //Sets the player collision box that is use for detecting collisions
        player.setMyCollisionBounds(new Rect(0,0,
                (int)(71*((float)gameBackground.getLaneSize()/(float)tempWidth)),
                (int)(gameBackground.getLaneSize()*((float)tempHeight/(float)tempWidth))));


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

        enemySpawnDelay = 0;
        random = new Random();
        gHUD = new HUD();
        playerFire = false;

    }

    /** PURPOSE:    Updates the logic for the game
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void update(){
        gHUD.updateScore();
        checkCollision();

        //Updates the projectiles on the screen and checks out bound
        for(int i = 0; i < gGInstance.myProjectiles.length; i++){
            if(gGInstance.myProjectiles[i]!=null){
                gGInstance.myProjectiles[i].update();
                if(gGInstance.myProjectiles[i].getDimensions().top >=
                        gGInstance.getScreenHeight() ||
                        gGInstance.myProjectiles[i].getDimensions().bottom <=0){
                    gGInstance.myProjectiles[i] = null;
                }
            }
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
        //Checks if player is on the dirt road and decrease the health
        float pCX = player.getDimensions().centerX();
        int gBGS = gameBackground.getGrassSize();
        int gBLS = gameBackground.getLaneSize();
        int gW = gGInstance.getScreenWidth();
        if(( pCX > gBGS && pCX < (gBGS + gBLS)) || (pCX < (gW - gBGS) && pCX > ((gW - gBGS) - gBLS))){
            player.decreaseHealth(1);
        }

        gHUD.setHealthLevels(player.getHealth(),player.getMaxHealth());
    }

    /** PURPOSE:    Draws the whole game world
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        gameBackground.draw(canvas);
        for(int i = 0; i < gGInstance.myProjectiles.length; i++){
            if(gGInstance.myProjectiles[i]!=null) {
                gGInstance.myProjectiles[i].draw(canvas);
            }
        }

        for(int j = 0; j < myEnemies.length; j++){
            if(myEnemies[j]!=null)myEnemies[j].draw(canvas);
        }

        for(int k = 0; k < gameItems.length; k++){
            if(gameItems[k]!=null)gameItems[k].draw(canvas);
        }

        player.draw(canvas);

        gHUD.draw(canvas);
    }

    /** PURPOSE:    Calls the players fire when the pressed is set true
     *  INPUT:      pressed             - Holds whether the screen is pressed
     *              x                   - The x point that was pressed
     *              y                   - The y point that was pressed
     *  OUTPUT:     NONE
     */
    public void isPressed(boolean pressed, float x, float y){
        playerFire = pressed && gHUD.buttonPressed(0,x,y);
        if(gHUD.buttonPressed(1,x,y))player.switchWeapon();
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

    /** PURPOSE:    Checks the collision of the various game objects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void checkCollision(){
        Rect tempDim;
        Rect tempDimP = player.getCollisionBounds();
        //Checks if the player or projectiles collide with an enemy
        for(int i = 0; i < myEnemies.length; i++) {
            if(myEnemies[i] != null){
                for (int j = 0; j < gGInstance.myProjectiles.length; j++){
                    if(gGInstance.myProjectiles[j] != null){
                        tempDim = gGInstance.myProjectiles[j].getCollisionBounds();
                        if(myEnemies[i].getCollisionBounds().intersects(tempDim.left,tempDim.top,
                                tempDim.right,tempDim.bottom)) {
                            //TODO:Change to use projectile damage
                            myEnemies[i].decreaseHealth(500);
                            gGInstance.myProjectiles[j] = null;
                        }
                    }
                }
                if(myEnemies[i].getCollisionBounds().intersects(tempDimP.left,tempDimP.top,
                        tempDimP.right,tempDimP.bottom)){
                    myEnemies[i] = null;
                    //TODO:Fill in what happens when enemy and player collide
                }
            }
        }


        for(int k = 0; k < gGInstance.myProjectiles.length;k++){
            if(gGInstance.myProjectiles[k]!=null && gGInstance.myProjectiles[k].getCollisionBounds().
                    intersects(tempDimP.left,tempDimP.top,tempDimP.right,tempDimP.bottom)){
                //TODO:Change to use projectile damage
                /*player.decreaseHealth(5);
                gGInstance.myProjectiles[k] = null;*/
            }

            for(int m = 0; m < gameItems.length; m++){
                if(gameItems[m] != null){
                    tempDim = gameItems[m].getCollisionBounds();
                    if(gGInstance.myProjectiles[k]!=null && gGInstance.myProjectiles[k].getCollisionBounds().
                            intersects(tempDim.left,tempDim.top,tempDim.right,tempDim.bottom)){
                        //TODO:To do item affect
                        switch (gameItems[m].getItemType()){
                            case HealthBox:
                                break;
                            case AmmoBox:
                                break;
                            case MysteryBox:
                                break;
                        }
                        gameItems[m] = null;
                        gGInstance.myProjectiles[k] = null;
                    }
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
        int tempHeight = 0;
        //Holds the new enemy vehicle's image
        Bitmap tempImage = null;
        //Holds the new enemy's type
        Enemy.EnemyType tempType;
        //Holds the collision boxes width
        int tempColWidth = tempWidth;
        //Holds the collision boxes height
        int tempColHeight = tempHeight;
        //Holds the lane size to be used in various calculations
        int tempGameLaneSize = gameBackground.getLaneSize();

        //Calculation to determine new enemy type and position
        //Randomly picks value between 1 to 72
        int value = random.nextInt(71)+1;
        //Randomly picks lane value between 1 and the number of lanes minus 2 for the dirt lanes
        int lane = random.nextInt((gameBackground.getNumLanes()-2))+1;
        //Calculates the X position for the enemy based on the lane it is going to be in
        int x = 10+gameBackground.getGrassSize()+(tempGameLaneSize*lane);
        //Calculates the Y position for the enemy based on the lane it is going to be in
        int y = (lane <=(gameBackground.getNumLanes()/2)-1) ? 100 : gGInstance.getScreenHeight();

        for(int i = 0; i < myEnemies.length; i++) {
            if (myEnemies[i] == null) {
                if (value <= 10) {
                    //Log.d("spawnEnemies: ", "BC ");
                    break;
                } else if (value <= 20) {
                    //Log.d("spawnEnemies: ", "MGC ");
                    break;
                } else if (value <= 30) {
                    tempHeight = gGInstance.getImageResources().
                            getInteger(R.integer.PickUpImageHeight);
                    tempColHeight = tempHeight;
                    tempColWidth = (int)(72*((float)tempGameLaneSize/(float)tempColWidth));
                    tempImage = gGInstance.getImages().getPickupImage();
                    tempType = Enemy.EnemyType.DronePickup;
                    //Log.d("spawnEnemies: ", "DP ");
                } else if (value <= 40) {
                    tempHeight = gGInstance.getImageResources().getInteger(R.integer.VanImageHeight);
                    tempColHeight = tempHeight;
                    tempColWidth = (int)(72*((float)tempGameLaneSize/(float)tempColWidth));
                    tempImage = gGInstance.getImages().getVanImage();
                    tempType = Enemy.EnemyType.SpikeVan;
                } else if (value <= 50) {
                    tempHeight = gGInstance.getImageResources().
                            getInteger(R.integer.AmbulanceImageHeight);
                    tempColHeight = tempHeight;
                    tempColWidth = (int)(84*((float)tempGameLaneSize/(float)tempColWidth));
                    tempImage = gGInstance.getImages().getAmbulanceImage();
                    tempType = Enemy.EnemyType.Ambulance;
                    //Log.d("spawnEnemies: ", "A ");
                }else if (value <= 60) {
                    tempHeight = gGInstance.getImageResources().
                            getInteger(R.integer.AmmoTruckImageHeight);
                    tempColHeight = tempHeight;
                    tempColWidth = (int)(102*((float)tempGameLaneSize/(float)tempColWidth));
                    tempImage = gGInstance.getImages().getAmmoTruckImage();
                    tempType = Enemy.EnemyType.AmmoTruck;
                    //Log.d("spawnEnemies: ", "AT ");
                }else if (value <= 70) {
                    tempHeight = gGInstance.getImageResources().
                            getInteger(R.integer.UpgradeTruckImageHeight);
                    tempColHeight = tempHeight;
                    tempColWidth = (int)(88*((float)tempGameLaneSize/(float)tempColWidth));
                    tempImage = gGInstance.getImages().getUpgradeTruckImage();
                    tempType = Enemy.EnemyType.UpgradeTruck;
                    //Log.d("spawnEnemies: ", "UT ");
                } else {
                    //myEnemies[i] = new Enemy(R.drawable.test,50,50,
                    //        Enemy.EnemyType.Helicopter,x,y);
                    //Log.d("spawnEnemies: ", "H ");
                    break;
                }
                myEnemies[i] = new Enemy(tempImage, tempWidth, tempHeight, tempType, x, y);
                myEnemies[i].resetWidthAndHeight(tempGameLaneSize, (int)(tempGameLaneSize*
                                ((float)tempHeight/(float)tempWidth)));
                myEnemies[i].setMyCollisionBounds(new Rect(0,0,tempColWidth,tempColHeight));
                break;
            }
        }
    }

    /** PURPOSE:    Updates the enemy logic
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void enemyUpdateLogic(){
        //Calls the enemy spawn method when the delay is up
        if(enemySpawnDelay == 0) {
            spawnEnemies();
            //TODO:Needs to be set to game config file values
            enemySpawnDelay = 5;
        }
        enemySpawnDelay--;

        // Updates the enemies on the screen and checks out bound or if they are dead and spawn item
        //  drop if need
        for(int j = 0; j < myEnemies.length; j++){
            if(myEnemies[j]!=null){
                myEnemies[j].update(0,0);
                if(myEnemies[j].getDimensions().top >= gGInstance.getScreenHeight() +
                                myEnemies[j].getDimensions().height()+1||
                        myEnemies[j].getDimensions().bottom <= 0 ){
                    myEnemies[j] = null;
                }
                //Second null check is for in case object was null for out of bounds
                if(myEnemies[j]!=null && myEnemies[j].isDead()) {
                    //TODO:Set correct enemy value for all enemies when defeat
                    //Spawns the items if an item drop vehicle was destroy
                    boolean set;
                    for (int k = 0; k < gameItems.length; k++){
                        set = false;
                        if(gameItems[k] == null) {
                            int w = gGInstance.getImageResources().
                                    getInteger(R.integer.ItemBoxImageWidth);
                            int h = gGInstance.getImageResources().
                                    getInteger(R.integer.ItemBoxImageHeight);
                            switch (myEnemies[j].getMyType()) {
                                case Ambulance:
                                    gameItems[k] = new Items(gGInstance.getImages().
                                            getHealthBoxImage(), w, h,
                                            Items.ItemTypes.HealthBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, 25, 25));
                                    gameItems[k].setMyCollisionBounds(new Rect(0,0,25,25));
                                    set = true;
                                    break;
                                case AmmoTruck:
                                    gameItems[k] = new Items(gGInstance.getImages().
                                            getAmmoBoxImage(), w, h,
                                            Items.ItemTypes.AmmoBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, 25, 25));
                                    gameItems[k].setMyCollisionBounds(new Rect(0,0,25,25));
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
    }
}

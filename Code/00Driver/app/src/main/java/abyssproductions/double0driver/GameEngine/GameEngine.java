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
    //  PURPOSE:    Holds the distance to wait before another vehicle spawn in the same lane
    private int [] laneLastSpawnSpace;

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
        gHUD.lifeLost(player.revivePlayer());
        if(gHUD.getNumLives() <= 0){
            //TODO:DO SOMETHING WHEN NUMBER OF LIVE IS 0
        }
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
        for (int j = 0; j < (gameBackground.getNumLanes()-2); j++){
            if(laneLastSpawnSpace[j] > 0)laneLastSpawnSpace[j]-=GameGlobals.enemiesUniVelocity;
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
        //Revives the player if they still have extra lives
        if(player.getHealth() == 0 && gHUD.getNumLives() != 0)player.revivePlayer();

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
        for (Projectile p:gGInstance.myProjectiles) if(p != null)p.draw(canvas);

        for (Enemy e: myEnemies) if(e != null)e.draw(canvas);

        for (Items i: gameItems) if (i != null)i.draw(canvas);

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
        if(gHUD.buttonPressed(1,x,y) && !pressed)player.switchWeapon();
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
        int myLane;
        int playerLane = calculateInLane(player.getDimensions().centerX());
        Rect tempDim;
        Rect tempDimP = player.getCollisionBounds();
        //Checks if the player or projectiles collide with an enemy
        for(int i = 0; i < myEnemies.length; i++) {
            if(myEnemies[i] != null){
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
                        if (myEnemies[j] != null && i != j) {
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
                    myEnemies[i] = null;
                    //TODO:Fill in what happens when enemy and player collide
                    player.decreaseHealth((int)(player.getMaxHealth()*.25));
                }
            }
        }


        for(int k = 0; k < gGInstance.myProjectiles.length;k++){
            //Checks if a projectile collides with the player
            if(gGInstance.myProjectiles[k]!=null && gGInstance.myProjectiles[k].getCollisionBounds().
                    intersects(tempDimP.left,tempDimP.top,tempDimP.right,tempDimP.bottom)){
                //TODO:Change to use projectile damage
                /*player.decreaseHealth(5);
                gGInstance.myProjectiles[k] = null;*/
            }

            //Checks if the projectile collides with item boxes
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
        int tempHeight;
        //Holds the new enemy vehicle's image
        Bitmap tempImage;
        //Holds the new enemy's type
        Enemy.EnemyType tempType;
        //Holds the collision boxes width
        int tempColWidth = tempWidth;
        //Holds the collision boxes height
        int tempColHeight;
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
        int y = (lane <=(gameBackground.getNumLanes()/2)-1) ? 0 : gGInstance.getScreenHeight();

        if(laneLastSpawnSpace[lane-1] <= 0) {
            for (int i = 0; i < myEnemies.length; i++) {
                if (myEnemies[i] == null) {
                    if (value <= 10) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.BasicCarImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (71 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getBasicCarImage(random.nextInt(2));
                        tempType = Enemy.EnemyType.BasicCar;
                        //Log.d("spawnEnemies: ", "BC ");
                    } else if (value <= 20) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.SportCarImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (71 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getSportCarImage();
                        tempType = Enemy.EnemyType.MachineGunCar;
                        //Log.d("spawnEnemies: ", "MGC ");
                        //break;
                    } else if (value <= 30) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.PickUpImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (72 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getPickupImage();
                        tempType = Enemy.EnemyType.DronePickup;
                        //Log.d("spawnEnemies: ", "DP ");
                    } else if (value <= 40) {
                        tempHeight = gGInstance.getImageResources().getInteger(R.integer.VanImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (72 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getVanImage();
                        tempType = Enemy.EnemyType.SpikeVan;
                    } else if (value <= 50) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.AmbulanceImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (84 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getAmbulanceImage();
                        tempType = Enemy.EnemyType.Ambulance;
                        //Log.d("spawnEnemies: ", "A ");
                    } else if (value <= 60) {
                        tempHeight = gGInstance.getImageResources().
                                getInteger(R.integer.AmmoTruckImageHeight);
                        tempColHeight = tempHeight;
                        tempColWidth = (int) (102 * ((float) tempGameLaneSize / (float) tempColWidth));
                        tempImage = gGInstance.getImages().getAmmoTruckImage();
                        tempType = Enemy.EnemyType.AmmoTruck;
                        //Log.d("spawnEnemies: ", "AT ");
                    } else if (value <= 70) {
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
                    //TODO:Hard code value need to change 
                    myEnemies[i] = new Enemy(tempImage, tempWidth, tempHeight, tempType, x-5, y);
                    myEnemies[i].resetWidthAndHeight(tempGameLaneSize-10, (int) (tempGameLaneSize *
                            ((float) tempHeight / (float) tempWidth)));
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
                myEnemies[j].update((int)player.getDimensions().centerX(),
                        (int)player.getDimensions().centerY());
                if(myEnemies[j].getDimensions().top >= gGInstance.getScreenHeight() +
                                myEnemies[j].getDimensions().height()+1||
                        myEnemies[j].getDimensions().bottom <= -10 ){
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
}

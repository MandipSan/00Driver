package abyssproductions.double0driver.GameEngine;

import android.graphics.Canvas;
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

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public GameEngine(){
        GameGlobals.getInstance().loadPointers();
        gameBackground = new Background();
        //TODO:Value need to be changed
        player = new Player(R.drawable.test,50,50);
        RectF temp = new RectF(0,0,gameBackground.getLaneSize()-20,gameBackground.getLaneSize()-20);//player.getDimensions();
        //Offset the player to always start in right middle lane
        temp.offset(((gameBackground.getNumLanes()/2)*gameBackground.getLaneSize())+
                gameBackground.getGrassSize()+5,1000);
        player.setMyDimensions(temp);

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
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++){
            if(GameGlobals.getInstance().myProjectiles[i]!=null){
                GameGlobals.getInstance().myProjectiles[i].update();
                if(GameGlobals.getInstance().myProjectiles[i].getDimensions().top >=
                        GameGlobals.getInstance().getScreenHeight() ||
                        GameGlobals.getInstance().myProjectiles[i].getDimensions().bottom <=0){
                    GameGlobals.getInstance().myProjectiles[i] = null;
                }
            }
        }

        enemyUpdateLogic();

        // Updates the items on the screen and checks out bound
        for(int m = 0; m < gameItems.length; m++){
            if(gameItems[m] != null) {
                gameItems[m].update();
                if(gameItems[m].getDimensions().top >= GameGlobals.getInstance().getScreenHeight()||
                        gameItems[m].getDimensions().bottom <= 0 ){
                    gameItems[m] = null;
                }
            }
        }

        if(playerFire)player.fireWeapon();
        player.update();

        gHUD.setHealthLevels(player.getHealth(),player.getMaxHealth());
    }

    /** PURPOSE:    Draws the whole game world
     *  INPUT:      canvas              - Pointer to the surface screen's canvas
     *  OUTPUT:     NONE
     */
    public void draw(Canvas canvas){
        gameBackground.draw(canvas);
        for(int i = 0; i < GameGlobals.getInstance().myProjectiles.length; i++){
            if(GameGlobals.getInstance().myProjectiles[i]!=null)
                GameGlobals.getInstance().myProjectiles[i].draw(canvas);
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
        RectF tempDim;
        RectF tempDimP = player.getDimensions();
        GameGlobals tempInst = GameGlobals.getInstance();
        //Checks if the player or projectiles collide with an enemy
        for(int i = 0; i < myEnemies.length; i++) {
            if(myEnemies[i] != null){
                for (int j = 0; j < tempInst.myProjectiles.length; j++){
                    if(tempInst.myProjectiles[j] != null){
                        tempDim = tempInst.myProjectiles[j].getDimensions();
                        if(myEnemies[i].getDimensions().intersects(tempDim.left,tempDim.top,
                                tempDim.right,tempDim.bottom)) {
                            //TODO:Change to use projectile damage
                            myEnemies[i].decreaseHealth(500);
                            tempInst.myProjectiles[j] = null;
                        }
                    }
                }
                if(myEnemies[i].getDimensions().intersects(tempDimP.left,tempDimP.top,
                        tempDimP.right,tempDimP.bottom)){
                    myEnemies[i] = null;
                    //TODO:Fill in what happens when enemy and player collide
                }
            }
        }


        for(int k = 0; k < tempInst.myProjectiles.length;k++){
            if(tempInst.myProjectiles[k]!=null && tempInst.myProjectiles[k].getDimensions().
                    intersects(tempDimP.left,tempDimP.top,tempDimP.right,tempDimP.bottom)){
                //TODO:Change to use projectile damage
                //player.decreaseHealth(5);
                //tempInst.myProjectiles[k] = null;
            }

            for(int m = 0; m < gameItems.length; m++){
                if(gameItems[m] != null){
                    tempDim = gameItems[m].getDimensions();
                    if(tempInst.myProjectiles[k]!=null && tempInst.myProjectiles[k].getDimensions().
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
                        tempInst.myProjectiles[k] = null;
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
        //Randomly picks value between 1 to 72
        int value = random.nextInt(71)+1;
        //Randomly picks lane value between 1 and the number of lanes minus 2 for the dirt lanes
        int lane = random.nextInt((gameBackground.getNumLanes()-2))+1;
        //Calculates the X position for the enemy based on the lane it is going to be in
        int x = 10+gameBackground.getGrassSize()+(gameBackground.getLaneSize()*lane);
        //Calculates the Y position for the enemy based on the lane it is going to be in
        int y = (lane <=(gameBackground.getNumLanes()/2)-1) ? 100 :
                GameGlobals.getInstance().getScreenHeight();

        for(int i = 0; i < myEnemies.length; i++) {
            if (myEnemies[i] == null) {
                if (value <= 10) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.BasicCar, x,
                            y);
                    Log.d("spawnEnemies: ", "BC ");
                    break;
                } else if (value <= 20) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.MachineGunCar,
                            x, y);
                    Log.d("spawnEnemies: ", "MGC ");
                    break;
                } else if (value <= 30) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.DronePickup,
                            x, y);
                    Log.d("spawnEnemies: ", "DP ");
                    break;
                } else if (value <= 40) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.SpikeVan, x,
                            y);
                    Log.d("spawnEnemies: ", "SV ");
                    break;
                } else if (value <= 50) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.Ambulance, x,
                            y);
                    Log.d("spawnEnemies: ", "A ");
                    break;
                }else if (value <= 60) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.AmmoTruck, x,
                            y);
                    Log.d("spawnEnemies: ", "AT ");
                    break;
                }else if (value <= 70) {
                    myEnemies[i] = new Enemy(R.drawable.test, 50, 50, Enemy.EnemyType.UpgradeTruck, x,
                            y);
                    Log.d("spawnEnemies: ", "UT ");
                    break;
                } else {
                    myEnemies[i] = new Enemy(R.drawable.test,50,50,
                            Enemy.EnemyType.Helicopter,x,y);
                    Log.d("spawnEnemies: ", "H ");
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
                myEnemies[j].update(0,0);
                if(myEnemies[j].getDimensions().top >=
                        GameGlobals.getInstance().getScreenHeight() +
                                myEnemies[j].getDimensions().height()+1||
                        myEnemies[j].getDimensions().bottom <= 0 ){
                    myEnemies[j] = null;
                }
                //Second null check in case object was null for out of bounds
                if(myEnemies[j]!=null && myEnemies[j].isDead()) {
                    //TODO:Set enemy value for all enemies when defeat
                    //Spawns the items if an item drop vehicle was destroy
                    boolean set;
                    for (int k = 0; k < gameItems.length; k++){
                        set = false;
                        if(gameItems[k] == null) {
                            switch (myEnemies[j].getMyType()) {
                                case Ambulance:
                                    gameItems[k] = new Items(R.drawable.test, 50, 50,
                                            Items.ItemTypes.HealthBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, 25, 25));
                                    set = true;
                                    break;
                                case AmmoTruck:
                                    gameItems[k] = new Items(R.drawable.test, 50, 50,
                                            Items.ItemTypes.AmmoBox, myEnemies[j].getDimensions().
                                            centerX(), myEnemies[j].getDimensions().
                                            centerY(), new RectF(0, 0, 10, 10));
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

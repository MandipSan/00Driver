package abyssproductions.double0driver;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Mandip Sangha on 2/17/2017.
 */

public class Background {
    private int numLanes;
    private int laneSize;
    private int GrassSize;
    private Rect roadDim;
    private Rect dirtRoadDim;
    private Rect roadDividerDim;
    private Paint paint;

    public Background(){
        int TotalRoadSize = (int)(GameGlobals.getInstance().getScreenWidth()*.6);
        GrassSize = (int)(GameGlobals.getInstance().getScreenWidth()*.2);
        Resources tempRes = GameGlobals.getInstance().getImageResources();

        //checks that if the number of lanes calculate is more than minimum number of lanes
        if(TotalRoadSize/tempRes.getInteger(R.integer.MaximumLaneSize) <
                tempRes.getInteger(R.integer.MinimumNumberOfLanes)){
            laneSize = TotalRoadSize/tempRes.getInteger(R.integer.MinimumNumberOfLanes);
            numLanes = tempRes.getInteger(R.integer.MinimumNumberOfLanes);
        }else{
            numLanes = TotalRoadSize/tempRes.getInteger(R.integer.MaximumLaneSize);
            laneSize = tempRes.getInteger(R.integer.MaximumLaneSize);
        }

        paint = new Paint();
        roadDim = new Rect(0,0,(numLanes-2)*laneSize,GameGlobals.getInstance().getScreenHeight());
        dirtRoadDim = new Rect(0,0,laneSize,GameGlobals.getInstance().getScreenHeight());
        roadDividerDim = new Rect(0,0,10,GameGlobals.getInstance().getScreenHeight());
        roadDim.offset(GrassSize+laneSize,0);
    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.rgb(34,139,34));
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(roadDim,paint);

        paint.setColor(Color.rgb(160,82,45));
        dirtRoadDim.offsetTo(GrassSize,0);
        canvas.drawRect(dirtRoadDim,paint);
        dirtRoadDim.offsetTo(roadDim.right,0);
        canvas.drawRect(dirtRoadDim,paint);

        paint.setColor(Color.WHITE);
        roadDividerDim.offsetTo(roadDim.left+laneSize,0);
        for(int i = 0; i < (numLanes-3); i++) {
            canvas.drawRect(roadDividerDim, paint);
            roadDividerDim.offset(laneSize, 0);
        }
    }

    public int getLaneSize(){
        return laneSize;
    }

    public int getNumLanes(){
        return numLanes;
    }

    public int getGrassSize(){
        return GrassSize;
    }
}

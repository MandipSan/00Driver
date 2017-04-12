package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.UpgradeImageAdapter;


/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class UpgradeScreen extends Fragment {
    private int Score;
    private int [] buttonVars;
    private int [] increaseValues;
    private int [] costValues;
    private UpgradeImageAdapter adapter;
    private GridView gridview;
    public enum ItemsList{MaxHealth, MachineGunDamage, MissileLauncherDamage, LaserBeamDamage,
        FlameThrowerDamage, MachineGunMaxAmmo, MissileLauncherMaxAmmo, LaserBeamMaxAmmo,
        FlameThrowerMaxAmmo, FillMachineGunAmmo, FillMissileLauncherAmmo, FillLaserBeamAmmo,
        FillFlameThrowerAmmo};

    public static UpgradeScreen newInstance(){
        return new UpgradeScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        if(buttonVars == null||buttonVars[0] == -1)((MainActivity)getActivity()).changeFrags("StartScreen");
        View view = inflater.inflate(R.layout.upgrade_screen,container,false);
        final TextView textView = ((TextView)view.findViewById(R.id.Score));
        adapter = new UpgradeImageAdapter(getActivity(),this);
        gridview = (GridView) view.findViewById(R.id.UpgradeMenu);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(Score - costValues[position] > 0){
                    Score -= costValues[position];
                    buttonVars[position] += increaseValues[position];
                    //TODO:Change from hardcode value
                    increaseValues[position] += 100;
                    costValues[position] += 100;
                    textView.setText("Score: "+Score);
                    adapter.notifyDataSetChanged();
                    gridview.invalidateViews();
                }
            }
        });

        Button button = (Button)view.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();
                bundle.putInt("score",Score);
                bundle.putInt("MaxHealth",buttonVars[ItemsList.MaxHealth.ordinal()]);
                bundle.putInt("MGDamage",buttonVars[ItemsList.MachineGunDamage.ordinal()]);
                bundle.putInt("MLDamage",buttonVars[ItemsList.MissileLauncherDamage.ordinal()]);
                bundle.putInt("LBDamage",buttonVars[ItemsList.LaserBeamDamage.ordinal()]);
                bundle.putInt("FTDamage",buttonVars[ItemsList.FlameThrowerDamage.ordinal()]);
                bundle.putInt("MGMaxAmmo",buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()]);
                bundle.putInt("MLMaxAmmo",buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()]);
                bundle.putInt("LBMaxAmmo",buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()]);
                bundle.putInt("FTMaxAmmo",buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()]);
                bundle.putInt("MGAmmo",buttonVars[ItemsList.FillMachineGunAmmo.ordinal()]);
                bundle.putInt("MLAmmo",buttonVars[ItemsList.FillMissileLauncherAmmo.ordinal()]);
                bundle.putInt("LBAmmo",buttonVars[ItemsList.FillLaserBeamAmmo.ordinal()]);
                bundle.putInt("FTAmmo",buttonVars[ItemsList.FillFlameThrowerAmmo.ordinal()]);
                ((MainActivity)getActivity()).changeFrags("GameScreen",bundle);
            }
        });

        textView.setText("Score: "+Score);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ;
    }

    /** PURPOSE:    To get the data need for the fragment
     *  INPUT:      bundle              - The data being passed to the fragment
     *  OUTPUT:     NONE
     */
    public void passData(Bundle bundle){
        if(buttonVars == null){
            buttonVars = new int[ItemsList.values().length];
            increaseValues = new int[ItemsList.values().length];
            costValues =  new int[ItemsList.values().length];
            for(int i  = 0; i < ItemsList.values().length; i++){
                increaseValues[i] = 100;
                costValues[i] = 100;
            }
        }
        if(bundle != null){
            Score = bundle.getInt("score");
            buttonVars[ItemsList.MaxHealth.ordinal()] = bundle.getInt("MaxHealth");
            buttonVars[ItemsList.MachineGunDamage.ordinal()] = bundle.getInt("MGDamage");
            buttonVars[ItemsList.MissileLauncherDamage.ordinal()] = bundle.getInt("MLDamage");
            buttonVars[ItemsList.LaserBeamDamage.ordinal()] = bundle.getInt("LBDamage");
            buttonVars[ItemsList.FlameThrowerDamage.ordinal()] = bundle.getInt("FTDamage");
            buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()] = bundle.getInt("MGMaxAmmo");
            buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()] = bundle.getInt("MLMaxAmmo");
            buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()] = bundle.getInt("LBMaxAmmo");
            buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()] = bundle.getInt("FTMaxAmmo");
            buttonVars[ItemsList.FillMachineGunAmmo.ordinal()] = bundle.getInt("MGAmmo");
            buttonVars[ItemsList.FillMissileLauncherAmmo.ordinal()] = bundle.getInt("MLAmmo");
            buttonVars[ItemsList.FillLaserBeamAmmo.ordinal()] = bundle.getInt("LBAmmo");
            buttonVars[ItemsList.FillFlameThrowerAmmo.ordinal()] = bundle.getInt("FTAmmo");
        }else{
            for(int i = 0; i < buttonVars.length; i++){
                buttonVars[i] = -1;
            }
        }
    }

    /** PURPOSE:    Return's the current button value for the position given
     *  INPUT:      position            - The to get the value at
     *  OUTPUT:     Return an int value
     */
    public int getCurrentValue(int position){
        return buttonVars[position];
    }

    /** PURPOSE:    Return's the current cost value for the position given
     *  INPUT:      position            - The to get the value at
     *  OUTPUT:     Return an int value
     */
    public int getCost(int position){
        return costValues[position];
    }

    /** PURPOSE:    Return's the current increase value for the position given
     *  INPUT:      position            - The to get the value at
     *  OUTPUT:     Return an int value
     */
    public int getIncreaseValue(int position){
        return increaseValues[position];
    }
}

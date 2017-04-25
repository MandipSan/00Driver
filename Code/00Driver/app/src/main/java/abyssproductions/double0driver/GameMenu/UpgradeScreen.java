package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.content.res.Resources;
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

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.UpgradeImageAdapter;

import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.MachineGun;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.SelectPrimaryWeapon;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.SelectSecondaryWeapon;


/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class UpgradeScreen extends Fragment {
    //  PURPOSE:    Holds the current score
    private int Score;
    //  PURPOSE:    Holds the values for each button that is passed and received
    private int [] buttonVars;
    //  PURPOSE:    Holds how much the button value will increase
    private int [] increaseValues;
    //  PURPOSE:    Holds the cost of increase the button value
    private int [] costValues;
    //  PURPOSE:    Holds where the primary or secondary weapon is the selectable
    private boolean primarySelectorActive;
    //  PURPOSE:    Holds the adapter for the grid buttons
    private UpgradeImageAdapter adapter;
    //  PURPOSE:    Holds the grid
    private GridView gridview;
    //  PURPOSE:    Holds the names and what each button in the grid is
    public enum ItemsList{NumberLives, MaxHealth, MachineGunDamage, MissileLauncherDamage, LaserBeamDamage,
        FlameThrowerDamage, MachineGunMaxAmmo, MissileLauncherMaxAmmo, LaserBeamMaxAmmo,
        FlameThrowerMaxAmmo, FillMachineGunAmmo, FillMissileLauncherAmmo, FillLaserBeamAmmo,
        FillFlameThrowerAmmo,SelectPrimaryWeapon,SelectSecondaryWeapon,MachineGun, MissileLauncher,
        LaserBeam, FlameThrower};

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
                if(Score - costValues[position] > 0 && position < SelectPrimaryWeapon.ordinal()){
                    Score -= costValues[position];
                    buttonVars[position] += increaseValues[position];
                    //TODO:Change from hardcode value
                    increaseValues[position] += 100;
                    costValues[position] += 100;
                    textView.setText("Score: "+Score);
                    adapter.notifyDataSetChanged();
                    gridview.invalidateViews();
                }else if(position == SelectPrimaryWeapon.ordinal()){
                    primarySelectorActive = true;
                }else if(position == SelectSecondaryWeapon.ordinal()){
                    primarySelectorActive = false;
                }else {
                    //Used to determine which weapon was activate
                    if (primarySelectorActive) {
                        for (int i = MachineGun.ordinal(); i <= ItemsList.values().length; i++) {
                            if (buttonVars[i] == 1) buttonVars[i] = 0;
                        }
                        buttonVars[position] = 1;
                    } else {
                        if (buttonVars[position] != 1) {
                            for (int i = MachineGun.ordinal(); i <= ItemsList.values().length; i++) {
                                if (buttonVars[i] == 2) buttonVars[i] = 0;
                            }
                            buttonVars[position] = 2;
                        }
                    }
                }
            }
        });

        Button button = (Button)view.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Resources res = getResources();
                Bundle bundle = new Bundle();
                bundle.putInt(res.getString(R.string.Score),Score);
                bundle.putInt(res.getString(R.string.MaxHealth),buttonVars[ItemsList.MaxHealth.ordinal()]);
                bundle.putInt(res.getString(R.string.MGDamage),buttonVars[ItemsList.MachineGunDamage.ordinal()]);
                bundle.putInt(res.getString(R.string.MLDamage),buttonVars[ItemsList.MissileLauncherDamage.ordinal()]);
                bundle.putInt(res.getString(R.string.LBDamage),buttonVars[ItemsList.LaserBeamDamage.ordinal()]);
                bundle.putInt(res.getString(R.string.FTDamage),buttonVars[ItemsList.FlameThrowerDamage.ordinal()]);
                bundle.putInt(res.getString(R.string.MGMaxAmmo),buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.MLMaxAmmo),buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.LBMaxAmmo),buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.FTMaxAmmo),buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.MGAmmo),buttonVars[ItemsList.FillMachineGunAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.MLAmmo),buttonVars[ItemsList.FillMissileLauncherAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.LBAmmo),buttonVars[ItemsList.FillLaserBeamAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.FTAmmo),buttonVars[ItemsList.FillFlameThrowerAmmo.ordinal()]);
                bundle.putInt(res.getString(R.string.NumLife),buttonVars[ItemsList.NumberLives.ordinal()]);

                //Used to calculate the activate primary and secondary weapon to pass back
                int j = 0;
                for(int i = MachineGun.ordinal(); i != ItemsList.values().length; i++){
                    if(buttonVars[i] == 1)bundle.putInt(res.getString(R.string.PrimaryWeapon),j);
                    if(buttonVars[i] == 2)bundle.putInt(res.getString(R.string.SecondaryWeapon),j);
                    j++;
                }

                ((MainActivity)getActivity()).changeFrags("GameScreen",bundle);
            }
        });

        textView.setText("Score: "+Score);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /** PURPOSE:    To get the data need for the fragment
     *  INPUT:      bundle              - The data being passed to the fragment
     *  OUTPUT:     NONE
     */
    public void passData(Bundle bundle){
        Resources res = GameGlobals.getInstance().getImageResources();
        if(buttonVars == null){
            buttonVars = new int[ItemsList.values().length];
            increaseValues = new int[ItemsList.values().length];
            costValues =  new int[ItemsList.values().length];
            for(int i  = 0; i < ItemsList.values().length; i++){
                buttonVars[i] = 0;
                increaseValues[i] = 100;
                costValues[i] = 100;
            }
        }
        //Sets the data passed to the correct button value holding position
        if(bundle != null){
            Score = bundle.getInt(res.getString(R.string.Score));
            buttonVars[ItemsList.MaxHealth.ordinal()] = bundle.getInt(res.getString(R.string.MaxHealth));
            buttonVars[ItemsList.MachineGunDamage.ordinal()] = bundle.getInt(res.getString(R.string.MGDamage));
            buttonVars[ItemsList.MissileLauncherDamage.ordinal()] = bundle.getInt(res.getString(R.string.MLDamage));
            buttonVars[ItemsList.LaserBeamDamage.ordinal()] = bundle.getInt(res.getString(R.string.LBDamage));
            buttonVars[ItemsList.FlameThrowerDamage.ordinal()] = bundle.getInt(res.getString(R.string.FTDamage));
            buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()] = bundle.getInt(res.getString(R.string.MGMaxAmmo));
            buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()] = bundle.getInt(res.getString(R.string.MLMaxAmmo));
            buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()] = bundle.getInt(res.getString(R.string.LBMaxAmmo));
            buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()] = bundle.getInt(res.getString(R.string.FTMaxAmmo));
            buttonVars[ItemsList.FillMachineGunAmmo.ordinal()] = bundle.getInt(res.getString(R.string.MGAmmo));
            buttonVars[ItemsList.FillMissileLauncherAmmo.ordinal()] = bundle.getInt(res.getString(R.string.MLAmmo));
            buttonVars[ItemsList.FillLaserBeamAmmo.ordinal()] = bundle.getInt(res.getString(R.string.LBAmmo));
            buttonVars[ItemsList.FillFlameThrowerAmmo.ordinal()] = bundle.getInt(res.getString(R.string.FTAmmo));
            buttonVars[ItemsList.NumberLives.ordinal()] = bundle.getInt(res.getString(R.string.NumLife));
            //Used to set the current active weapons
            switch (bundle.getInt(res.getString(R.string.PrimaryWeapon))){
                case 0:
                    buttonVars[MachineGun.ordinal()] = 1;
                    break;
                case 1:
                    buttonVars[ItemsList.MissileLauncher.ordinal()] = 1;
                    break;
                case 2:
                    buttonVars[ItemsList.LaserBeam.ordinal()] = 1;
                    break;
                case 3:
                    buttonVars[ItemsList.MissileLauncher.ordinal()] = 1;
                    break;
                case 4:
                    break;
            }
            switch (bundle.getInt(res.getString(R.string.SecondaryWeapon))){
                case 0:
                    if(buttonVars[MachineGun.ordinal()] != 1)
                        buttonVars[MachineGun.ordinal()] = 2;
                    break;
                case 1:
                    if(buttonVars[MachineGun.ordinal()] != 1)
                        buttonVars[ItemsList.MissileLauncher.ordinal()] = 2;
                    break;
                case 2:
                    if(buttonVars[MachineGun.ordinal()] != 1)
                        buttonVars[ItemsList.LaserBeam.ordinal()] = 2;
                    break;
                case 3:
                    if(buttonVars[MachineGun.ordinal()] != 1)
                        buttonVars[ItemsList.MissileLauncher.ordinal()] = 2;
                    break;
                case 4:
                    break;
            }
        }else{
            //Used in case the fragment started but no data was passed
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

    /** PURPOSE:    Return's the value in primarySelectorActive
     *  INPUT:      NONE
     *  OUTPUT:     Return a boolean value
     */
    public boolean getPrimarySelectorActive(){
        return primarySelectorActive;
    }
}

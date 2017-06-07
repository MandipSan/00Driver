package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.GameObjects.Items;
import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.UpgradeImageAdapter;



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
    //  PURPOSE:    Holds whether these type of weapon have been unlocked
    private boolean mLUnlocked, lBUnlocked, fTUnlocked;
    //  PURPOSE:    Holds the adapter for the grid buttons
    private UpgradeImageAdapter adapter;
    //  PURPOSE:    Holds the grid
    private GridView gridview;
    //  PURPOSE:    Holds button images int reference
    public int [] buttonImages;
    //  PURPOSE:    Holds the names and what each button in the grid is
    public enum ItemsList{NumberLives, MaxHealth, MachineGunDamage, MissileLauncherDamage, LaserBeamDamage,
        FlameThrowerDamage, MachineGunMaxAmmo, MissileLauncherMaxAmmo, LaserBeamMaxAmmo,
        FlameThrowerMaxAmmo, FillMachineGunAmmo, FillMissileLauncherAmmo, FillLaserBeamAmmo,
        FillFlameThrowerAmmo,SelectPrimaryWeapon,SelectSecondaryWeapon,MachineGun, MissileLauncher,
        LaserBeam, FlameThrower}

    public static UpgradeScreen newInstance(){
        return new UpgradeScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        final Resources res = getResources();
        primarySelectorActive = true;
        if(buttonVars == null||buttonVars[0] == -1)((MainActivity)getActivity()).changeFrags("StartScreen");
        View view = inflater.inflate(R.layout.upgrade_screen,container,false);
        final TextView textView = ((TextView)view.findViewById(R.id.Score));
        adapter = new UpgradeImageAdapter(getActivity(),this);
        gridview = (GridView) view.findViewById(R.id.UpgradeMenu);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                boolean dropThrough = false;
                switch (ItemsList.values()[position]){
                    case NumberLives:
                        updateButton(position,res.getInteger(R.integer.NewLifeCost), res.
                                getInteger(R.integer.NewLifeIncreaseBy));
                        break;
                    case MaxHealth:
                        updateButton(position,res.getInteger(R.integer.IncreaseMaxHealthCost), res.
                                getInteger(R.integer.IncreaseMaxHealthIncreaseBy));
                        break;
                    case MachineGunDamage:
                        for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                            if (buttonVars[i] == 1) buttonVars[i] = 0;
                        }
                        buttonVars[ItemsList.MachineGun.ordinal()] = 1;
                        updateButton(position,res.getInteger(R.integer.MGDamageCost), res.
                                getInteger(R.integer.MGDamageIncreaseBy));
                        break;
                    case MissileLauncherDamage:
                        for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                            if (buttonVars[i] == 1) buttonVars[i] = 0;
                        }
                        buttonVars[ItemsList.MissileLauncher.ordinal()] = 1;
                        updateButton(position,res.getInteger(R.integer.MLDamageCost), res.
                                getInteger(R.integer.MLDamageIncreaseBy));
                        if(!mLUnlocked) {
                            buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()] =  res.
                                    getInteger(R.integer.MLMaxAmmoIncreaseBy);
                            buttonVars[ItemsList.FillMissileLauncherAmmo.ordinal()] =  res.
                                    getInteger(R.integer.MLMaxAmmoIncreaseBy);
                            mLUnlocked = true;
                        }
                        break;
                    case LaserBeamDamage:
                        for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                            if (buttonVars[i] == 1) buttonVars[i] = 0;
                        }
                        buttonVars[ItemsList.LaserBeam.ordinal()] = 1;
                        updateButton(position,res.getInteger(R.integer.LBDamageCost), res.
                                getInteger(R.integer.LBDamageIncreaseBy));
                        if(!lBUnlocked) {
                            buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()] = res.
                                    getInteger(R.integer.LBMaxAmmoIncreaseBy);
                            buttonVars[ItemsList.FillLaserBeamAmmo.ordinal()] = res.
                                    getInteger(R.integer.LBMaxAmmoIncreaseBy);
                            lBUnlocked = true;
                        }
                        break;
                    case FlameThrowerDamage:
                        for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                            if (buttonVars[i] == 1) buttonVars[i] = 0;
                        }
                        buttonVars[ItemsList.FlameThrower.ordinal()] = 1;
                        updateButton(position,res.getInteger(R.integer.FTDamageCost), res.
                                getInteger(R.integer.FTDamageIncreaseBy));
                        if(!fTUnlocked) {
                            buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()] = res.
                                    getInteger(R.integer.FTMaxAmmoIncreaseBy);
                            buttonVars[ItemsList.FillFlameThrowerAmmo.ordinal()] = res.
                                    getInteger(R.integer.FTMaxAmmoIncreaseBy);
                            fTUnlocked = true;
                        }
                        break;
                    case MachineGunMaxAmmo:
                        updateButton(position,res.getInteger(R.integer.MGMaxAmmoCost), res.
                                getInteger(R.integer.MGMaxAmmoIncreaseBy));
                        break;
                    case MissileLauncherMaxAmmo:
                        if(mLUnlocked)updateButton(position,res.getInteger(R.integer.MLMaxAmmoCost), res.
                                getInteger(R.integer.MLMaxAmmoIncreaseBy));
                        else Toast.makeText(getActivity(), "Weapon not unlocked",
                                    Toast.LENGTH_SHORT).show();
                        break;
                    case LaserBeamMaxAmmo:
                        if(lBUnlocked)updateButton(position,res.getInteger(R.integer.LBMaxAmmoCost), res.
                                getInteger(R.integer.LBMaxAmmoIncreaseBy));
                        else Toast.makeText(getActivity(), "Weapon not unlocked",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case FlameThrowerMaxAmmo:
                        if(fTUnlocked)updateButton(position,res.getInteger(R.integer.FTMaxAmmoCost), res.
                                getInteger(R.integer.FTMaxAmmoIncreaseBy));
                        else Toast.makeText(getActivity(), "Weapon not unlocked",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case FillMachineGunAmmo:
                        if(buttonVars[position] <
                                buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()]){
                            updateButton(position,res.getInteger(R.integer.MGAmmoCost),0);
                            buttonVars[position] = buttonVars[ItemsList.MachineGunMaxAmmo.ordinal()];
                        }else{
                            Toast.makeText(getActivity(), "Ammo full",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case FillMissileLauncherAmmo:
                        if(buttonVars[position] <
                                buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()]){
                            updateButton(position,res.getInteger(R.integer.MLAmmoCost),0);
                            buttonVars[position] = buttonVars[ItemsList.MissileLauncherMaxAmmo.ordinal()];
                        }else{
                            if(!mLUnlocked) {
                                Toast.makeText(getActivity(), "Weapon not unlocked",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Ammo full",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case FillLaserBeamAmmo:
                        if(buttonVars[position] <
                                buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()]){
                            updateButton(position,res.getInteger(R.integer.LBAmmoCost),0);
                            buttonVars[position] = buttonVars[ItemsList.LaserBeamMaxAmmo.ordinal()];
                        }else{
                            if(!lBUnlocked) {
                                Toast.makeText(getActivity(), "Weapon not unlocked",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Ammo full",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case FillFlameThrowerAmmo:
                        if(buttonVars[position] <
                                buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()]){
                            updateButton(position,res.getInteger(R.integer.FTAmmoCost),0);
                            buttonVars[position] = buttonVars[ItemsList.FlameThrowerMaxAmmo.ordinal()];
                        }else{
                            if(!fTUnlocked) {
                                Toast.makeText(getActivity(), "Weapon not unlocked",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Ammo full",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case SelectPrimaryWeapon:
                        primarySelectorActive = true;
                        break;
                    case SelectSecondaryWeapon:
                        primarySelectorActive = false;
                        break;
                    case FlameThrower:
                        if(!fTUnlocked){
                            Toast.makeText(getActivity(), "Weapon not unlocked",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                        dropThrough = true;
                    case MissileLauncher:
                        if(!mLUnlocked && !dropThrough){
                            Toast.makeText(getActivity(), "Weapon not unlocked",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                        dropThrough = true;
                    case LaserBeam:
                        if(!lBUnlocked && !dropThrough){
                            Toast.makeText(getActivity(), "Weapon not unlocked",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        }
                    case MachineGun:
                        //Used to determine which weapon was activate
                        if (primarySelectorActive) {
                            for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                                if (buttonVars[i] == 1) buttonVars[i] = 0;
                            }
                            buttonVars[position] = 1;
                        } else {
                            if (buttonVars[position] != 1) {
                                for (int i = ItemsList.MachineGun.ordinal(); i < ItemsList.values().length; i++) {
                                    if (buttonVars[i] == 2) buttonVars[i] = 0;
                                }
                                buttonVars[position] = 2;
                            }
                        }
                        break;
                }

                textView.setText("Score: "+Score);
                adapter.notifyDataSetChanged();
                gridview.invalidateViews();
            }
        });

        Button button = (Button)view.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
                for(int i = ItemsList.MachineGun.ordinal(); i != ItemsList.values().length; i++){
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
        //Used as a check and to set array variables if they weren't initialized
        initialVarLoad();
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
                    buttonVars[ItemsList.MachineGun.ordinal()] = 1;
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
                    if(buttonVars[ItemsList.MachineGun.ordinal()] != 1)
                        buttonVars[ItemsList.MachineGun.ordinal()] = 2;
                    break;
                case 1:
                    if(buttonVars[ItemsList.MissileLauncher.ordinal()] != 1)
                        buttonVars[ItemsList.MissileLauncher.ordinal()] = 2;
                    break;
                case 2:
                    if(buttonVars[ItemsList.LaserBeam.ordinal()] != 1)
                        buttonVars[ItemsList.LaserBeam.ordinal()] = 2;
                    break;
                case 3:
                    if(buttonVars[ItemsList.MissileLauncher.ordinal()] != 1)
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

    /** PURPOSE:    Updates the button values
     *  INPUT:      position            - The position button is in
     *              increaseCostBy      - The new amount the cost is to increase by
     *              increaseValueBy     - The new amount the value is to increase by
     *  OUTPUT:     NONE
     */
    private void updateButton(int position, int increaseCostBy, int increaseValueBy){
        if(Score - costValues[position] > 0){
            Score -= costValues[position];
            buttonVars[position] += increaseValues[position];
            increaseValues[position] += increaseValueBy;
            costValues[position] += increaseCostBy;
        }
    }

    /** PURPOSE:    Setup the initial variable value if the variables not loaded
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void initialVarLoad(){
        Resources res = GameGlobals.getInstance().getImageResources();
        if(buttonVars == null){
            buttonVars = new int[ItemsList.values().length];
            increaseValues = new int[ItemsList.values().length];
            costValues =  new int[ItemsList.values().length];
            buttonImages = new int[ItemsList.values().length];
            for(int i  = 0; i < ItemsList.values().length; i++){
                buttonVars[i] = 0;
                buttonImages[i] = R.drawable.healthbox;
                //increaseValues[i] = 100;
                //costValues[i] = 100;
            }
            increaseValues[ItemsList.MaxHealth.ordinal()] = res.getInteger(R.integer.IncreaseMaxHealthIncreaseBy);
            costValues[ItemsList.MaxHealth.ordinal()] = res.getInteger(R.integer.IncreaseMaxHealthCost);
            buttonImages[ItemsList.MaxHealth.ordinal()] = R.drawable.ugmaxhealth;
            increaseValues[ItemsList.MachineGunDamage.ordinal()] = res.getInteger(R.integer.MGDamageIncreaseBy);
            costValues[ItemsList.MachineGunDamage.ordinal()] = res.getInteger(R.integer.MGDamageCost);
            buttonImages[ItemsList.MachineGunDamage.ordinal()] = R.drawable.ugmachinegun;
            increaseValues[ItemsList.MissileLauncherDamage.ordinal()] = res.getInteger(R.integer.MLDamageIncreaseBy);
            costValues[ItemsList.MissileLauncherDamage.ordinal()] = res.getInteger(R.integer.MLDamageCost);
            buttonImages[ItemsList.MissileLauncherDamage.ordinal()] = R.drawable.ugmissile;
            increaseValues[ItemsList.LaserBeamDamage.ordinal()] = res.getInteger(R.integer.LBDamageIncreaseBy);
            costValues[ItemsList.LaserBeamDamage.ordinal()] = res.getInteger(R.integer.LBDamageCost);
            buttonImages[ItemsList.LaserBeamDamage.ordinal()] = R.drawable.uglaser;
            increaseValues[ItemsList.FlameThrowerDamage.ordinal()] = res.getInteger(R.integer.FTDamageIncreaseBy);
            costValues[ItemsList.FlameThrowerDamage.ordinal()] = res.getInteger(R.integer.FTDamageCost);
            buttonImages[ItemsList.FlameThrowerDamage.ordinal()] = R.drawable.ugflamethrower;
            increaseValues[ItemsList.MachineGunMaxAmmo.ordinal()] = res.getInteger(R.integer.MGMaxAmmoIncreaseBy);
            costValues[ItemsList.MachineGunMaxAmmo.ordinal()] = res.getInteger(R.integer.MGMaxAmmoCost);
            buttonImages[ItemsList.MachineGunMaxAmmo.ordinal()] = R.drawable.ugmachinegunmaxammo;
            increaseValues[ItemsList.MissileLauncherMaxAmmo.ordinal()] = res.getInteger(R.integer.MLMaxAmmoIncreaseBy);
            costValues[ItemsList.MissileLauncherMaxAmmo.ordinal()] = res.getInteger(R.integer.MLMaxAmmoCost);
            buttonImages[ItemsList.MissileLauncherMaxAmmo.ordinal()] = R.drawable.ugmissilemaxammo;
            increaseValues[ItemsList.LaserBeamMaxAmmo.ordinal()] = res.getInteger(R.integer.LBMaxAmmoIncreaseBy);
            costValues[ItemsList.LaserBeamMaxAmmo.ordinal()] = res.getInteger(R.integer.LBMaxAmmoCost);
            buttonImages[ItemsList.LaserBeamMaxAmmo.ordinal()] = R.drawable.uglasermaxammo;
            increaseValues[ItemsList.FlameThrowerMaxAmmo.ordinal()] = res.getInteger(R.integer.FTMaxAmmoIncreaseBy);
            costValues[ItemsList.FlameThrowerMaxAmmo.ordinal()] = res.getInteger(R.integer.FTMaxAmmoCost);
            buttonImages[ItemsList.FlameThrowerMaxAmmo.ordinal()] = R.drawable.ugflamethrowermaxammo;
            increaseValues[ItemsList.FillMachineGunAmmo.ordinal()] = 0;
            costValues[ItemsList.FillMachineGunAmmo.ordinal()] = res.getInteger(R.integer.MGAmmoCost);
            buttonImages[ItemsList.FillMachineGunAmmo.ordinal()] = R.drawable.ugfillmachinegunammo;
            increaseValues[ItemsList.FillMissileLauncherAmmo.ordinal()] = 0;
            costValues[ItemsList.FillMissileLauncherAmmo.ordinal()] = res.getInteger(R.integer.MLAmmoCost);
            buttonImages[ItemsList.FillMissileLauncherAmmo.ordinal()] = R.drawable.ugfillmissileammo;
            increaseValues[ItemsList.FillLaserBeamAmmo.ordinal()] = 0;
            costValues[ItemsList.FillLaserBeamAmmo.ordinal()] = res.getInteger(R.integer.LBAmmoCost);
            buttonImages[ItemsList.FillLaserBeamAmmo.ordinal()] = R.drawable.ugfilllaserammo;
            increaseValues[ItemsList.FillFlameThrowerAmmo.ordinal()] = 0;
            costValues[ItemsList.FillFlameThrowerAmmo.ordinal()] = res.getInteger(R.integer.FTAmmoCost);
            buttonImages[ItemsList.FillFlameThrowerAmmo.ordinal()] = R.drawable.ugfillflamethrowerammo;
            increaseValues[ItemsList.NumberLives.ordinal()] = res.getInteger(R.integer.NewLifeIncreaseBy);
            costValues[ItemsList.NumberLives.ordinal()] = res.getInteger(R.integer.IncreaseMaxHealthCost);
            buttonImages[ItemsList.NumberLives.ordinal()] = R.drawable.ugnumberlives;
            increaseValues[ItemsList.FillMissileLauncherAmmo.ordinal()] = 0;
            costValues[ItemsList.FillMissileLauncherAmmo.ordinal()] = res.getInteger(R.integer.MLAmmoCost);
            increaseValues[ItemsList.FillLaserBeamAmmo.ordinal()] = 0;
            costValues[ItemsList.FillLaserBeamAmmo.ordinal()] = res.getInteger(R.integer.LBAmmoCost);
            increaseValues[ItemsList.FillFlameThrowerAmmo.ordinal()] = 0;
            costValues[ItemsList.FillFlameThrowerAmmo.ordinal()] = res.getInteger(R.integer.FTAmmoCost);
            increaseValues[ItemsList.NumberLives.ordinal()] = res.getInteger(R.integer.NewLifeIncreaseBy);
            costValues[ItemsList.NumberLives.ordinal()] = res.getInteger(R.integer.IncreaseMaxHealthCost);
            mLUnlocked = lBUnlocked = fTUnlocked = false;
        }
    }
}

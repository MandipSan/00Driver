package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import abyssproductions.double0driver.GameMenu.UpgradeScreen;
import abyssproductions.double0driver.R;

import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.FlameThrowerMaxAmmo;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.LaserBeamMaxAmmo;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.MachineGun;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.MachineGunMaxAmmo;
import static abyssproductions.double0driver.GameMenu.UpgradeScreen.ItemsList.MissileLauncherMaxAmmo;

/**
 * Created by Mandip Sangha on 4/7/2017.
 */

public class UpgradeImageAdapter extends BaseAdapter {
    private Context context;
    private UpgradeScreen upScreen;

    public UpgradeImageAdapter(Context c, UpgradeScreen screen) {
        context = c;
        upScreen = screen;
    }

    public int getCount() {
        return UpgradeScreen.ItemsList.values().length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        String itemString;
        String costString;
        View view;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = inflater.inflate(R.layout.single_grid_tile, null);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(R.id.Items);
        UpgradeScreen.ItemsList item = UpgradeScreen.ItemsList.values()[position];
        itemString = "Increase: " + item.toString() + " " + upScreen.getCurrentValue(position) +
                " by " + upScreen.getIncreaseValue(position);
        costString = "Cost: " + upScreen.getCost(position);
        view.setBackgroundResource(upScreen.buttonImages[position]);

        if(position >= UpgradeScreen.ItemsList.FillMachineGunAmmo.ordinal() &&
                position <= UpgradeScreen.ItemsList.FillFlameThrowerAmmo.ordinal()) {
            itemString = item.toString() + " " + upScreen.getCurrentValue(position) +
                    " to ammo max "+ upScreen.getCurrentValue(position-4);
            //Sets whether primary weapon selector is active or not button
        }else if(position == UpgradeScreen.ItemsList.SelectPrimaryWeapon.ordinal()){
            costString = "";
            view.setBackgroundResource(0);
            if(upScreen.getPrimarySelectorActive()){
                itemString = "Primary weapon selector active";
                view.setBackgroundColor(Color.GREEN);
            }else{
                itemString = "Primary weapon selector not active";
                view.setBackgroundColor(Color.WHITE);
            }
            //Sets whether secondary weapon selector is active or not button
        }else if(position == UpgradeScreen.ItemsList.SelectSecondaryWeapon.ordinal()){
            costString = "";
            view.setBackgroundResource(0);
            if(upScreen.getPrimarySelectorActive()){
                itemString = "Secondary weapon selector not active";
                view.setBackgroundColor(Color.WHITE);
            }else{
                itemString = "Secondary weapon selector active";
                view.setBackgroundColor(Color.GREEN);
            }
        }else if (position >= UpgradeScreen.ItemsList.SelectSecondaryWeapon.ordinal()) {
            costString = "";
            view.setBackgroundResource(0);
            //Sets the active weapons buttons value
            if(upScreen.getCurrentValue(position) == 1){
                itemString = "Current Active Primary Weapon " + item.toString();
                view.setBackgroundColor(Color.CYAN);
            }else if(upScreen.getCurrentValue(position) == 2){
                itemString = "Current Active Secondary Weapon " + item.toString();
                view.setBackgroundColor(Color.YELLOW);
            }else{
                itemString = "Weapon Not Selected " + item.toString();
                view.setBackgroundColor(Color.WHITE);
            }
        }
        textView.setText(itemString);
        textView = (TextView) view.findViewById(R.id.Cost);
        textView.setText(costString);
        return view;
    }
}

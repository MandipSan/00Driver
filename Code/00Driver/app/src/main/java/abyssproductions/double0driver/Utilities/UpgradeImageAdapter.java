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
        String temp;
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
        //Sets up the basic buttons
        if(position < UpgradeScreen.ItemsList.SelectPrimaryWeapon.ordinal()) {
            view.setBackgroundResource(R.drawable.healthbox);
            temp = "Increase: " + item.toString() + " " + upScreen.getCurrentValue(position) +
                    " by " + upScreen.getIncreaseValue(position);
            textView.setText(temp);
            textView = (TextView) view.findViewById(R.id.Cost);
            textView.setText("Cost: " + upScreen.getCost(position));
        //Sets whether primary weapon selector is active or not button
        }else if(position == UpgradeScreen.ItemsList.SelectPrimaryWeapon.ordinal()){
            if(upScreen.getPrimarySelectorActive()){
                temp = "Primary weapon selector active";
                view.setBackgroundColor(Color.GREEN);
            }else{
                temp = "Primary weapon selector not active";
                view.setBackgroundColor(Color.WHITE);
            }
            textView.setText(temp);
            textView = (TextView) view.findViewById(R.id.Cost);
            textView.setText("");
        //Sets whether secondary weapon selector is active or not button
        }else if(position == UpgradeScreen.ItemsList.SelectSecondaryWeapon.ordinal()){
            if(upScreen.getPrimarySelectorActive()){
                temp = "Secondary weapon selector not active";
                view.setBackgroundColor(Color.WHITE);
            }else{
                temp = "Secondary weapon selector active";
                view.setBackgroundColor(Color.GREEN);
            }
            textView.setText(temp);
            textView = (TextView) view.findViewById(R.id.Cost);
            textView.setText("");
        }else {
            //Sets the active weapons buttons value
            if(upScreen.getCurrentValue(position) == 1){
                view.setBackgroundColor(Color.CYAN);
                temp = "Current Active Primary Weapon " + item.toString();
            }else if(upScreen.getCurrentValue(position) == 2){
                view.setBackgroundColor(Color.YELLOW);
                temp = "Current Active Secondary Weapon " + item.toString();
            }else{
                view.setBackgroundColor(Color.WHITE);
                temp = "Weapon Not Selected " + item.toString();
            }
            textView.setText(temp);
            textView = (TextView) view.findViewById(R.id.Cost);
            textView.setText("");
        }
        return view;
    }
}

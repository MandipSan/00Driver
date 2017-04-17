package abyssproductions.double0driver.Utilities;

import android.content.Context;
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
        View view;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = inflater.inflate(R.layout.single_grid_tile, null);
        } else {
            view = convertView;
        }
        view.setBackgroundResource(R.drawable.healthbox);
        TextView textView = (TextView) view.findViewById(R.id.Items);
        UpgradeScreen.ItemsList item = UpgradeScreen.ItemsList.values()[position];
        String temp = "Increase: " + item.toString() + " " + upScreen.getCurrentValue(position) +
                " by " + upScreen.getIncreaseValue(position);
        textView.setText(temp);
        textView = (TextView) view.findViewById(R.id.Cost);
        textView.setText("Cost: " + upScreen.getCost(position));
        return view;
    }

}

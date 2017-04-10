package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 4/7/2017.
 */

public class UpgradeImageAdapter extends BaseAdapter {
    private Context context;
    private enum ItemsList{Health, MachineGunDamage, MissileLauncherDamage, LaserBeamDamage,
        FlameThrowerDamage, MachineGunAmmo, MissileLauncherAmmo, LaserBeamAmmo, FlameThrowerAmmo,
        FillMachineGunAmmo, FillMissileLauncherAmmo, FillLaserBeamAmmo, FillFlameThrowerAmmo,};

    public UpgradeImageAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return ItemsList.values().length;
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
            view.setBackgroundResource(R.drawable.ammobox);
            TextView textView = (TextView) view.findViewById(R.id.Items);
            ItemsList item =ItemsList.values()[position];
            textView.setText(item.toString());
        } else {
            view = convertView;
        }
        return view;
    }

}

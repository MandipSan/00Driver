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
import android.widget.Toast;

import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.UpgradeImageAdapter;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class UpgradeScreen extends Fragment {
    private int Score;
    public static UpgradeScreen newInstance(){
        return new UpgradeScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.upgrade_screen,container,false);
        GridView gridview = (GridView) view.findViewById(R.id.UpgradeMenu);
        gridview.setAdapter(new UpgradeImageAdapter(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = (Button)getView().findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).changeFrags("StartScreen");
            }
        });

        ((TextView)getView().findViewById(R.id.Score)).setText("Score: "+Score);
    }

    /** PURPOSE:    To get the data need for the fragment
     *  INPUT:      bundle              - The data being passed to the fragment
     *  OUTPUT:     NONE
     */
    public void passData(Bundle bundle){
        if(bundle != null){
            Score = bundle.getInt("score");

        }
    }
}

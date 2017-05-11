package abyssproductions.double0driver.GameMenu;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class HelpScreen extends Fragment {
    public static HelpScreen newInstance(){
        return new HelpScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.help_screen,container,false);
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
    }
}

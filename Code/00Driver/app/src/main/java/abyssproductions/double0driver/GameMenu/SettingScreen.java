package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

import abyssproductions.double0driver.GameGlobals;
import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;
import abyssproductions.double0driver.Utilities.SoundEffects;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class SettingScreen extends Fragment {
    public static SettingScreen newInstance(){
        return new SettingScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.setting_screen,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final GameGlobals instance = GameGlobals.getInstance();
        if(instance.mySoundEffects == null)instance.mySoundEffects = new SoundEffects(getContext());
        final View view = getView();
        Button button = (Button)view.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).changeFrags("StartScreen");
            }
        });

        //Sound Effect Volume related objects
        SeekBar bar = (SeekBar) view.findViewById(R.id.soundEffectSeekBar);
        bar.setMax(100);
        bar.setProgress((int)(instance.mySoundEffects.getVolumeLevel()*100));
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                instance.mySoundEffects.changeVolume(progress/100);
            }
        });
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.muteSoundEffect);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.mySoundEffects.mute(((CheckBox) v).isChecked());
                ((SeekBar) view.findViewById(R.id.soundEffectSeekBar)).
                        setProgress((int)(instance.mySoundEffects.getVolumeLevel()*100));

            }
        });

        //Music Volume related objects
        bar = (SeekBar) view.findViewById(R.id.musicSeekBar);
        bar.setMax(100);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        checkBox = (CheckBox)view.findViewById(R.id.muteMusic);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    ((SeekBar) view.findViewById(R.id.musicSeekBar)).setProgress(0);
                }else{

                }

            }
        });

        checkBox = (CheckBox)view.findViewById(R.id.healthCheckBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.setDisplayMiniHealthBar(((CheckBox) v).isChecked());
            }
        });
    }
}

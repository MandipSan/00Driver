package abyssproductions.double0driver.GameMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import abyssproductions.double0driver.GameEngine.GameSurfaceView;
import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */


public class GameScreen extends Fragment implements GameSurfaceView.GameOver {
    private GameSurfaceView game;
    private ViewGroup layout;

    public static GameScreen newInstance(){
        return new GameScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(game == null) {
            View view = inflater.inflate(R.layout.game_screen,container,false);
            layout = (ViewGroup)view.findViewById(R.id.game_screen);

            game = new GameSurfaceView(getContext(), this);
            game.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(game);
        }else{
            game.reset();
        }
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();

    }

    @Override
    public void gameOver(int score){
        Bundle bundle = new Bundle();
        bundle.putInt("score",score);
        ((MainActivity)getActivity()).changeFrags("StartScreen");
    }
}

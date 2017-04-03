package abyssproductions.double0driver.GameMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 3/31/2017.
 */

public class HighScoreScreen extends Fragment {
    private int newScore;
    private int [] scores;
    private String [] names;

    public static HighScoreScreen newInstance(){
        return new HighScoreScreen();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        scores = new int[10];
        names = new String[10];
        for(int i = 0; i < scores.length; i++){
            scores[i] = 0;
            names[i] = "NONAME";
        }
        return inflater.inflate(R.layout.highscore_screen,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadToArray();
        if(newScore != -1)adjustScore();
        saveToFile();
        displayHighscore();
        Button button = (Button)getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).changeFrags("StartScreen");
            }
        });
    }

    /** PURPOSE:    To get the data need for the fragment
     *  INPUT:      bundle              - The data being passed to the fragement
     *  OUTPUT:     NONE
     */
    public void passData(Bundle bundle){
        if(bundle != null) newScore = bundle.getInt("score");
        else newScore = -1;
    }

    /** PURPOSE:    Saves the high scores to internal store
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void saveToFile(){
        FileOutputStream outputStream;
        try{
            outputStream = getContext().openFileOutput("HighScore", Context.MODE_PRIVATE);
            for(int i = 0; i < names.length; i++){
                outputStream.write(names[i].getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write(scores.toString().getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        }catch (Exception e){

        }
    }

    /** PURPOSE:    Load the high scores to internal store
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void loadToArray(){
        FileInputStream inputStream;
        InputStreamReader streamReader;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String temp;
        int arrayPos = 0;
        boolean switchInput = true;

        try{
            inputStream = getContext().openFileInput("HighScore");
            streamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(streamReader);

            while((temp = bufferedReader.readLine()) != null){
                //stringBuilder = new StringBuilder();
                if(switchInput){
                    names[arrayPos] = temp;
                    switchInput = false;
                }else{
                    scores[arrayPos] = Integer.parseInt(temp);
                    arrayPos++;
                    switchInput = true;
                }
                if(arrayPos == names.length)break;

            }
            bufferedReader.close();
            streamReader.close();
            inputStream.close();

        }catch (Exception e){

        }
    }

    /** PURPOSE:    Readjusts the high scores if new score is better
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    private void adjustScore(){
        int tempScore;
        String tempName;
        int curScore = newScore;
        String curName = "NONAME";

        for(int i = 0; i < scores.length; i++){
            if(scores[i] < curScore){
                if(curScore == newScore){
                    NameInputDialogFragment dialog = new NameInputDialogFragment();
                    dialog.show(getActivity().getSupportFragmentManager(),"input");
                    if(!dialog.isVisible()){
                        curName = dialog.getInput();
                    }
                }
                tempScore = scores[i];
                tempName = names[i];
                scores[i] = curScore;
                names[i] = curName;
                curScore = tempScore;
                curName = tempName;
            }
        }
    }

    /** PURPOSE:    Displays the high scores
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void displayHighscore(){
        String temp;
        for(int i = 0; i < scores.length; i++){
            temp = names[i] + scores[i]+"\n";
            TextView text = (TextView) getView().findViewById(R.id.highScoreBox);
            text.setText(text.getText()+temp);
        }
    }

    public static class NameInputDialogFragment extends DialogFragment {
        private String input;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            input = "NONAME";

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.name_input_dialog,null))
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText temp = (EditText) getView().findViewById(R.id.namebox);
                        input = temp.toString();
                    }
                })
                .setTitle("Enter Name");

            // Create the AlertDialog object and return it
            return builder.create();
        }

        public String getInput(){
            return input;
        }
    }
}

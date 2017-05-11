package abyssproductions.double0driver.GameMenu;

<<<<<<< HEAD
import android.app.Activity;
=======
>>>>>>> a54c1b12b92b0426c65c138d852927e5add9aeb7
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.Button;


import abyssproductions.double0driver.MainActivity;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 3/25/2017.
 */

public class CreditScreen extends Fragment {


    public static CreditScreen newInstance(){
        return new CreditScreen();
    }
    private ListView creditsView ;
    private ArrayAdapter<String> creditsAdapter ;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Find the ListView resource.
        creditsView = (ListView) getActivity().findViewById( R.id.creditsListView );

        // Create and populate a List of planet names.
        String[] creditRows = new String[] {
                "Game Engine : Mandip Sangha",
                "Game Objects : Mandip Sangha",
                "Projectile Objects : Mark Reffel",
                "Game Menus : Mark Reffel",
                "Credits Page : Mark Reffel",
                "Music By : Drive by Alex (c) copyright 2013 Licensed under a Creative Commons Attribution (3.0) license. http://dig.ccmixter.org/files/AlexBeroza/43098 Ft: cdk & Darryl J"
        };
        ArrayList<String> creditsList = new ArrayList<String>();
        creditsList.addAll( Arrays.asList(creditRows));


        // Create ArrayAdapter using the planet list.
        creditsAdapter = new ArrayAdapter<String>(getContext(), R.layout.credits_row, creditsList);

        // Set the ArrayAdapter as the ListView's adapter.
        creditsView.setAdapter( creditsAdapter );
        return inflater.inflate(R.layout.credit_screen,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = (Button)getView().findViewById(R.id.backC);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).changeFrags("StartScreen");
            }
        });
    }
}

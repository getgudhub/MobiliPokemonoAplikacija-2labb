package com.byethost12.kitm.mobiliaplikacija;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PokemonTableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_table);

        final DatabaseSQLite db = new DatabaseSQLite(this);
        Button perziuraBtn = (Button) findViewById(R.id.btnPerziura);

        perziuraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  ArrayList<String> pokes = new ArrayList<String>();
                pokes.addAll(db.getAllPokemons());
                for(String str : pokes){
                    DisplayPokes();
                }*/

                Cursor c = db.getAllPokes();
                if (c.moveToFirst()&& c.getCount() >0 && c!= null) {
                    do {
                        DisplayContact(c);
                    } while (c.moveToNext());
                }else{
                    DisplayContact(c);
                }
                db.close();

            }
        });
    }

    public void DisplayContact(Cursor c) {
        Toast.makeText(
                this,
                        "id: " + c.getString(0)
                        + "\n" + "Name: " + c.getString(1)
                        + "\n" + "Type:  " + c.getString(2)
                        + "\n" + "Abilities: "+ c.getString(3)
                        + "\n" + "Cp: " + c.getString(4)
                        + "\n" + "Weight:  " + c.getString(5)
                        + "\n" + "Height: "+ c.getString(6),
                Toast.LENGTH_LONG)
                .show();
    }
    public void DisplayPokes(ArrayList c) {
        Toast.makeText(
                this,
                        "id: " + c.getString(0)
                        + "\n" + "Name: " + c.getString(1)
                        + "\n" + "Type:  " + c.getString(2)
                        + "\n" + "Abilities: "+ c.getString(3)
                        + "\n" + "Cp: " + c.getString(4)
                        + "\n" + "Weight:  " + c.getString(5)
                        + "\n" + "Height: "+ c.getString(6),
                Toast.LENGTH_LONG)
                .show();
    }

}

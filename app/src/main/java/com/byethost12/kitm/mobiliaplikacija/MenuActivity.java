package com.byethost12.kitm.mobiliaplikacija;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnPrideti;
    Button btnPerziureti;
    Button searchBtn;
    Button deleteBtn;
    Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        setTitle(R.string.choice_label);

        final DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(this);
        Button perziuraBtn = (Button) findViewById(R.id.btnPerziuraRecycler);
        deleteBtn = (Button) findViewById(R.id.btnTrinimas);
        btnPrideti = (Button) findViewById(R.id.btnPrideti);
        btnPerziureti = (Button) findViewById(R.id.btnPerziureti);
        searchBtn = (Button) findViewById(R.id.btnPaieska);
        updateBtn = (Button) findViewById(R.id.btnRedagavimas);

        btnPrideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NewPokemonActivity.class);
                startActivity(intent);
            }
        });

        btnPerziureti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getAllData();
                if (res.getCount() == 0 || res == null) {
                    showMessage("Error", "Nieko nerasta");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Pokemono Id: " + res.getString(0) + "\n");
                    buffer.append("Pokemono Vardas: " + res.getString(1) + "\n");
                    buffer.append("Pokemono Tipas: " + res.getString(2) + "\n");
                    buffer.append("Pokemono Sugebėjimai: " + res.getString(3) + "\n");
                    buffer.append("Pokemono Cp: " + res.getString(4) + "\n");
                    buffer.append("Pokemono Plotis: " + res.getDouble(5) + "kg\n");
                    buffer.append("Pokemono Ūgis: " + res.getDouble(6) + "m\n\n");
                }
                // Show all data
                showMessage("Visi pokemonai", buffer.toString());
            }
        });

        perziuraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PokemonTableActivity.class);
                startActivity(intent);
            }
        });

        /*Toast list ismetimas
        perziuraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.getAllPokes();
                if (c.moveToFirst() && c.getCount() > 0 && c != null) {
                    do {
                        DisplayPokemons(c);
                    } while (c.moveToNext());
                } else {
                    showMessage("error", "Nothing found");
                }
                db.close();

            }
        });
        */

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDeleteActivity = new Intent(MenuActivity.this, PokemonDeleteActivity.class);
                startActivity(goToDeleteActivity);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearchActivity = new Intent(MenuActivity.this, PokemonSearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUpdateActivity = new Intent(MenuActivity.this, PokemonReworkActivity.class);
                startActivity(goToUpdateActivity);
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /*Toast list ismetimas
    public void DisplayPokemons(Cursor c) {

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
    }*/
}

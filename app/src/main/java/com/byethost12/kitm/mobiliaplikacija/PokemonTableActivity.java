package com.byethost12.kitm.mobiliaplikacija;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PokemonTableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_table);

        final DatabaseSQLite db = new DatabaseSQLite(this);
        Button perziuraBtn = (Button) findViewById(R.id.btnPerziura);
        Button perziura2Btn = (Button) findViewById(R.id.btnPerziura2);
        Button deleteBtn = (Button) findViewById(R.id.btnTrinimas);

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
        perziura2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = db.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Pokemon Id: " + res.getString(0) + "\n");
                    buffer.append("Pokemon Name: " + res.getString(1) + "\n");
                    buffer.append("Pokemon Type: " + res.getString(2) + "\n");
                    buffer.append("Pokemon Abilities: " + res.getString(3) + "\n");
                    buffer.append("Pokemon Cp: " + res.getString(4) + "\n");
                    buffer.append("Pokemon Weight: " + res.getDouble(5) + "\n");
                    buffer.append("Pokemon Height: " + res.getDouble(6) + "\n\n");
                }
                // Show all data
                showMessage("All Pokemons", buffer.toString());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToDeleteActivity = new Intent(PokemonTableActivity.this, PokemonDeleteActivity.class);
                startActivity(goToDeleteActivity);
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokemonTableActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

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
    }


}

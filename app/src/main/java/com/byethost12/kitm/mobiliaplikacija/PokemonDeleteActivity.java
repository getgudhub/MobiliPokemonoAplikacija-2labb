package com.byethost12.kitm.mobiliaplikacija;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PokemonDeleteActivity extends Activity{

    EditText etId;
    DatabaseSQLite db;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        etId = (EditText) findViewById(R.id.etId);
        db = new DatabaseSQLite(this);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(etId.getText().toString().equals("") || db.checkId(Integer.parseInt(etId.getText().toString()))== false){
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.invalid_id));
                }else{
                    showToast();
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.deletePokemon(Integer.parseInt(etId.getText().toString()));
                            Toast.makeText(PokemonDeleteActivity.this, "Pokemon deleted", Toast.LENGTH_SHORT).show();
                            Intent goBack = new Intent(PokemonDeleteActivity.this, PokemonTableActivity.class);
                            startActivity(goBack);
                        }
                    });
                }
            }
        });
    }
    public void showToast(){
        db = new DatabaseSQLite(this);
        Pokemonas pok = new Pokemonas();
        pok = db.getDeletePokemonInfo(Integer.parseInt(etId.getText().toString()));
        Toast.makeText(
                this,
                        "id: " + pok.getId()
                        + "\n" + "Name: " + pok.getName()
                        + "\n" + "Type:  " + pok.getType()
                        + "\n" + "Abilities: "+ pok.getAbilities()
                        + "\n" + "Cp: " + pok.getCp()
                        + "\n" + "Weight:  " + pok.getWeight() +" kg"
                        + "\n" + "Height: "+ pok.getHeight() +" m"
                        + "\n\n" + "Norėdami ištrinti pokemoną spauskite trinti mygtuką vėl",
                Toast.LENGTH_LONG)
                .show();
    }
}

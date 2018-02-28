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
    DatabaseSQLitePokemon db;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        etId = (EditText) findViewById(R.id.etId);
        db = new DatabaseSQLitePokemon(this);

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
                            Toast.makeText(PokemonDeleteActivity.this, "Pokemonas ištrintas", Toast.LENGTH_SHORT).show();
                            Intent goBack = new Intent(PokemonDeleteActivity.this, MenuActivity.class);
                            startActivity(goBack);
                        }
                    });
                }
            }
        });
    }
    public void showToast(){
        db = new DatabaseSQLitePokemon(this);
        Pokemonas pok = new Pokemonas();
        pok = db.getByIdPokemonInfo(Integer.parseInt(etId.getText().toString()));
        Toast.makeText(
                this,
                        "id: " + pok.getId()
                        + "\n" + "Vardas: " + pok.getName()
                        + "\n" + "Tipas:  " + pok.getType()
                        + "\n" + "Sugebėjimai: "+ pok.getAbilities()
                        + "\n" + "Cp: " + pok.getCp()
                        + "\n" + "Svoris:  " + pok.getWeight() +" kg"
                        + "\n" + "Ūgis: "+ pok.getHeight() +" m"
                        + "\n\n" + "Norėdami ištrinti pokemoną spauskite trinti mygtuką vėl",
                Toast.LENGTH_LONG)
                .show();
    }
}

package com.byethost12.kitm.mobiliaplikacija;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class PokemonSearchActivity extends Activity {

    Button searchBtn;
    EditText etId;
    EditText etName;
    DatabaseSQLitePokemon db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.search_label);

        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        searchBtn = (Button) findViewById(R.id.searchPokeBtn);
        db = new DatabaseSQLitePokemon(this);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().equals("") && etName.getText().toString().equals("")){
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.empty_fields));
                }else if(!etId.getText().toString().equals("") && db.checkId(Integer.parseInt(etId.getText().toString()))){
                   showPokemonById();
                }else if(!etName.getText().toString().equals("") && db.checkName(etName.getText().toString())){
                    showPokemonByName();
                }else{
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.not_found_pokemon));
                }
            }
        });


    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PokemonSearchActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void showPokemonById() {
        db = new DatabaseSQLitePokemon(this);
        Pokemonas pok = new Pokemonas();
        pok = db.getByIdPokemonInfo(Integer.parseInt(etId.getText().toString()));
        StringBuffer buffer = new StringBuffer();
        buffer.append("Pokemono Vardas: " + pok.getName() + "\n");
        buffer.append("Pokemono Tipas: " + pok.getType() + "\n");
        buffer.append("Pokemono Sugebėjimai: " + pok.getAbilities() + "\n");
        buffer.append("Pokemono Cp: " + pok.getCp() + "\n");
        buffer.append("Pokemono Plotis: " + pok.getWeight() + " kg\n");
        buffer.append("Pokemono Ūgis: " + pok.getHeight() + " m\n");
        buffer.append("user id: " + pok.getUserId() + " \n\n");

        showMessage("Pokemonas su ID: "+pok.getId(), buffer.toString());
    }
    public void showPokemonByName() {
        db = new DatabaseSQLitePokemon(this);
        Pokemonas pok = new Pokemonas();
        pok = db.getByNamePokemonInfo(etName.getText().toString());
        StringBuffer buffer = new StringBuffer();
        buffer.append("Pokemono Id: " + pok.getId() + "\n");
        buffer.append("Pokemono Tipas: " + pok.getType() + "\n");
        buffer.append("Pokemono Sugebėjimai: " + pok.getAbilities() + "\n");
        buffer.append("Pokemono Cp: " + pok.getCp() + "\n");
        buffer.append("Pokemono Plotis: " + pok.getWeight() + " kg\n");
        buffer.append("Pokemono Ūgis: " + pok.getHeight() + " m\n");
        buffer.append("user id: " + pok.getUserId() + " \n\n");

        showMessage("Pokemonas su vardu: "+pok.getName(), buffer.toString());
    }
}

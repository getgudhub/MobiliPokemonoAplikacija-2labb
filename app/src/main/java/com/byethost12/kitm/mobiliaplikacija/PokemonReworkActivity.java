package com.byethost12.kitm.mobiliaplikacija;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class PokemonReworkActivity extends AppCompatActivity {

    private ActionMenuView amvMenu;
    Button updateBtn, deleteBtn;
    EditText  etId, etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbFast, cbInvisible, cbFlying, cbSwimmer, cbThrows;
    Spinner spinner;
    DatabaseSQLitePokemon db;
    Toolbar toolbar;
    MenuItem returnItem;

    int id;
    String name;
    double weight;
    double height;
    String rb = "";
    String checkboxText = "";
    String spinnerText = "";

    Pokemonas pokemonas;

    String items[] = {"Vanduo", "Ugnis", "Tamsa", "Žolytė", "Elektra", "Žemė", "Oras"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);

        rbGroup = (RadioGroup) findViewById(R.id.rbGroup);
        rbStrong = (RadioButton) findViewById(R.id.rbStrong);
        rbMedium = (RadioButton) findViewById(R.id.rbMedium);
        rbWeak = (RadioButton) findViewById(R.id.rbWeak);

        cbSwimmer = (CheckBox) findViewById(R.id.cbSwimmer);
        cbThrows = (CheckBox) findViewById(R.id.cbThrows);
        cbFast = (CheckBox) findViewById(R.id.cbFast);
        cbInvisible = (CheckBox) findViewById(R.id.cbInvisible);
        cbFlying = (CheckBox) findViewById(R.id.cbFlying);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle !=null){
            id = bundle.getInt("id");
            etId.setText(valueOf(id));
            name = bundle.getString("name");
            etName.setText(name);
            weight = bundle.getDouble("weight");
            etWeight.setText(""+weight);
            height = bundle.getDouble("height");
            etHeight.setText(""+height);
            rb = bundle.getString("CP");
              if(rb.equals(rbStrong.getText().toString())){
                  rbStrong.setChecked(true);
              }else if(rb.equals(rbMedium.getText().toString())){
                    rbMedium.setChecked(true);
              }else{
                  rbWeak.setChecked(true);
              }
            checkboxText = bundle.getString("abil");
             setCheckBox(checkboxText);
            spinnerText = bundle.getString("type");
             setSpinnerText(spinnerText);

        }

        updateBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                pokemonas = new Pokemonas();
                db = new DatabaseSQLitePokemon(PokemonReworkActivity.this);

                if(Validation.isValidId(etId.getText().toString())){
                    if(!db.checkId(Integer.parseInt(etId.getText().toString()))){
                        etId.requestFocus();
                        etId.setError(getResources().getString(R.string.id_invalid));
                    }
                }
                if(etId.getText().toString().equals("") || !Validation.isValidId(etId.getText().toString()) ){
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.id_invalid));
                }else if (etName.getText().toString().equals("") || !Validation.isValidPokemonName(etName.getText().toString())) {
                    etName.requestFocus();
                    etName.setError(getResources().getString(R.string.name_invalid));
                } else if (etWeight.getText().toString().equals("") || !Validation.isValidSize(etWeight.getText().toString())) {
                    etWeight.requestFocus();
                    etWeight.setError(getResources().getString(R.string.weight_invalid));
                } else if (etHeight.getText().toString().equals("") || !Validation.isValidSize(etHeight.getText().toString())) {
                    etHeight.requestFocus();
                    etHeight.setError(getResources().getString(R.string.height_invalid));
                } else if (!(cbFlying.isChecked() || cbInvisible.isChecked() || cbThrows.isChecked() || cbFast.isChecked() || cbSwimmer.isChecked())) {
                    cbFlying.requestFocus();
                    cbFlying.setError(getResources().getString(R.string.checkbox_not_checked));
                } else {
                    id = Integer.parseInt(etId.getText().toString());
                    pokemonas.setId(id);
                    name = etName.getText().toString();
                    pokemonas.setName(name);
                    weight = Double.parseDouble(etWeight.getText().toString());
                    pokemonas.setWeight(weight);
                    height = Double.parseDouble(etHeight.getText().toString());
                    pokemonas.setHeight(height);
                    if (rbStrong.isChecked()) {
                        rb = rbStrong.getText().toString();
                    } else if (rbMedium.isChecked()) {
                        rb = rbMedium.getText().toString();
                    } else {
                        rb = rbWeak.getText().toString();
                    }
                    pokemonas.setCp(rb);

                    checkboxText = "";
                    if (cbFlying.isChecked()) {
                        checkboxText = checkboxText + "Skrendantis, ";
                    }
                    if (cbInvisible.isChecked()) {
                        checkboxText = checkboxText + "Nematomumas, ";
                    }
                    if (cbSwimmer.isChecked()) {
                        checkboxText = checkboxText + "Plaukiantis, ";
                    }
                    if (cbThrows.isChecked()) {
                        checkboxText = checkboxText + "Mėtantis sunkius/aštrius daiktus, ";
                    }
                    if (cbFast.isChecked()) {
                        checkboxText = checkboxText + "Greitas, ";
                    }
                    pokemonas.setAbilities(checkboxText);

                    spinnerText = spinner.getSelectedItem().toString();
                    pokemonas.setType(spinnerText);

                    db.updatePokemon(pokemonas);

                    toastMessage(pokemonas);

                    Intent goToSearchActivity = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                    startActivity(goToSearchActivity);

                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                db = new DatabaseSQLitePokemon(PokemonReworkActivity.this);
                if(!Validation.isValidId(etId.getText().toString()) || !db.checkId(Integer.parseInt(etId.getText().toString()))) {
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.invalid_id));
                }else{
                    showDeleteToast();
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.deletePokemon(Integer.parseInt(etId.getText().toString()));
                            Toast.makeText(PokemonReworkActivity.this, "Pokemonas ištrintas", Toast.LENGTH_SHORT).show();
                            Intent goBack = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                            startActivity(goBack);
                        }
                    });
                }
            }
        });

        returnItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pokemon_rework_menu, menu);
        MenuItem returnItem = menu.findItem(R.id.actionBack);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setCheckBox(String cbText){
        if(checkboxText.contains(cbFast.getText().toString()+", ")){
            cbFast.setChecked(true);
        }if(checkboxText.contains(cbFlying.getText().toString()+", ")){
            cbFlying.setChecked(true);
        }if(checkboxText.contains(cbInvisible.getText().toString()+", ")){
            cbInvisible.setChecked(true);
        }if(checkboxText.contains(cbSwimmer.getText().toString()+", ")){
            cbSwimmer.setChecked(true);
        }if(checkboxText.contains(cbThrows.getText().toString()+", ")){
            cbThrows.setChecked(true);
        }
    }

    public void setSpinnerText(String spinnerText){
        if(spinnerText.equals(items[0])){
            spinner.setSelection(0);
        }else if(spinnerText.equals(items[1])){
            spinner.setSelection(1);
        }else if(spinnerText.equals(items[2])){
            spinner.setSelection(2);
        }else if(spinnerText.equals(items[3])){
            spinner.setSelection(3);
        }else if(spinnerText.equals(items[4])){
            spinner.setSelection(4);
        }else if(spinnerText.equals(items[5])){
            spinner.setSelection(5);
        }else{
            spinner.setSelection(6);
        }
    }

    public void toastMessage(Pokemonas pokemonas){
        Toast.makeText(this,
                        "Id: " +etId.getText().toString() + "\n" +
                        "Vardas: " + pokemonas.getName() + "\n" +
                        "Svoris: " + pokemonas.getWeight() + " kg\n" +
                        "Ūgis: " + pokemonas.getHeight() + " m\n" +
                        "CP: " + pokemonas.getCp() + "\n" +
                        "Sugebėjimai: " + pokemonas.getAbilities() + "\n" +
                        "Tipas: " + pokemonas.getType()
        , Toast.LENGTH_LONG).show();
    }

    public void showDeleteToast(){
        db = new DatabaseSQLitePokemon(this);
        Pokemonas pok = new Pokemonas();
        pok = db.getByIdPokemonInfo(Integer.parseInt(etId.getText().toString()));
        Toast.makeText(
                this,
                "Norėdami ištrinti pokemoną spauskite trinti mygtuką dar kartą",
                Toast.LENGTH_SHORT)
                .show();
    }

}

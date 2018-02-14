package com.byethost12.kitm.mobiliaplikacija;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class PokemonUpdateActivity extends Activity {

    Button updateBtn;
    EditText  etId, etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbFast, cbInvisible, cbFlying, cbSwimmer, cbThrows;
    Spinner spinner;

    Pokemonas pokemonas;

    String items[] = {"Vanduo", "Ugnis", "Tamsa", "Žolytė", "Elektra", "Žemė", "Oras"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateBtn = (Button) findViewById(R.id.updateBtn);
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

        updateBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int id;
                String name;
                double weight;
                double height;
                String rb = "";
                String spinnerText = "";

                pokemonas = new Pokemonas();
                DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(PokemonUpdateActivity.this);

                if(etId.getText().toString().equals("") || db.checkId(Integer.parseInt(etId.getText().toString()))==false){
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.id_invalid));
                }else if (etName.getText().toString().equals("") || !Validation.isValidCredentials(etName.getText().toString())) {
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

                    String checkboxText = "";

                    if (cbFlying.isChecked()) {
                        checkboxText = checkboxText + "Skrendantis,";
                    }
                    if (cbInvisible.isChecked()) {
                        checkboxText = checkboxText + "Nematomumas,";
                    }
                    if (cbSwimmer.isChecked()) {
                        checkboxText = checkboxText + "Plaukiantis";
                    }
                    if (cbThrows.isChecked()) {
                        checkboxText = checkboxText + "Mėtantis sunkius/aštrius daiktus";
                    }
                    if (cbFast.isChecked()) {
                        checkboxText = checkboxText + "Greitas";
                    }
                    pokemonas.setAbilities(checkboxText);

                    spinnerText = spinner.getSelectedItem().toString();
                    pokemonas.setType(spinnerText);

                    db.updatePokemon(pokemonas);

                    toastMessage(
                                    "Id: " +etId.getText().toString() + "\n" +
                                    "Vardas: " + pokemonas.getName() + "\n" +
                                    "Svoris: " + pokemonas.getWeight() + " kg\n" +
                                    "Ūgis: " + pokemonas.getHeight() + " m\n" +
                                    "CP: " + pokemonas.getCp() + "\n" +
                                    "Sugebėjimai: " + pokemonas.getAbilities() + "\n" +
                                    "Tipas: " + pokemonas.getType());

                    Intent goToSearchActivity = new Intent(PokemonUpdateActivity.this, ChoiceActivity.class);
                    startActivity(goToSearchActivity);

                }
            }
        });
    }
    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

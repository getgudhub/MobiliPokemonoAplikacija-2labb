package com.byethost12.kitm.mobiliaplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class NewPokemonActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText  etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbFast, cbInvisible, cbFlying, cbSwimmer, cbThrows;
    Spinner spinner;

    Pokemonas pokemonas;

    String items[] = {"Vanduo", "Ugnis", "Tamsa", "Žolytė", "Elektra", "Žemė", "Oras"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        setTitle(R.string.new_entry_label);

        btnSubmit = (Button) findViewById(R.id.btnAdd);
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



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                String name;
                double weight;
                double height;
                String rb = "";
                String spinnerText = "";

                pokemonas = new Pokemonas();
                DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(NewPokemonActivity.this);

                if (etName.getText().toString().equals("") || !Validation.isValidCredentials(etName.getText().toString())) {
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
                   // id = Integer.parseInt(etId.getText().toString());
                   // pokemonas.setId(id);
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

                    Intent goToSearchActivity = new Intent(NewPokemonActivity.this, ChoiceActivity.class);
                    startActivity(goToSearchActivity);

                    spinnerText = spinner.getSelectedItem().toString();
                    pokemonas.setType(spinnerText);

                    db.addPokemon(pokemonas);
                    pokemonas = db.getByNamePokemonInfo(etName.getText().toString());

                    toastMessage(
                            "ID: " + pokemonas.getId() + "\n" +
                            "Vardas: " + pokemonas.getName() + "\n" +
                            "Svoris: " + pokemonas.getWeight() + " kg\n" +
                            "Ūgis: " + pokemonas.getHeight() + " m\n" +
                            "CP: " + pokemonas.getCp() + "\n" +
                            "Sugebėjimai: " + pokemonas.getAbilities() + "\n" +
                            "Tipas: " + pokemonas.getType());

                }

            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

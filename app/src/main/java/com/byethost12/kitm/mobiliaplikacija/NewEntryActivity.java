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

public class NewEntryActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText etId, etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbVegan, cbInvisible, cbTwoHeads;
    Spinner spinner;

    Pokemonas pokemonas;

    String items[] = {"Water", "Fire", "Dark", "Grass", "Electricity", "Earth", "Air"};

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

        cbVegan = (CheckBox) findViewById(R.id.cbVegan);
        cbInvisible = (CheckBox) findViewById(R.id.cbInvisible);
        cbTwoHeads = (CheckBox) findViewById(R.id.cbTwoHeads);

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
                DatabaseSQLite db = new DatabaseSQLite(NewEntryActivity.this);

                /*if (etId.getText().toString().equals("") || !Validation.isValidId(etId.getText().toString())) {
                    etId.requestFocus();
                    etId.setError(getResources().getString(R.string.id_invalid));
                } else */
                if (etName.getText().toString().equals("") || !Validation.isValidCredentials(etName.getText().toString())) {
                    etName.requestFocus();
                    etName.setError(getResources().getString(R.string.name_invalid));
                } else if (etWeight.getText().toString().equals("") || !Validation.isValidSize(etWeight.getText().toString())) {
                    etWeight.requestFocus();
                    etWeight.setError(getResources().getString(R.string.weight_invalid));
                } else if (etHeight.getText().toString().equals("") || !Validation.isValidSize(etHeight.getText().toString())) {
                    etHeight.requestFocus();
                    etHeight.setError(getResources().getString(R.string.height_invalid));
                } else if (!(cbVegan.isChecked() || cbInvisible.isChecked() || cbTwoHeads.isChecked())) {
                    cbTwoHeads.requestFocus();
                    cbTwoHeads.setError(getResources().getString(R.string.checkbox_not_checked));
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

                    if (cbVegan.isChecked()) {
                        checkboxText = checkboxText + "Vegan,";
                    }
                    if (cbInvisible.isChecked()) {
                        checkboxText = checkboxText + "Invisible,";
                    }
                    if (cbTwoHeads.isChecked()) {
                        checkboxText = checkboxText + "Two heads";
                    }
                    pokemonas.setAbilities(checkboxText);

                    Intent goToSearchActivity = new Intent(NewEntryActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);

                    spinnerText = spinner.getSelectedItem().toString();
                    pokemonas.setType(spinnerText);

                    db.addPokemon(pokemonas);

                    toastMessage(
                            "Name: " + pokemonas.getName() + "\n" +
                            "Weight: " + pokemonas.getWeight() + " kg\n" +
                            "Height: " + pokemonas.getHeight() + " m\n" +
                            "CP: " + pokemonas.getCp() + "\n" +
                            "Abilities: " + pokemonas.getAbilities() + "\n" +
                            "Type: " + pokemonas.getType());

                }

            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

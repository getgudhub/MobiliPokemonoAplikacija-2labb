package com.byethost12.kitm.mobiliaplikacija;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class PokemonReworkActivity extends AppCompatActivity {

    Button updateBtn, deleteBtn;
    EditText  etName, etWeight, etHeight;
    RadioGroup rbGroup;
    RadioButton rbStrong, rbMedium, rbWeak;
    CheckBox cbFast, cbInvisible, cbFlying, cbSwimmer, cbThrows;
    Spinner spinner;
    DatabaseSQLitePokemon db;
    DatabaseSQLiteUser dbu;
    Toolbar toolbar;

    int id, userid;
    String name;
    double weight;
    double height;
    String rb = "";
    String checkboxText = "";
    String spinnerText = "";

    Pokemonas pokemonas;
    Pokemonas oldPokemonas;
    String username;

    String items[] = {"Vanduo", "Ugnis", "Tamsa", "Žolytė", "Elektra", "Žemė", "Oras"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

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
            oldPokemonas = new Pokemonas();
            id = bundle.getInt("id");
            oldPokemonas.setId(id);
            name = bundle.getString("name");
            oldPokemonas.setName(name);
            etName.setText(name);
            weight = bundle.getDouble("weight");
            oldPokemonas.setWeight(weight);
            etWeight.setText(""+weight);
            height = bundle.getDouble("height");
            oldPokemonas.setHeight(height);
            etHeight.setText(""+height);
            rb = bundle.getString("CP");
              if(rb.equals(rbStrong.getText().toString())){
                  rbStrong.setChecked(true);
              }else if(rb.equals(rbMedium.getText().toString())){
                    rbMedium.setChecked(true);
              }else{
                  rbWeak.setChecked(true);
              }
            oldPokemonas.setCp(rb);
            checkboxText = bundle.getString("abil");
            oldPokemonas.setAbilities(checkboxText);
             setCheckBox(checkboxText);
            spinnerText = bundle.getString("type");
            oldPokemonas.setType(spinnerText);
             setSpinnerText(spinnerText);
            oldPokemonas.setUserId(bundle.getInt("userid"));
        }

        updateBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                pokemonas = new Pokemonas();
                db = new DatabaseSQLitePokemon(PokemonReworkActivity.this);
                dbu = new DatabaseSQLiteUser(PokemonReworkActivity.this);

                if (etName.getText().toString().equals("") || !Validation.isValidPokemonName(etName.getText().toString())) {
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

                    userid = oldPokemonas.getUserId();
                    pokemonas.setUserId(userid);
                    toastMessage(pokemonas);

                    username = dbu.getNameById(userid);
                    Intent goToSearchActivity = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                    goToSearchActivity.putExtra("username", username);
                    startActivity(goToSearchActivity);
                    PokemonReworkActivity.this.finish();

                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                db = new DatabaseSQLitePokemon(PokemonReworkActivity.this);
                    showDeleteToast();
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.deletePokemon(id);
                            Toast.makeText(PokemonReworkActivity.this, "Pokemonas ištrintas", Toast.LENGTH_SHORT).show();

                            username = getUsername(oldPokemonas.getUserId());
                            Intent goBack = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                            goBack.putExtra("username", username);
                            startActivity(goBack);
                            PokemonReworkActivity.this.finish();
                        }
                    });
               // }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pokemon_rework_menu, menu);
        MenuItem returnItem = menu.findItem(R.id.actionBack);
        returnItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                username = getUsername(oldPokemonas.getUserId());
                if (!updatePokemon()) {
                    Toast.makeText(PokemonReworkActivity.this, "Pakitimų nėra", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    PokemonReworkActivity.this.finish();
                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PokemonReworkActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Ar norėtumėte išeiti neišsisaugoję?");
                    builder.setMessage("Pasirinkę 'Taip' pakitimai bus išsaugoti, jeigu įrašyti/pasirinkti teisingai.")
                            .setPositiveButton("Taip", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.updatePokemon(pokemonas);
                                    Intent intent = new Intent(PokemonReworkActivity.this, PokemonTableActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                    PokemonReworkActivity.this.finish();
                                }
                            })
                            .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
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
        Toast.makeText(
                this,
                "Norėdami ištrinti pokemoną spauskite trinti mygtuką dar kartą",
                Toast.LENGTH_SHORT)
                .show();
    }

    public boolean updatePokemon(){
                pokemonas = new Pokemonas();
                db = new DatabaseSQLitePokemon(PokemonReworkActivity.this);

                if (etName.getText().toString().equals("") || !Validation.isValidPokemonName(etName.getText().toString())) {
                    return false;
                } else if (etWeight.getText().toString().equals("") || !Validation.isValidSize(etWeight.getText().toString())) {
                    return false;
                } else if (etHeight.getText().toString().equals("") || !Validation.isValidSize(etHeight.getText().toString())) {
                    return false;
                } else if (!(cbFlying.isChecked() || cbInvisible.isChecked() || cbThrows.isChecked() || cbFast.isChecked() || cbSwimmer.isChecked())) {
                    return false;
                } else {
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

                    if(pokemonas.getAbilities().equals(oldPokemonas.getAbilities())&&
                            pokemonas.getCp().equals(oldPokemonas.getCp())&&
                            pokemonas.getHeight()==oldPokemonas.getHeight()&&
                            pokemonas.getWeight()==oldPokemonas.getWeight()&&
                            pokemonas.getName().equals(oldPokemonas.getName())&&
                            pokemonas.getType().equals(oldPokemonas.getType())){
                        return false;
                    }else{
                        return true;
                    }
                }
            }
            public String getUsername(int userid){
                DatabaseSQLiteUser dbu = new DatabaseSQLiteUser(PokemonReworkActivity.this);
                String username = dbu.getNameById(userid);
                return username;
            }

}

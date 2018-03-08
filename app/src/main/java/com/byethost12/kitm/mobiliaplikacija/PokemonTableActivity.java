package com.byethost12.kitm.mobiliaplikacija;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class PokemonTableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    DatabaseSQLiteUser dbUser = new DatabaseSQLiteUser(PokemonTableActivity.this);
    DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(PokemonTableActivity.this);
    
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Pokemonas> pokemonList;
    private PokemonAdapter adapter;
    Button btnPrideti;
    Toolbar toolbar;
    User user;
    String name = "";
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_table);

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(PokemonTableActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.search_label);
        toolbar.setTitleMarginEnd(20);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        if(bundle !=null){
            name = bundle.getString("name");
        }
        
        user = dbUser.getUser(name);
        id = user.getId();
        
        if(dbUser.isAdmin(user.getUserlevel()))
        {pokemonList = db.getAllPokemons();
        }else{
            pokemonList = db.getUserPokemons(user.getId());
        }
        adapter = new PokemonAdapter(this, pokemonList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PokemonAdapter.ClickListener(){
            @Override
            public void onItemClick(int position, View v) {
                //starts new activity on Card click from adapter's class, ViewHolder method
            }
        });

        btnPrideti = (Button) findViewById(R.id.btnPrideti);
        btnPrideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PokemonTableActivity.this, NewPokemonActivity.class);
                intent.putExtra("userid", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_table_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        MenuItem returnItem = menu.findItem(R.id.actionBack);
        returnItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(PokemonTableActivity.this, LoginActivity.class);
                startActivity(intent);
                PokemonTableActivity.this.finish();

                return true;
            }
        });

        SearchManager sm = (SearchManager) PokemonTableActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(PokemonTableActivity.this);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override  //every time you press search btn an activity is created which in turn class this function
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsynchFetch(query).execute();
        }
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Pokemonas> newList = new ArrayList<>();
        for(Pokemonas pokemon : pokemonList){
            String pavadinimas = pokemon.getName().toLowerCase();
            if(pavadinimas.contains(newText)) newList.add(pokemon);
        }
        adapter.setFilter(newList);
        return false;
    }

    private class AsynchFetch extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog = new ProgressDialog(PokemonTableActivity.this);
        String searchQuery;

        public AsynchFetch(String searchQuery){
            this.searchQuery = searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Prašome palaukti");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(PokemonTableActivity.this);
            pokemonList = db.getUserPokemons(user.getId());
            if(pokemonList.isEmpty()){
                return "no rows";
            }else{
                return (pokemonList.toString());
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(result.equals("no rows")){
                Toast.makeText(PokemonTableActivity.this, "Pagal paieška nerasta duomenų", Toast.LENGTH_LONG).show();
            }else{// setup and hand over list pokemonai to recycleView
                mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
                PokemonAdapter pa = new PokemonAdapter(PokemonTableActivity.this, pokemonList);
                mRecyclerView.setAdapter(pa);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(PokemonTableActivity.this));
            }
        }
    }

}

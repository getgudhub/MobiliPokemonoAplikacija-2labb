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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class PokemonTableActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Pokemonas> pokemonList;
    private PokemonAdapter adapter;
    Button btnPrideti;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_table);

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(PokemonTableActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseSQLitePokemon db = new DatabaseSQLitePokemon(PokemonTableActivity.this);
        pokemonList = db.getAllPokemons();
        adapter = new PokemonAdapter(pokemonList);
        mRecyclerView.setAdapter(adapter);

        btnPrideti = (Button) findViewById(R.id.btnPrideti);
        btnPrideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PokemonTableActivity.this, NewPokemonActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.pokemon_table_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchManager sm = (SearchManager) PokemonTableActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView !=null){
            searchView.setSearchableInfo(sm.getSearchableInfo(PokemonTableActivity.this.getComponentName()));
            searchView.setIconified(false);
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
            pokemonList = db.getAllPokemons();
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
                Toast.makeText(PokemonTableActivity.this, "Pagal paieška nerasta duomnų", Toast.LENGTH_LONG).show();
            }else{// setup and hand over list pokemonai to recycleView
                mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
                PokemonAdapter pa = new PokemonAdapter(PokemonTableActivity.this, pokemonList);
                mRecyclerView.setAdapter(pa);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(PokemonTableActivity.this));
            }
        }
    }

}

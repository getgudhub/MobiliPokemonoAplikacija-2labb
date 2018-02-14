package com.byethost12.kitm.mobiliaplikacija;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PokemonTableActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView searchView;
    private ArrayList<Pokemonas> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_table);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PokemonAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //adds item to aaction bar
        getMenuInflater().inflate(R.menu.pokemon_table_menu,menu);
        //get search item from actionbar and get search service
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchManager sm = (SearchManager) PokemonTableActivity.this.getSytsemService();
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
            DatabaseSQLitePokemon db= new DatabaseSQLitePokemon(PokemonTableActivity.this);
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
                Toast.makeText(PokemonTableActivity.this, "Pagal paieška nerasta duomnų", Toast.LENGTH_LONG);
            }else{  // setup and hand over list pokemonai to recycleView
                mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
                PokemonAdapter pa = new PokemonAdapter(PokemonTableActivity.this, pokemon_list);
                mRecyclerView.setAdapter(pa);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(PokemonTableActivity.this));
            }
        }
    }

}

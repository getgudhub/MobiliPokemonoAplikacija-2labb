package com.byethost12.kitm.mobiliaplikacija;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Pokemonas> arrayList = new ArrayList<>();
    private List<Pokemonas> pokemons = Collections.emptyList();
    private Pokemonas currentPokemon;

    public static final String ENTRY = "com.byethost12.kitm.mobiliaplikacija";

    PokemonAdapter(ArrayList<Pokemonas> arrayList){
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvPavadinimas, tvTipas, tvGalia, tvSavybes;

        public MyViewHolder(View v) {
            super(v);
            tvPavadinimas = (TextView) itemView.findViewById(R.id.pavadinimas);
            tvTipas = (TextView) itemView.findViewById(R.id.tipas);
            tvGalia = (TextView) itemView.findViewById(R.id.galia);
            tvSavybes = (TextView) itemView.findViewById(R.id.savybes);
            //itemView.setOnClickListener(this);
        }

        @Override  //visiems saraso elementams
        public void onClick(View view) {
            int itemPos = getAdapterPosition();
            int pokemonId = pokemons.get(itemPos).getId();

            Pokemonas pokemonas = pokemons.get(itemPos);

            Intent intent =  new Intent(context, NewPokemonActivity.class);
            intent.putExtra(ENTRY, pokemonId);
            context.startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    // susieti su esamu lanu ir perduoti pokemonu sarasa is DB
    public PokemonAdapter(Context context, List<Pokemonas> pokemons) {
        this.context= context;
        this.pokemons = pokemons;
        inflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) inflater.inflate(R.layout.container_pokemon, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;   //return new CustomViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        currentPokemon = pokemons.get(position);
        vh.tvPavadinimas.setText(currentPokemon.getName());
        vh.tvGalia.setText("Galia: " +currentPokemon.getCp());
        vh.tvSavybes.setText("Savybės: " +currentPokemon.getAbilities());
        vh.tvTipas.setText("Tipas: "+ currentPokemon.getType());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemons.size();
    }
}

package com.byethost12.kitm.mobiliaplikacija;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Pokemonas> pokemons = Collections.emptyList();
    private Pokemonas currentPokemon;

    public static final String ENTRY = "com.byethost12.kitm.mobiliaplikacija";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tvPavadinimas, tvTipas, tvGalia, tvSavybes;
       // private Context context;

        public ViewHolder(View v) {
            super(v);
            tvPavadinimas = (TextView) itemView.findViewById(R.id.pavadinimas);
            tvTipas = (TextView) itemView.findViewById(R.id.tipas);
            tvGalia = (TextView) itemView.findViewById(R.id.galia);
            tvSavybes = (TextView) itemView.findViewById(R.id.savybes);
            itemView.setOnClickListener(this);
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

    // Provide a suitable constructor (depends on the kind of dataset) susieti su esamu lanu ir perduoti pokemonu sarasa is DB
    public PokemonAdapter(Context context, List<Pokemonas> pokemons) {
        this.context= context;
        this.pokemons = pokemons;
        inflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView tv = (TextView) inflater.inflate(R.layout.container_pokemon, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(tv);
        return vh;   //return new CustomViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ViewHolder vh = new  ViewHolder();
        currentPokemon = pokemons.get(position);
        vh.tvPavadinimas.setText(currentPokemon.getName());
        vh.tvGalia.setText("Galia: " +currentPokemon.getCp());
        vh.tvSavybes.setText("Savybės: " +currentPokemon.getAbilities());
        vh.tvTipas.setText("Tipas: "+ currentPokemon.getType());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 0;
    }
}

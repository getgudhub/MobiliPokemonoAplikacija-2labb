package com.byethost12.kitm.mobiliaplikacija;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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

    private static ClickListener clickListener = null;

    private Context context;
    private LayoutInflater inflater;
    private List<Pokemonas> pokemons = Collections.emptyList();
    private Pokemonas currentPokemon;

    public static final String ENTRY = "com.byethost12.kitm.mobiliaplikacija";

    ArrayList<Pokemonas> arrayList = new ArrayList<>();
    PokemonAdapter(ArrayList<Pokemonas> arrayList){
        this.arrayList = arrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId, tvPavadinimas, tvTipas, tvGalia, tvSavybes;
        private CardView cardView;

        public MyViewHolder(View v)  {
            super(v);
            tvId = (TextView) itemView.findViewById(R.id.id);
            tvPavadinimas = (TextView) itemView.findViewById(R.id.pavadinimas);
            tvTipas = (TextView) itemView.findViewById(R.id.tipas);
            tvGalia = (TextView) itemView.findViewById(R.id.galia);
            tvSavybes = (TextView) itemView.findViewById(R.id.savybes);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(clickListener !=null){
                        clickListener.onItemClick(getAdapterPosition(),v);
                        int itemPos = getAdapterPosition();
                       //Pokemonas pokemonas = pokemons.get(itemPos);

                        int pokeId = pokemons.get(itemPos).getId();
                        String pokeName = pokemons.get(itemPos).getName();
                        Double pokeWeight = pokemons.get(itemPos).getWeight();
                        Double pokeHeight = pokemons.get(itemPos).getHeight();
                        String pokeRB = pokemons.get(itemPos).getCp();
                        String pokeAbilities = pokemons.get(itemPos).getAbilities();
                        String pokeType = pokemons.get(itemPos).getType();

                        Intent intent =  new Intent(context, PokemonUpdateActivity.class);
                        intent.putExtra("id", pokeId);
                        intent.putExtra("name", pokeName);
                        intent.putExtra("weight", pokeWeight);
                        intent.putExtra("height", pokeHeight);
                        intent.putExtra("CP", pokeRB);
                        intent.putExtra("abil", pokeAbilities);
                        intent.putExtra("type", pokeType);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    // susieti su esamu lanu ir perduoti pokemonu sarasa is DB
    public PokemonAdapter(Context context, ArrayList<Pokemonas> pokemons) {
        this.context= context;
        this.pokemons = pokemons;
        inflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) inflater.inflate(R.layout.container_pokemon, parent, false);
        return new MyViewHolder (v);   //return new CustomViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        currentPokemon = pokemons.get(position);
        vh.tvPavadinimas.setText(currentPokemon.getName());
        vh.tvGalia.setText("Galia: " +currentPokemon.getCp());
        vh.tvId.setText("Id: " +currentPokemon.getId());
        vh.tvSavybes.setText("Savybės: " +currentPokemon.getAbilities());
        vh.tvTipas.setText("Tipas: "+ currentPokemon.getType());

    }

    public void setFilter(ArrayList<Pokemonas> list){
        pokemons = new ArrayList<>();
        pokemons.addAll(list);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void setOnItemClickListener(ClickListener clickListener){
        PokemonAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}

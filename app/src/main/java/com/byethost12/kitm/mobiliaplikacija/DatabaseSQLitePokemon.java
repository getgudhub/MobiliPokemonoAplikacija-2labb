package com.byethost12.kitm.mobiliaplikacija;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSQLitePokemon extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION   = 2;
    private static final String DATABASE_NAME   = "db";

    private static final String TABLE_POKEMONS      = "pokemons";
    private static final String POKEMON_ID          = "id";
    private static final String POKEMON_NAME        = "name";
    private static final String POKEMON_TYPE        = "type";
    private static final String POKEMON_ABILITIES   = "abilities";
    private static final String POKEMON_CP          = "cp";
    private static final String POKEMON_WEIGHT      = "weight";
    private static final String POKEMON_HEIGHT      = "height";

    String CREATE_POKEMONS_TABLE ="CREATE TABLE "+ TABLE_POKEMONS
            + "(" +  POKEMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + POKEMON_NAME + " TEXT,"
            + POKEMON_TYPE + " TEXT,"
            + POKEMON_ABILITIES + " TEXT,"
            + POKEMON_CP +" TEXT,"
            + POKEMON_WEIGHT + " REAL,"
            + POKEMON_HEIGHT + " REAL" + ")";

    String CREATE_TABLE_IF_NOT_EXISTS_POKEMONS_TABLE = "CREATE TABLE IF NOT EXISTS "+
            TABLE_POKEMONS
            + "(" +  POKEMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + POKEMON_NAME + " TEXT,"
            + POKEMON_TYPE + " TEXT,"
            + POKEMON_ABILITIES + " TEXT,"
            + POKEMON_CP +" TEXT,"
            + POKEMON_WEIGHT + " REAL,"
            + POKEMON_HEIGHT + " REAL" + ")";

    public DatabaseSQLitePokemon(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POKEMONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKEMONS);

        // Create tables again
        onCreate(db);
    }

    public void addPokemon(Pokemonas poke){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POKEMON_NAME,        poke.getName());
        values.put(POKEMON_TYPE,        poke.getType());
        values.put(POKEMON_ABILITIES,   poke.getAbilities());
        values.put(POKEMON_CP,          poke.getCp());
        values.put(POKEMON_WEIGHT,      poke.getWeight());
        values.put(POKEMON_HEIGHT,      poke.getHeight());

        // Inserting Row
        db.insert(TABLE_POKEMONS, null, values);

        // Closing database connection
        db.close();
    }

    public int updatePokemon( Pokemonas poke){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POKEMON_NAME,        poke.getName());
        values.put(POKEMON_TYPE,        poke.getType());
        values.put(POKEMON_ABILITIES,   poke.getAbilities());
        values.put(POKEMON_CP,          poke.getCp());
        values.put(POKEMON_WEIGHT,      poke.getWeight());
        values.put(POKEMON_HEIGHT,      poke.getHeight());

        int i = db.update(TABLE_POKEMONS, values, POKEMON_ID + " = ?",
                new String[] {String.valueOf(poke.getId())});

        db.close();
        return i;

    }

    public boolean deletePokemon(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_POKEMONS, POKEMON_ID + "=" + id, null) > 0;
    }

    public Cursor getAllPokes() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_POKEMONS, new String[]{
                POKEMON_ID, POKEMON_NAME, POKEMON_TYPE,
                    POKEMON_ABILITIES, POKEMON_CP,
                        POKEMON_WEIGHT, POKEMON_HEIGHT},
                null, null, null, null, null);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_IF_NOT_EXISTS_POKEMONS_TABLE);
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_POKEMONS, null);
        return res;
    }

    Pokemonas getByIdPokemonInfo(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_POKEMONS,
                new String[]{
                        POKEMON_ID,
                        POKEMON_NAME,
                        POKEMON_TYPE,
                        POKEMON_ABILITIES,
                        POKEMON_CP,
                        POKEMON_WEIGHT,
                        POKEMON_HEIGHT
                },
                POKEMON_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pokemonas pokemon = new Pokemonas(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5),
                cursor.getDouble(6)
        );
        cursor.close();
        return pokemon;
    }
    Pokemonas getByNamePokemonInfo(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_POKEMONS,
                new String[]{
                        POKEMON_ID,
                        POKEMON_NAME,
                        POKEMON_TYPE,
                        POKEMON_ABILITIES,
                        POKEMON_CP,
                        POKEMON_WEIGHT,
                        POKEMON_HEIGHT
                },
                POKEMON_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pokemonas pokemon = new Pokemonas(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5),
                cursor.getDouble(6)
        );
        cursor.close();
        return pokemon;
    }


    public boolean checkId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_POKEMONS + " where " + POKEMON_ID + " = " + id;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
            }
    }
    public boolean checkName(String  name){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_POKEMONS + " where " + POKEMON_NAME + " = '" + name +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }



    public ArrayList<Pokemonas> getAllPokemons() {
            ArrayList<Pokemonas> pokemon = new ArrayList<Pokemonas>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_TABLE_IF_NOT_EXISTS_POKEMONS_TABLE);

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POKEMONS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pokemonas poke = new Pokemonas();

                poke.setId(Integer.parseInt(cursor.getString(0)));
                poke.setName(cursor.getString(1));
                poke.setType(cursor.getString(2));
                poke.setAbilities(cursor.getString(3));
                poke.setCp(cursor.getString(4));
                poke.setWeight(cursor.getDouble(5));
                poke.setHeight(cursor.getDouble(6));

                // adding pokemon to list
                pokemon.add(poke);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return pokemons list
        return pokemon;
    }

}

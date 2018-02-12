package com.byethost12.kitm.mobiliaplikacija;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DatabaseSQLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION   = 2;
    private static final String DATABASE_NAME   = "db";

    private static final String TABLE_USERS     = "users";
    private static final String USER_ID         = "id";
    private static final String USER_LEVEL      = "userlevel";
    private static final String USER_NAME       = "name";
    private static final String USER_PASSWORD   = "password";
    private static final String USER_EMAIL      = "email";

    private static final String TABLE_POKEMONS      = "pokemons";
    private static final String POKEMON_ID          = "id";
    private static final String POKEMON_NAME        = "name";
    private static final String POKEMON_TYPE        = "type";
    private static final String POKEMON_ABILITIES   = "abilities";
    private static final String POKEMON_CP          = "cp";
    private static final String POKEMON_WEIGHT      = "weight";
    private static final String POKEMON_HEIGHT      = "height";

    String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_LEVEL + " TEXT,"
            + USER_NAME + " TEXT,"
            + USER_PASSWORD + " TEXT,"
            + USER_EMAIL + " TEXT" + ")";

    String CREATE_POKEMONS_TABLE ="CREATE TABLE "+ TABLE_POKEMONS
            + "(" +  POKEMON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + POKEMON_NAME + " TEXT,"
            + POKEMON_TYPE + " TEXT,"
            + POKEMON_ABILITIES + " TEXT,"
            + POKEMON_CP +" TEXT,"
            + POKEMON_WEIGHT + " REAL,"
            + POKEMON_HEIGHT + " REAL" + ")";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_POKEMONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKEMONS);

        // Create tables again
        onCreate(db);
    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_LEVEL,      user.getUserlevel());
        values.put(USER_NAME,       user.getUsernameForRegister());
        values.put(USER_PASSWORD,   user.getPasswordForRegister());
        values.put(USER_EMAIL,      user.getEmailForRegister());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);

        // Closing database connection
        db.close();
    }

    User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{
                        USER_ID,
                        USER_LEVEL,
                        USER_NAME,
                        USER_PASSWORD,
                        USER_EMAIL
                },
                USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        cursor.close();
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserlevel(cursor.getString(1));
                user.setUsernameForRegister(cursor.getString(2));
                user.setPasswordForRegister(cursor.getString(3));
                user.setEmailForRegister(cursor.getString(4));

                // adding user to list
                users.add(user);
            } while (cursor.moveToNext());
        }   cursor.close();

        // return users list
        return users;

    }

    public boolean isValidUser(String username, String password){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE "
                        + USER_NAME + "='" + username + "'AND " +
                        USER_PASSWORD + "='" + password + "'" , null);
        if (c.getCount() > 0)
            return true;
        c.close();
        return false;

    }

    //POKEMONS

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
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_POKEMONS,null);
        return res;
    }

    Pokemonas getDeletePokemonInfo(int id){
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

    public boolean deletePokemon(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_POKEMONS, POKEMON_ID + "=" + id, null) > 0;
    }

    /*public ArrayList<Pokemonas> getAllPokemons() {
            ArrayList<Pokemonas> pokemon = new ArrayList<Pokemonas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POKEMONS;

        SQLiteDatabase db = this.getWritableDatabase();

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
    }*/

}

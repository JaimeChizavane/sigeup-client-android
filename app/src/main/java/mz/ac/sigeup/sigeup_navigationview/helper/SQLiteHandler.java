package mz.ac.sigeup.sigeup_navigationview.helper;

/**
 * Created by jaimechizavane on 9/19/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sigeup";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAME = "nome";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENERO = "genero";
    private static final String KEY_DATANASCIMENTO = "data_nascimento";
    private static final String KEY_PERFIL = "perfil_id";
    private static final String KEY_CONFIRMADO = "confirmado";
    private static final String KEY_TOKEN = "token";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + "INTERGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                +KEY_USERNAME + "TEXT UNIQUE"+ KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_GENERO + " TEXT,"
                + KEY_DATANASCIMENTO + " TEXT" +
                /*KEY_PERFIL + " TEXT" +*/
                KEY_PERFIL + " INTERGER" +
                KEY_CONFIRMADO + " INTERGER" +
                KEY_TOKEN + " INTERGER" +
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);


        Log.d(TAG, "Tabela na base de dados SQLite criada com sucesso!");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String id, String username, String name, String email, String password, String dataNascimento, String perfil, String confirmado, String genero, String token) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_DATANASCIMENTO, dataNascimento);
        values.put(KEY_GENERO, genero);
        values.put(KEY_PERFIL, perfil);
        values.put(KEY_CONFIRMADO, confirmado);
        values.put(KEY_TOKEN, token);

        Log.d(TAG, "Values: "+values.toString());
        // Inserting Row
        //long row_id = db.insert(TABLE_USER, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + username);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("username", cursor.getString(3));
            user.put("email", cursor.getString(4));
            user.put("data_nascimento", cursor.getString(5));
            user.put("genero", cursor.getString(6));
            user.put("perfil", cursor.getString(7));
            user.put("confirmado", cursor.getString(8));
            user.put("token", cursor.getString(9));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Buscando dados do Usuario em Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
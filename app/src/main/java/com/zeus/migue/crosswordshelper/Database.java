package com.zeus.migue.crosswordshelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 79812 on 09/06/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 2;
    private static final String WORDS_TABLE_NAME = "words";
    private static final String WORD_ID = "_id";
    private static final String WORD_CONTENT = "word";
    private static final String WORD_CREATION_DATE = "creation_date";

    private static Database instance;

    public static Database getInstance(Context context){
        if (instance == null){
            instance = new Database(context);
        }
        return instance;
    }

    public static Database getInstance(){
        return instance;
    }

    public Database(Context context) {
        super(context, PATH + "/" + DATABASE_NAME, null, DATABASE_VERSION);
        //context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(String.format(Locale.US, "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT , %s VARCHAR (100), %s INTEGER)", WORDS_TABLE_NAME, WORD_ID, WORD_CONTENT, WORD_CREATION_DATE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format(Locale.US, "CREATE TABLE words_tmp (%s INTEGER, %s VARCHAR (100) PRIMARY KEY, %s INTEGER)", WORD_ID, WORD_CONTENT, WORD_CREATION_DATE));
        sqLiteDatabase.execSQL("INSERT INTO words_tmp (_id, word, creation_date) SELECT _id,  word, creation_date FROM words");
        sqLiteDatabase.execSQL("ALTER TABLE words RENAME TO words_secondary");
        sqLiteDatabase.execSQL("ALTER TABLE words_tmp RENAME TO words");
    }

    //Ordered to put them in adapter and get id of them
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT * FROM " +WORDS_TABLE_NAME + " ORDER BY " + WORD_CONTENT;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            words.add(new Word(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2)
            ));
        }
        cursor.close();
        db.close();
        return words;
    }

    //Unordered, only a simple string list
    public List<String> getValidWords() {
        List<String> words = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT " + WORD_CONTENT +  " FROM " +WORDS_TABLE_NAME;
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            words.add(cursor.getString(0));
        }
        cursor.close();
        return words;
    }

    public long addValidWord(String word){
        SQLiteDatabase db = getWritableDatabase();
        /*ContentValues cv = new ContentValues();
        cv.put(WORD_CONTENT, word);
        cv.put(WORD_CREATION_DATE, System.currentTimeMillis());*/
        try{
            db.execSQL("INSERT INTO "+ WORDS_TABLE_NAME + " VALUES ((SELECT MAX("+ WORD_ID+") + 1 FROM " +WORDS_TABLE_NAME +"), '" + word+ "', " + System.currentTimeMillis()+ ")");
            String SQL = "SELECT MAX("+ WORD_ID+") FROM " +WORDS_TABLE_NAME;
            Cursor cursor = db.rawQuery(SQL, null);
            cursor.moveToFirst();
            long id = cursor.getLong(0);
            cursor.close();
            return id;
        } catch (SQLException exception){
            Log.d("SQL Exception", exception.getMessage());
            return -1;
        }
    }

    public void updateWord(long id, String word){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORD_CONTENT, word);
        db.update(WORDS_TABLE_NAME, cv, WORD_ID + " = " + id, null);
    }

    public void removeWord(String word){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ WORDS_TABLE_NAME +  " WHERE " + WORD_CONTENT + " = '" +word +"'");
    }

    public void removeWord(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ WORDS_TABLE_NAME +  " WHERE " + WORD_ID + " = " + id);
    }

}

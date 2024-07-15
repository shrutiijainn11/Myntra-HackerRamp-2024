package com.example.instapostsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "challenges.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_CHALLENGE_1 = "VintageRevival";
    public static final String TABLE_CHALLENGE_2 = "FusionFiesta";
    public static final String TABLE_CHALLENGE_3 = "CosplayGame";
    public static final String TABLE_COMMENTS = "Comments";
    public static final String TABLE_USERS = "Users";

    private static final String CREATE_TABLE_CHALLENGE_1 = "CREATE TABLE " + TABLE_CHALLENGE_1 + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "mobile TEXT, " +
            "email TEXT, " +
            "image BLOB, " +
            "likes INTEGER DEFAULT 0, " +
            "comments INTEGER DEFAULT 0, " +
            "isLiked INTEGER DEFAULT 0);";

    private static final String CREATE_TABLE_CHALLENGE_2 = "CREATE TABLE " + TABLE_CHALLENGE_2 + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "mobile TEXT, " +
            "email TEXT, " +
            "image BLOB, " +
            "likes INTEGER DEFAULT 0, " +
            "comments INTEGER DEFAULT 0, " +
            "isLiked INTEGER DEFAULT 0);";

    private static final String CREATE_TABLE_CHALLENGE_3 = "CREATE TABLE " + TABLE_CHALLENGE_3 + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "mobile TEXT, " +
            "email TEXT, " +
            "image BLOB, " +
            "likes INTEGER DEFAULT 0, " +
            "comments INTEGER DEFAULT 0, " +
            "isLiked INTEGER DEFAULT 0);";

    private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE " + TABLE_COMMENTS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "image_id INTEGER, " +
            "comment TEXT, " +
            "FOREIGN KEY (image_id) REFERENCES " + TABLE_CHALLENGE_1 + "(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (image_id) REFERENCES " + TABLE_CHALLENGE_2 + "(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (image_id) REFERENCES " + TABLE_CHALLENGE_3 + "(id) ON DELETE CASCADE);";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "email TEXT UNIQUE, " +
            "mobile TEXT, " +
            "points INTEGER DEFAULT 0);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHALLENGE_1);
        db.execSQL(CREATE_TABLE_CHALLENGE_2);
        db.execSQL(CREATE_TABLE_CHALLENGE_3);
        db.execSQL(CREATE_TABLE_COMMENTS);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_CHALLENGE_1 + " ADD COLUMN isLiked INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE " + TABLE_CHALLENGE_2 + " ADD COLUMN isLiked INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE " + TABLE_CHALLENGE_3 + " ADD COLUMN isLiked INTEGER DEFAULT 0");
        }

        if (oldVersion < 3) {
            db.execSQL(CREATE_TABLE_USERS);
        }
    }

    public void incrementComments(String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + table + " SET comments = comments + 1 WHERE id = " + id);
    }

    public void addComment(int imageId, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_COMMENTS + " (image_id, comment) VALUES (?, ?)", new Object[]{imageId, comment});
    }

    public Cursor getComments(int imageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT comment FROM " + TABLE_COMMENTS + " WHERE image_id = ?", new String[]{String.valueOf(imageId)});
    }

    public void addSubmission(String table, String name, String mobile, String email, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + table + " (name, mobile, email, image) VALUES (?, ?, ?, ?)", new Object[]{name, mobile, email, image});
    }

    public void updateImage(String table, int id, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image);
        db.update(table, contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void incrementLikes(String tableName, int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + tableName + " SET likes = likes + 1, isLiked = 1 WHERE id = " + postId);
    }

    public void decrementLikes(String tableName, int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + tableName + " SET likes = likes - 1, isLiked = 0 WHERE id = " + postId);
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email = ?", new String[]{email});
    }

    public void updateUserPoints(String email, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USERS + " SET points = points + ? WHERE email = ?", new Object[]{points, email});
    }

    public boolean isValidUser(String name, String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE name=? AND mobile=?", new String[]{name, mobile});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public void addUser(String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("mobile", phone);
        contentValues.put("points", 0);
        db.insert(TABLE_USERS, null, contentValues);
    }


}


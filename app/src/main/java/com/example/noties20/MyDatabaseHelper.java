package com.example.noties20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Noties.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_notes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "Note_title";
    private static final String COLUMN_TEXT = "note_text";
    private static final String COLUMN_TIME = "noteTime";

    private static final String TABLE_NAME1 = "my_tasks";
    private static final String COLUMN_IDTASK = "_id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_STATUS = "status";

    private static final String TABLE_NAME2 = "my_users";
    private static final String COLUMN_IDUSER = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_TEXT + " TEXT, " +
                        COLUMN_TIME + " TEXT);";

        String query2 = "CREATE TABLE " + TABLE_NAME1 +
                "(" + COLUMN_IDTASK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT, " +
                COLUMN_STATUS + " INTEGER);";

        String query3 = "CREATE TABLE " + TABLE_NAME2 +
                "(" + COLUMN_IDUSER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);";

        db.execSQL(query2);
        db.execSQL(query);
        db.execSQL(query3);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    void addTask(String txt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK, txt);
        cv.put(COLUMN_STATUS,0);
        long result = db.insert(TABLE_NAME1,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed adding task", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void addBook(String title, String text, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TEXT, text);
        cv.put(COLUMN_TIME, time);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed adding book", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addUser(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user);
        cv.put(COLUMN_PASSWORD, pass);

        long result = db.insert(TABLE_NAME2,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed adding user", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from my_users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUserPass(String username , String password){

        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns = { COLUMN_IDUSER };
        String selection = COLUMN_USERNAME + "=?" + " and " + COLUMN_PASSWORD + "=?";
        String [] selectionargs = { username , password};
        Cursor cursor = db.query(TABLE_NAME2 , columns , selection ,selectionargs , null , null , null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;

    }


    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllTask(){
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TEXT, text);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    void deleteAllTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME1);
    }



    public void updateTask(String id , String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK , task);

        long result=db.update(TABLE_NAME1 , values , "_id=?" , new String[]{String.valueOf(id)});
        if(result==-1){
            Toast.makeText(context, "Failed to update task status", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The task was updated successfully!!", Toast.LENGTH_SHORT).show();

        }

    }

    public void updateStatus(String id , int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS , status);
        long result=db.update(TABLE_NAME1,values,"_id=?",new String[]{id});
        if(result==-1){
            Toast.makeText(context, "Failed to update task status", Toast.LENGTH_SHORT).show();
        }
        else{
            if(status==0){
                Toast.makeText(context, "The task is marked as uncompleted!!", Toast.LENGTH_SHORT).show();
            }
            else if (status==1)
            {
                Toast.makeText(context, "The task is marked as completed!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void deleteTask(String id ){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(TABLE_NAME1 , "_id=?" , new String[]{id});
        if(result==-1){
            Toast.makeText(context, "Failed to delete task!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The task was deleted successfully!!", Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteFinishedTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(TABLE_NAME1 , "status=1",null);
        if(result==-1){
            Toast.makeText(context, "Failed to delete finished tasks!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The finished tasks were deleted successfully!!", Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteUnfinishedTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(TABLE_NAME1 , "status=0",null);
        if(result==-1){
            Toast.makeText(context, "Failed to delete unfinished tasks!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "The unfinished tasks were deleted successfully!!", Toast.LENGTH_SHORT).show();

        }
    }

    public Cursor searchNote(String note, String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="Select * from "+TABLE_NAME+ " WHERE "+COLUMN_TEXT+ "Like "+note+" Or "+COLUMN_TITLE+ " Like"+title;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }

}

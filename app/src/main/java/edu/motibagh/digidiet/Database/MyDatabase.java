package edu.motibagh.digidiet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import edu.motibagh.digidiet.Firebasecrud;

/**
 * Created by SHIKHA on 07-07-2018.
 */

public class MyDatabase extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1; // database version is in capital letter bcoz it is constant
    public static final String DATABASE_NAME = "mydb";
    //table name
    public static final String TABLE_NAME = "book_record";

    //column name
    public static final String FILEID ="fileid";
    public static final String SUBJECT ="subject";
    public static final String FILEURL ="url";
    public static final String ISSTOREDINDB = "isstoredindb";
    public static final String FILELOCALPATH ="path";
    public static final String AUTHOR ="author";
    public static final String SEMESTER ="semester";
    public static final String TYPE ="type";
    public static final String TITLE ="title";


    public MyDatabase(Context context) // context is activity reference
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db)  //create table
    {
        String query="CREATE TABLE " + TABLE_NAME + "(" + FILEID + " NUMBER PRIMARY KEY," + SUBJECT + " TEXT," + FILEURL + " TEXT," + ISSTOREDINDB + " TEXT,"+ FILELOCALPATH + " TEXT,"+ AUTHOR + " TEXT,"+ SEMESTER + " TEXT,"+ TYPE + " TEXT,"+ TITLE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) //upgrade table
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void clearTableData() {
        SQLiteDatabase db =  this.getReadableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void updateRecord(String filePath, int FileId, String isdownloaded) {
        SQLiteDatabase db =  this.getReadableDatabase();
        ContentValues data=new ContentValues();
        data.put(FILEID,FileId);
        data.put(ISSTOREDINDB,isdownloaded);
        data.put(FILELOCALPATH,filePath);
        db.update(TABLE_NAME, data, FILEID + "=" + FileId,null);
    }
    public void addRecord(Firebasecrud record)
    {
        SQLiteDatabase db=  getWritableDatabase(); // to write data in database
        ContentValues values=new ContentValues(); // contentvalues is used to store data in row and column format

        values.put(FILEID,record.getFileId());
        values.put(SUBJECT,record.getSubject());
        values.put(FILEURL,record.getUrl());
        values.put(ISSTOREDINDB,record.getIsStoredinDB());
        values.put(FILELOCALPATH,record.getFilelocalPath());
        values.put(AUTHOR,record.getAuthor());
        values.put(SEMESTER,record.getSemester());
        values.put(TYPE,record.getType());
        values.put(TITLE,record.getTitle());

        db.insert(TABLE_NAME,null,values);
    }

    public List<Firebasecrud> getAllRecord()
    {
        SQLiteDatabase db= getReadableDatabase();
        ArrayList<Firebasecrud> bookList=new ArrayList<Firebasecrud>();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor= db.rawQuery(query,null); //rawquery method return value but exexcsql don't return it simply execute query
        if(cursor.moveToFirst())
        {
            do {
                Firebasecrud record = new Firebasecrud((cursor.getInt(0)),(cursor.getString(1)),
                        (cursor.getString(2)),(cursor.getString(3)),(cursor.getString(4)),(cursor.getString(5)),(cursor.getString(6)),(cursor.getString(7)),(cursor.getString(8)));
                //Add record to list
                bookList.add(record);
            }while(cursor.moveToNext());
        }
        return bookList;
    }

}

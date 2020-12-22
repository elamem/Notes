package tech.elamparithi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String NOTES = "NOTES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_BODY = "BODY";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + NOTES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_BODY + " TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(NotesModel notesModel){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,notesModel.getTitle());
        cv.put(COLUMN_BODY,notesModel.getBody());

        long result = db.insert(NOTES, null, cv);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }

    public List<NotesModel> getAllNotes() {

        List<NotesModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + NOTES;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {

            do{
                returnList.add(new NotesModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));

            }
            while (cursor.moveToNext());
        }


        return returnList;

    }

    public boolean updateOne(NotesModel notesModel){



        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,notesModel.getTitle());
        cv.put(COLUMN_BODY,notesModel.getBody());

        int update = db.update(NOTES, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(notesModel.getId())});

        if(update == -1){
            return  false;

        }
        else
            return  true;

    }

    public boolean deleteOne(int id) {

        SQLiteDatabase db  = getWritableDatabase();

        int delete = db.delete(NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});


        if(delete == -1)
            return false;
        else
            return true;

    }
}

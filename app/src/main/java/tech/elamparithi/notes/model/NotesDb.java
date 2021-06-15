package tech.elamparithi.notes.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.elamparithi.notes.model.Entity.Notes;
import tech.elamparithi.notes.model.dao.NotesDao;

@Database(entities = {Notes.class},version = 1, exportSchema = false)
public abstract class NotesDb  extends RoomDatabase {
    private static NotesDb instance;
    public abstract NotesDao notesDao();
    public static NotesDb getDatabase(final Context context) {
        if(instance == null)
        {
            synchronized (Notes.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDb.class,"notes_db")
                            .build();
                }
            }
        }
        return instance;
    }
}

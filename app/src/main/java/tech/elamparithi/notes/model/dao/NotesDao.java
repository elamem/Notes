package tech.elamparithi.notes.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tech.elamparithi.notes.model.Entity.Notes;

@Dao
public interface NotesDao {

    @Insert
    void insert(Notes notes);

    @Query("SELECT * FROM Notes")
    LiveData<List<Notes>> getAllNotes();

    @Query("SELECT * FROM Notes WHERE id =:id")
    Notes getNotesById(int id);

    @Query("DELETE FROM Notes WHERE id =:id")
    void delete(int id);

    @Query("UPDATE Notes SET title = :title, body =:body WHERE id = :id")
    void update(int id, String title, String body);

}

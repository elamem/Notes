package tech.elamparithi.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import tech.elamparithi.notes.model.Entity.Notes;
import tech.elamparithi.notes.model.NotesDb;
import tech.elamparithi.notes.model.dao.NotesDao;

public class NotesViewModel extends AndroidViewModel {

    private final NotesDao notesDao;
    private final ThreadPoolExecutor executor;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        executor = new ThreadPoolExecutor(1, 3, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        NotesDb notesDb = NotesDb.getDatabase(application);
        notesDao = notesDb.notesDao();
    }

    public void insert(Notes notes) {

        executor.execute(() -> notesDao.insert(notes));

    }

    public LiveData<List<Notes>> getAllNotes() {
        return notesDao.getAllNotes();
    }

    public void update(int id, String title, String body) {

        executor.execute(() -> notesDao.update(id, title, body));

    }

    public void delete(int id) {

        executor.execute(() -> notesDao.delete(id));
    }

    public Notes getNotesById(int id) {

        return notesDao.getNotesById(id);
    }

}

package tech.elamparithi.notes.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import tech.elamparithi.notes.databinding.ActivityMainBinding;
import tech.elamparithi.notes.model.Entity.Notes;
import tech.elamparithi.notes.viewModel.NotesViewModel;
import tech.elamparithi.notes.viewModel.NotesViewModelFactory;

public class MainActivity extends AppCompatActivity {

    public static final String UPDATE_CONTENT = "updateContent";
    public static final String SELECTED_NOTE = "selected_note";
    ActivityMainBinding binding;
    NotesAdapter notesAdapter = null;

    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(MainActivity.this, new NotesViewModelFactory(getApplication())).get(NotesViewModel.class);

        notesAdapter = new NotesAdapter();

        notesViewModel.getAllNotes().observe(MainActivity.this, notes -> notesAdapter.submitList(notes));

        binding.rcNotes.setAdapter(notesAdapter);

        binding.fabAddNotes.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            intent.putExtra(UPDATE_CONTENT, false);
            startActivity(intent);

        });

        notesAdapter.setOnCardClickListener(id -> new Thread(() -> {

                    Notes note = notesViewModel.getNotesById(id);
                    Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                    intent.putExtra(UPDATE_CONTENT, true);
                    intent.putExtra(SELECTED_NOTE, note);
                    startActivity(intent);
                }).start()
        );

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int adapterPosition = viewHolder.getAdapterPosition();

                notesViewModel.delete(notesAdapter.getCurrentList().get(adapterPosition).getId());

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcNotes);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

}
package tech.elamparithi.notes.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import tech.elamparithi.notes.databinding.ActivityEditorBinding;
import tech.elamparithi.notes.model.Entity.Notes;
import tech.elamparithi.notes.viewModel.NotesViewModel;
import tech.elamparithi.notes.viewModel.NotesViewModelFactory;

public class EditorActivity extends AppCompatActivity {

    ActivityEditorBinding binding;

    boolean updateContent;

    private NotesViewModel notesViewModel;
    private Notes note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();

        notesViewModel = new ViewModelProvider(EditorActivity.this, new NotesViewModelFactory(getApplication())).get(NotesViewModel.class);

        Bundle extras = intent.getExtras();

        updateContent = extras.getBoolean(MainActivity.UPDATE_CONTENT);

        if (updateContent) {
            getNotes(extras);
        }

        setContentView(binding.getRoot());

    }

    private void getNotes(Bundle extras) {
        note = extras.getParcelable(MainActivity.SELECTED_NOTE);

        binding.etTitle.setText(note.getTitle());
        binding.etBody.setText(note.getBody());
    }

    @Override
    public void onBackPressed() {

        if (!Objects.requireNonNull(binding.etBody.getText()).toString().isEmpty()) {

            String title = Objects.requireNonNull(binding.etTitle.getText()).toString();

            String body = binding.etBody.getText().toString();

            if (updateContent) {
                notesViewModel.update(note.getId(), title, body);
            } else {
                Notes notes = new Notes(title, body);
                notesViewModel.insert(notes);
            }

        }

        super.onBackPressed();

    }
}

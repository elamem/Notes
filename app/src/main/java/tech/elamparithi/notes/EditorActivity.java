package tech.elamparithi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import tech.elamparithi.notes.databinding.ActivityEditorBinding;

public class EditorActivity extends AppCompatActivity {


    ActivityEditorBinding binding;
    DataBaseHelper dataBaseHelper;
    String title, body;
    boolean updateContent;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        updateContent = extras.getBoolean(MainActivity.UPDATE_CONTENT);

        if(updateContent){
            getNotes(extras);
        }

        setContentView(binding.getRoot());
        dataBaseHelper = new DataBaseHelper(EditorActivity.this);


    }

    private void getNotes(Bundle extras) {
        id = extras.getInt(MainActivity.ID);
        title = extras.getString(MainActivity.TITLE);
        body = extras.getString(MainActivity.BODY);

        binding.etTitle.setText(title);
        binding.etBody.setText(body);
    }

    @Override
    public void onBackPressed() {

        if(!binding.etBody.getText().toString().isEmpty()){


            String title = binding.etTitle.getText().toString();

            String body = binding.etBody.getText().toString();

            boolean result = false;
            if(updateContent){

                NotesModel notesModel = new NotesModel(id, title, body);


                 result = dataBaseHelper.updateOne(notesModel);

            }
            else {

                NotesModel notesModel = new NotesModel(-1, title, body);


                 result = dataBaseHelper.addOne(notesModel);

            }
            Log.e("IS SUCCESS ",String.valueOf(result));


        }

        super.onBackPressed();



    }
}

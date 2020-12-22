package tech.elamparithi.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import tech.elamparithi.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String UPDATE_CONTENT = "updateContent";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String ID = "id";
    ActivityMainBinding binding;
    List<NotesModel> notesList = new ArrayList<>();
    NotesAdapter notesAdapter = null;
    DataBaseHelper dataBaseHelper = null;
    private MenuItem count;
    private MenuItem selectAll;
    private MenuItem deleteSelected;
    private boolean selectAllActive = false;
    public static  List<Integer> selectedNotesIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        notesList = dataBaseHelper.getAllNotes();

        notesAdapter = new NotesAdapter(notesList);

        binding.rcNotes.setAdapter(notesAdapter);


        binding.fabAddNotes.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            intent.putExtra(UPDATE_CONTENT,false);
            startActivity(intent);


        });


        notesAdapter.setOnCardClickListener(new NotesAdapter.NotesUtil() {
                                                @Override
                                                public void cardClick(int position) {


                                                    Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                                                    intent.putExtra(UPDATE_CONTENT, true);
                                                    intent.putExtra(TITLE, notesList.get(position).getTitle());
                                                    intent.putExtra(BODY, notesList.get(position).getBody());
                                                    intent.putExtra(ID, notesList.get(position).getId());
                                                    startActivity(intent);


                                                }

                                                @Override
                                                public void updateSelectedNotesCount() {

                                                    if(selectedNotesIds.isEmpty()){

                                                        handleMenuItems(false,"0");

                                                    }
                                                    else {

                                                      handleMenuItems(true,String.valueOf(selectedNotesIds.size()));
                                                    }
                                                }
                                            }
        );

    }


    private void deleteSelectedNotes() {

        for(int id: selectedNotesIds) {
            boolean result = dataBaseHelper.deleteOne(id);
            Log.e("Delete result "+id +" ", String.valueOf(result));

        }
        selectedNotesIds.clear();
        handleMenuItems(false,"0");
        notesList = dataBaseHelper.getAllNotes();

        notesAdapter.setNotesData(notesList);
        notesAdapter.notifyDataSetChanged();

    }

    private void selectAllNotes() {

      int totalNotes =   binding.rcNotes.getChildCount();

        for(int i = 0 ; i<totalNotes;i++ ){

            MaterialCardView cardView = binding.rcNotes.getChildAt(i).findViewById(R.id.cvListItem);

            if(!cardView.isChecked()) {
                cardView.setChecked(true);
               selectedNotesIds.add( notesList.get(i).getId());
            }

        }

        count.setTitle(String.valueOf(totalNotes));

    }

    private void deselectAllNotes() {

        int totalNotes =   binding.rcNotes.getChildCount();

        for(int i = 0 ; i<totalNotes;i++ ){

            MaterialCardView cardView = binding.rcNotes.getChildAt(i).findViewById(R.id.cvListItem);

            if(cardView.isChecked()) {
                cardView.setChecked(false);
                selectedNotesIds.remove(new Integer( notesList.get(i).getId()));
            }

        }

        count.setTitle("0");
        handleMenuItems(false,"0");
    }

    private void handleMenuItems(boolean visibility,String countStr) {
        count.setTitle(countStr);

        count.setVisible(visibility);
        selectAll.setVisible(visibility);
        deleteSelected.setVisible(visibility);

        if(!visibility){
            binding.fabAddNotes.show();
        }
        else
        {
            binding.fabAddNotes.hide();
        }

    }


    @Override
    protected void onResume() {

        super.onResume();
        if (notesAdapter != null && dataBaseHelper != null) {
            notesList = dataBaseHelper.getAllNotes();
            notesAdapter.setNotesData(notesList);
            notesAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.list_menu,menu);

         count = menu.findItem(R.id.count);
         selectAll = menu.findItem(R.id.selectAll);
         deleteSelected = menu.findItem(R.id.deleteSelected);

        count.setVisible(false);
        selectAll.setVisible(false);
        deleteSelected.setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.deleteSelected:

                deleteSelectedNotes();

                return true;

            case R.id.selectAll:

                if(selectAllActive){
                    selectAllActive = false;
                    deselectAllNotes();
                }
                else {
                    selectAllActive = true;
                    selectAllNotes();
                }
            default:
                return super.onOptionsItemSelected(item);



        }

    }




}
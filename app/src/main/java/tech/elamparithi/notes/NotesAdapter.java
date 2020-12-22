package tech.elamparithi.notes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.elamparithi.notes.databinding.NotesItemViewBinding;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    List<NotesModel> allNotesList;
    private NotesUtil notesUtil;

    NotesAdapter(List<NotesModel> allNotesList) {
        this.allNotesList = allNotesList;
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new NotesViewHolder(NotesItemViewBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {


        holder.binding.txtTitle.setText(allNotesList.get(position).getTitle());
        holder.binding.txtBody.setText(allNotesList.get(position).getBody());

        holder.binding.cvListItem.setChecked(false);

        holder.binding.cvListItem.setOnClickListener(v -> {

            if (MainActivity.selectedNotesIds.size() > 0){

                if(holder.binding.cvListItem.isChecked()) {
                    holder.binding.cvListItem.setChecked(false);
                    MainActivity.selectedNotesIds.remove(new Integer(allNotesList.get(position).getId()));

                }
                else{
                    holder.binding.cvListItem.setChecked(true);
                    MainActivity.selectedNotesIds.add(allNotesList.get(position).getId());
                }

                notesUtil.updateSelectedNotesCount();

            }
            else
                notesUtil.cardClick(position);


        });
        holder.binding.cvListItem.setOnLongClickListener(v -> {

            if(holder.binding.cvListItem.isChecked()) {
                holder.binding.cvListItem.setChecked(false);
                MainActivity.selectedNotesIds.remove(new Integer(allNotesList.get(position).getId()));

            }
            else{
                holder.binding.cvListItem.setChecked(true);
                MainActivity.selectedNotesIds.add(allNotesList.get(position).getId());
            }


            notesUtil.updateSelectedNotesCount();


            return true;
        });

    }

    @Override
    public int getItemCount() {
        return allNotesList.size();
    }

    public void setNotesData(List<NotesModel> notesList) {
        allNotesList.clear();
        allNotesList.addAll(notesList);
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        NotesItemViewBinding binding;

        public NotesViewHolder(@NonNull NotesItemViewBinding notesItemViewBinding) {
            super(notesItemViewBinding.getRoot());

            binding = notesItemViewBinding;


        }
    }

    public interface NotesUtil {

        void cardClick(int position);

        void updateSelectedNotesCount();
    }

    public void setOnCardClickListener(NotesUtil notesUtil) {
        this.notesUtil = notesUtil;
    }


}

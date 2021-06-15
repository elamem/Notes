package tech.elamparithi.notes.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import tech.elamparithi.notes.databinding.NotesItemViewBinding;
import tech.elamparithi.notes.model.Entity.Notes;

public class NotesAdapter extends ListAdapter<Notes, NotesAdapter.NotesViewHolder> {

    private NotesUtil notesUtil;

    private static final DiffUtil.ItemCallback<Notes> NOTES_DIFF_UTIL = new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getBody().equals(newItem.getBody());
        }
    };

    NotesAdapter() {
        super(NOTES_DIFF_UTIL);

    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NotesViewHolder notesViewHolder = new NotesViewHolder(NotesItemViewBinding.inflate(LayoutInflater.from(parent.getContext())));

        notesViewHolder.binding.cvListItem.setOnClickListener(v -> notesUtil.cardClick(getItem(notesViewHolder.getAdapterPosition()).getId()));

        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {

        Notes note = getItem(position);
        holder.binding.txtTitle.setText(note.getTitle());
        holder.binding.txtBody.setText(note.getBody());
        holder.binding.cvListItem.setChecked(note.isSelected());

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        NotesItemViewBinding binding;

        public NotesViewHolder(@NonNull NotesItemViewBinding notesItemViewBinding) {
            super(notesItemViewBinding.getRoot());

            binding = notesItemViewBinding;

        }
    }

    public interface NotesUtil {

        void cardClick(int id);

    }

    public void setOnCardClickListener(NotesUtil notesUtil) {
        this.notesUtil = notesUtil;
    }

}

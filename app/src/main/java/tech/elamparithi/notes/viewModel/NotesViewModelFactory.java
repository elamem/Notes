package tech.elamparithi.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NotesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;

    public NotesViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == NotesViewModel.class) {
            return (T) new NotesViewModel(application);
        }
        return null;
    }
}

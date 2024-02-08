package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import app.demo.mycontacts.model.Contact;

public class ContactDetailViewModelFactory implements ViewModelProvider.Factory {

    private final Contact contact;
    private final Application application;

    public ContactDetailViewModelFactory(@NonNull Application application, Contact contact) {
        this.application = application;
        this.contact = contact;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new ContactDetailViewModel(application, contact);
        }
        throw new IllegalArgumentException("Unknown ContactDetailViewModel class");
    }
}

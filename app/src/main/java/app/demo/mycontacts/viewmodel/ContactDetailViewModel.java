package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.model.ContactDetail;
import app.demo.mycontacts.repository.ContactRepository;

public class ContactDetailViewModel extends ViewModel  {
    private final LiveData<ContactDetail> contactDetailLiveData = new MutableLiveData<>();

    public ContactDetailViewModel(@NonNull Application application, Contact contact) {
        ContactRepository contactRepository = new ContactRepository(application);
        ((MutableLiveData<ContactDetail>) contactDetailLiveData).setValue(contactRepository.getContactDetail(contact));

    }

    public LiveData<ContactDetail> getSelectedContact() {
        return contactDetailLiveData;
    }
}
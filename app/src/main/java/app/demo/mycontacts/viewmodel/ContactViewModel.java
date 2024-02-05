package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.repository.ContactRepository;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);

        List<Contact> sampleContacts = new ArrayList<>();
        sampleContacts.add(new Contact("John Doe", "123-456-7890"));
        insertContact(sampleContacts.get(0));

        allContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insertContact(Contact contact) {
        contactRepository.insertContact(contact);
    }


    public void insertContactList(List<Contact> contactList) {
        for (Contact contact : contactList) {
            insertContact(contact);
        }
    }
}
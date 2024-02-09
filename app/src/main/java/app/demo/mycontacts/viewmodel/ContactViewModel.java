package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.repository.ContactRepository;
public class ContactViewModel extends AndroidViewModel {
    private final ContactManager contactManager;
    private final ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
        contactManager = new ContactManager(contactRepository);
        //fetchUniqueContacts();
    }

    public void fetchUniqueContacts() {
        List<Contact> uniqueContacts = contactRepository.getUniqueContacts();
        updateContactsFromDevice(uniqueContacts);
    }

    private void updateContactsFromDevice(List<Contact> deviceContacts) {
        contactManager.updateHashMap(deviceContacts);

        if (allContacts != null && allContacts.getValue() != null && allContacts.getValue().size() > 0)
            contactManager.compareAndUpdateDatabase(allContacts.getValue());

        contactManager.addHashMapContactIntoDatabase();
        //allContacts = contactRepository.getAllContacts();//???????????!!!!!!!!!!!
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }
}
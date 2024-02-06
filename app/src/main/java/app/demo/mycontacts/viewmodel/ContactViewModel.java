package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.repository.ContactRepository;
public class ContactViewModel extends AndroidViewModel {

private ContactManager contactManager;
private ContactRepository contactRepository;
private LiveData<List<Contact>> allContacts;

public ContactViewModel(@NonNull Application application){
    super(application);
    contactRepository = new ContactRepository(application);
    allContacts = contactRepository.getAllContacts();

    contactManager = new ContactManager(contactRepository);
}

public void updateContactsFromDevice(List<Contact> deviceContacts) {
    contactManager.updateHashMap(deviceContacts);

    if (allContacts!=null && allContacts.getValue()!=null && allContacts.getValue().size()>0)
        contactManager.compareAndUpdateDatabase(allContacts.getValue());

    contactManager.addHashMapContactIntoDatabase();
}

/*public void compareAndUpdateDatabase(List<Contact> databaseContacts) {
    contactManager.compareAndUpdateDatabase(databaseContacts);
}*/

public void insertContactList(@NonNull List<Contact> contactList) {
    for (Contact contact : contactList) {
        insertContact(contact);
    }
}
public void insertContact(@NonNull Contact contact){

     contactRepository.insertContact(contact);
}

public void removeDeletedContacts(){

    contactRepository.deleteContacts();
}

public void setContactsNotExist() {
   contactRepository.setAllContactsNotExist();
}

public LiveData<List<Contact>> getAllContacts(){
    return allContacts;
}
}
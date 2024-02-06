package app.demo.mycontacts.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.repository.ContactRepository;
public class ContactViewModel extends AndroidViewModel {
private ContactRepository contactRepository;
private LiveData<List<Contact>> allContacts;

public ContactViewModel(@NonNull Application application){
    super(application);
    contactRepository = new ContactRepository(application);
    //mock data
    insertContact(new Contact("John Doe", "123-456-7890"));
    allContacts = contactRepository.getAllContacts();
}

public void insertContact(Contact contact){
    contactRepository.insertContact(contact);
}

public LiveData<List<Contact>> getAllContacts(){
    return allContacts;
}
}
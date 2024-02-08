package app.demo.mycontacts.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import app.demo.mycontacts.dao.ContactDao;
import app.demo.mycontacts.database.AppDatabase;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.model.ContactDetail;
import app.demo.mycontacts.model.ContactFetcher;

public class ContactRepository {
private ContactDao contactDao;
private Context  context;

public ContactRepository(Application application) {
    AppDatabase db = AppDatabase.getInstance(application);
    contactDao = db.contactDao();
    this.context  = application.getApplicationContext();
}

public LiveData<List<Contact>> getAllContacts() {
    return contactDao.getAllContacts();
}

public List<Contact> getUniqueContacts() {
    return ContactFetcher.fetchUniqueContacts(context);
}



public ContactDetail getContactDetail(Contact contact) {
    return ContactFetcher.fetchUniqueContact(context, contact);
}




public void insertContact(Contact contact) {
    new InsertContactAsyncTask(contactDao).execute(contact);
}

public void updateContact(Contact contact) {
    new UpdateContactAsyncTask(contactDao).execute(contact);
}


public void deleteContacts(Contact contact) {
    new DeleteContactsAsyncTask(contactDao).execute(contact);
}





private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
    private ContactDao contactDao;

    private InsertContactAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Contact... contacts) {
        contactDao.insert(contacts[0]);
        return null;
    }
}

private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
    private ContactDao contactDao;

    private UpdateContactAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Contact... contacts) {
        contactDao.updateContact(contacts[0]);
        return null;
    }
}

private static class DeleteContactsAsyncTask extends AsyncTask<Contact, Void, Void> {
    private ContactDao contactDao;

    private DeleteContactsAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Contact... contacts){
        contactDao.deleteContact(contacts[0]);
        return null;
    }
}

}

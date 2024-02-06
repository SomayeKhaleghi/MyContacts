package app.demo.mycontacts.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import app.demo.mycontacts.dao.ContactDao;
import app.demo.mycontacts.database.AppDatabase;
import app.demo.mycontacts.model.Contact;

public class ContactRepository {
private ContactDao contactDao;

public ContactRepository(Application application) {
    AppDatabase db = AppDatabase.getInstance(application);
    contactDao = db.contactDao();
}

public LiveData<List<Contact>> getAllContacts() {
    return contactDao.getAllContacts();
}

public void insertContact(Contact contact) {
    new InsertContactAsyncTask(contactDao).execute(contact);
}

public void updateContact(Contact contact) {
    new UpdateContactAsyncTask(contactDao).execute(contact);
}


public void deleteContacts() {
    new DeleteContactsAsyncTask(contactDao).execute();
}


public void setAllContactsNotExist() {
    new UpdateContactsExistanceAsyncTask(contactDao).execute();
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

private static class DeleteContactsAsyncTask extends AsyncTask<Void, Void, Void> {
    private ContactDao contactDao;

    private DeleteContactsAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Void... voids){
        contactDao.removeDeletedContacts();
        return null;
    }
}


private static class UpdateContactsExistanceAsyncTask extends AsyncTask<Void, Void, Void> {
    private ContactDao contactDao;

    private UpdateContactsExistanceAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Void... voids){
        contactDao.setAllContactsNotExist();
        return null;
    }
}
}

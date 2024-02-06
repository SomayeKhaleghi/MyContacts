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
}
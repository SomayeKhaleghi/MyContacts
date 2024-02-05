package app.demo.mycontacts.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import app.demo.mycontacts.model.Contact;
@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Query("SELECT * FROM contact_table")
    LiveData<List<Contact>> getAllContacts();
}
package app.demo.mycontacts.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import app.demo.mycontacts.model.Contact;
@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Query("SELECT * FROM contact ORDER BY name;")
    LiveData<List<Contact>> getAllContacts();

    @Delete
    void deleteContact(Contact contact);
}
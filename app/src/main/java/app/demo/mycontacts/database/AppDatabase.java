package app.demo.mycontacts.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import app.demo.mycontacts.R;
import app.demo.mycontacts.dao.ContactDao;
import app.demo.mycontacts.model.Contact;
@Database(entities = {Contact.class}, version = 13)
public abstract class AppDatabase extends RoomDatabase {

private static volatile AppDatabase instance;

public abstract ContactDao contactDao();

public static synchronized AppDatabase getInstance(Context context) {

    String DATABASE_NAME = context.getString(R.string.dbName);
    if (instance == null) {
        instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
        ).build();
    }
    return instance;
}
}
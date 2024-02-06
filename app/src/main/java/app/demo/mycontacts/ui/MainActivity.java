package app.demo.mycontacts.ui;

import static android.provider.ContactsContract.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.viewmodel.ContactViewModel;

public class MainActivity extends AppCompatActivity  implements   ContactAdapter.ItemEventListener {
    private ContactViewModel contactViewModel;
    private ContactAdapter contactAdapter;
    private static final int PERMISSION_REQUEST_READ_CONTACTS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactAdapter = new ContactAdapter(new ArrayList<>(), this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        //((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);

        recyclerView.setAdapter(contactAdapter);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        } else {
            loadPhoneContacts();
        }

        contactViewModel.getAllContacts().observe(this, contacts -> {
            // Update the UI with the new list of contacts
            contactAdapter.setContactList(contacts);
            //  recyclerView.scrollToPosition(0);
        });
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadPhoneContacts();
        } else {
            // Handle permission denied
            Toast.makeText(this, "Permission denied. Unable to fetch contacts.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private void loadPhoneContacts() {
        //contactViewModel.setContactsNotExist();
       /*   List<Contact> contactList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CommonDataKinds.Phone.CONTENT_URI, null, null, null, CommonDataKinds.Phone.DISPLAY_NAME);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact(id, name, phoneNumber, true);
                contactList.add(contact);


            }
            cursor.close();

            Log.d("somaye2020", "row count: " +contactList.size());
          if (contactList.size() > 0) {
                for (Contact contact : contactList) {
                    contactViewModel.insertContact(contact);
                }
            }
        }
*/
        //contactViewModel.insertContact(new Contact("1","somaye", "09051591030", true));
        //contactViewModel.insertContact(new Contact("2","ramin", "09124653573", true));
        //contactViewModel.insertContact(new Contact("3","zahra", "09391032366", true));

   /*List<Contact> contactList = new ArrayList<>();
     for (int i =0 ; i<400 ; i++)
    {
        contactList.add(new Contact(i+"","zahra"+i, "09391032366", true));

    }
    */
       // contactViewModel.updateContactsFromDevice(contactList);
        //contactViewModel.insertContactList(contactList);
        //contactViewModel.removeDeletedContacts();


     contactViewModel.fetchUniqueContacts();
    }

    @Override
    public void onItemClick(Contact contact, int position) {
        String tmp = "id: " + contact.getId() + ", name:" + contact.getName();
        Toast.makeText(this, tmp, Toast.LENGTH_SHORT).show();
        Log.d("somaye2020", "id: " + contact.getId() + ", name:" + contact.getName());
    }
}
package app.demo.mycontacts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.viewmodel.ContactViewModel;
import app.demo.mycontacts.R;
public class MainActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new ContactAdapter(new ArrayList<>());
        recyclerView.setAdapter(contactAdapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        // Fetch contacts and insert a sample contact (you may replace it with actual logic)


        contactViewModel.getAllContacts().observe(this, contacts -> {
            // Update the UI with the new list of contacts
            contactAdapter.setContactList(contacts);
        });


        // Request permissions if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            loadPhoneContacts();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1); // You can use any request code here
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadPhoneContacts();
        } else {
            // Handle permission denied
            Toast.makeText(this, "Permission denied. Unable to fetch contacts.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void loadPhoneContacts() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
        );

        List<Contact> phoneContacts = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));

                phoneContacts.add(new Contact(name, phoneNumber));
            }
            cursor.close();
        }

        // Insert phone contacts into the ViewModel
        contactViewModel.insertContactList(phoneContacts);
    }
}
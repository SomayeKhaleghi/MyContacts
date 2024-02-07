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
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
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

        recyclerView.setAdapter(contactAdapter);
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_READ_CONTACTS);
        } else {
            loadPhoneContacts();
        }


       contactViewModel.getAllContacts().observe(this, contacts -> {
            contactAdapter.setContactList(contacts);
            //  recyclerView.scrollToPosition(0);
        });
    }

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

    private void loadPhoneContacts() {
       contactViewModel.fetchUniqueContacts();
    }

    @Override
    public void onItemClick(Contact contact, int position) {
        String tmp = "id: " + contact.getId() + ", name:" + contact.getName();
        Toast.makeText(this, tmp, Toast.LENGTH_SHORT).show();
        Log.d("somaye2020", "id: " + contact.getId() + ", name:" + contact.getName());
    }
}
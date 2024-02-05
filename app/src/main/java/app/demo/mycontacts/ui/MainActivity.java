package app.demo.mycontacts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        List<Contact> sampleContacts = new ArrayList<>();
        sampleContacts.add(new Contact("John Doe", "123-456-7890"));
        contactViewModel.insertContact(sampleContacts.get(0));

        contactViewModel.getAllContacts().observe(this, contacts -> {
            // Update the UI with the new list of contacts
            contactAdapter.setContactList(contacts);
        });


    }
}
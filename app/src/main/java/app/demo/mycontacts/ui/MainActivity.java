package app.demo.mycontacts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import app.demo.mycontacts.R;
import app.demo.mycontacts.viewmodel.ContactViewModel;

public class MainActivity extends AppCompatActivity {
private ContactViewModel contactViewModel;
private ContactAdapter contactAdapter;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    contactAdapter = new ContactAdapter(new ArrayList<>());

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(contactAdapter);

    contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
    contactViewModel.getAllContacts().observe(this, contacts -> {
        // Update the UI with the new list of contacts
        contactAdapter.setContactList(contacts);
    });
}

}
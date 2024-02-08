package app.demo.mycontacts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;
import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.utils.Constants;
import app.demo.mycontacts.viewmodel.ContactViewModel;

public class MainActivity extends AppCompatActivity  implements   ContactAdapter.ItemEventListener {

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, Constants.PERMISSION_REQUEST_READ_CONTACTS);
            } else {
                loadPhoneContacts();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQUEST_READ_CONTACTS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadPhoneContacts();
        } else {
            Toast.makeText(this, "Permission denied. Unable to fetch contacts.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPhoneContacts() {
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ContactListFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onItemClick(Contact contact, int position) {

        ContactDetailFragment fragment = new ContactDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, contact.getId());
        bundle.putString(Constants.NAME, contact.getName());
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
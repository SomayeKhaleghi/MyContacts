package app.demo.mycontacts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.widget.Toast;
import java.util.Objects;
import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.utils.Constants;
import app.demo.mycontacts.viewmodel.ContactViewModel;

public class MainActivity extends AppCompatActivity  implements  ContactAdapter.ItemEventListener {
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


    @Override
    public void onItemClick(Contact contact, int position) {

        ContactDetailFragment fragment = new ContactDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, contact.getId());
        bundle.putString(Constants.NAME, contact.getName());
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(ContactDetailFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();

        if (backStackEntryCount > 0) {
            String topFragmentTag = fragmentManager.getBackStackEntryAt(backStackEntryCount - 1).getName();
            if (!Objects.equals(topFragmentTag, ContactDetailFragment.class.getSimpleName())) {
                showExitDialog();
                return;
            }
        }

        super.onBackPressed();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_app)
                .setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    finish();
                    finishAffinity();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAndRemoveTask();

                    }
                    Process.killProcess(Process.myPid());
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                })
                .show();
    }

    boolean onRestartFetched = false;
    private ContactViewModel contactViewModel;

    private void loadPhoneContacts() {

        if (contactViewModel == null) {
            contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ContactListFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    protected void onResume() {

        if (contactViewModel == null) {
            contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        } else {
            if (onRestartFetched)
                onRestartFetched = false;
            else {
                contactViewModel.fetchUniqueContacts();
            }
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {

        if (contactViewModel == null) {
            onRestartFetched = true;
            contactViewModel.fetchUniqueContacts();
        }

        super.onRestart();
    }
}
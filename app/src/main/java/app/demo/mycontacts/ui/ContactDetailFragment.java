package app.demo.mycontacts.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.utils.Constants;
import app.demo.mycontacts.viewmodel.ContactDetailViewModel;
import app.demo.mycontacts.viewmodel.ContactDetailViewModelFactory;

public class ContactDetailFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewPhoneNumber;
    private TextView textViewEmailAddress;
    private TextView textViewWhatsapp;
    private TextView textViewTelegram;
    ContactDetailViewModel contactDetailViewModel;

    public ContactDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);

        textViewName = view.findViewById(R.id.textViewName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewEmailAddress = view.findViewById(R.id.textViewEmailAddress);
        textViewWhatsapp = view.findViewById(R.id.textViewWhatsapp);
        textViewTelegram = view.findViewById(R.id.textViewTelegram);

        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey(Constants.ID) || !arguments.containsKey(Constants.NAME)) {
            return view;
        }

        Integer id = arguments.getInt(Constants.ID);
        String name = arguments.getString(Constants.NAME);
        assert name != null;
        Contact contact = new Contact(id, name);

        ContactDetailViewModelFactory factory = new ContactDetailViewModelFactory(requireActivity().getApplication(), contact);
        contactDetailViewModel = new ViewModelProvider((ViewModelStoreOwner) this, factory).get(ContactDetailViewModel.class);
        contactDetailViewModel.getSelectedContact().observe(getViewLifecycleOwner(), contactDetail -> {
            textViewName.setText(contactDetail.getContact().getName());
            textViewPhoneNumber.setText(contactDetail.getPhoneNumber());
            textViewEmailAddress.setText(contactDetail.getEmail());
            textViewWhatsapp.setText(contactDetail.getWhatsApp());
            textViewTelegram.setText(contactDetail.getTelegram());
        });

        return view;
    }
}
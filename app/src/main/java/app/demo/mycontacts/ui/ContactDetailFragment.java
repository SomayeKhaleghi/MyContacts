package app.demo.mycontacts.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.model.ContactDetail;
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
        // Required empty public constructor
    }

    public static ContactDetailFragment newInstance(String param1, String param2) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        Bundle args = getArguments();
        if (args == null)
            return view;

        String id = args.getString(Constants.ID);
        String name = args.getString(Constants.NAME);
        assert name != null;
        assert id != null;
        Contact contact = new Contact(id, name);

        ContactDetailViewModelFactory factory = new ContactDetailViewModelFactory(requireActivity().getApplication(), contact);
        contactDetailViewModel = new ViewModelProvider((ViewModelStoreOwner) this, factory).get(ContactDetailViewModel.class);
        contactDetailViewModel.getSelectedContact().observe(getViewLifecycleOwner(), contactDetail -> {
            textViewName.setText(contactDetail.getContact().getName());
            textViewPhoneNumber.setText( contactDetail.getPhoneNumber());
            textViewEmailAddress.setText(contactDetail.getEmail());
            textViewWhatsapp.setText(contactDetail.getWhatsApp());
            textViewTelegram.setText(contactDetail.getTelegram());
        });

        return view;
    }
}
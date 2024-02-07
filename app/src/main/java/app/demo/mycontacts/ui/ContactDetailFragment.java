package app.demo.mycontacts.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.demo.mycontacts.R;
import app.demo.mycontacts.utils.Constants;

public class ContactDetailFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewPhoneNumber;
    private TextView textViewEmailAddress;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contact_detail, container, false);

        textViewName = view.findViewById(R.id.textViewName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewEmailAddress = view.findViewById(R.id.textViewEmailAddress);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(Constants.NAME);
            String phoneNumber = args.getString(Constants.PHONE_NUMBER);
            String email = args.getString(Constants.EMAIL_ADDRESS);

            assert name != null;
            if (!name.isEmpty())
                textViewName.setText(name);

            assert phoneNumber != null;
            if (!phoneNumber.isEmpty())
                textViewPhoneNumber.setText(getString(R.string.phone_number)+" "+phoneNumber);

            assert email != null;
            if (!email.isEmpty())
                textViewEmailAddress.setText(getString(R.string.email_address)+" "+email);
        }

        return  view;
    }
}
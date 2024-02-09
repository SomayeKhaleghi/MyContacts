package app.demo.mycontacts.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import app.demo.mycontacts.R;
import app.demo.mycontacts.viewmodel.ContactViewModel;

public class ContactListFragment extends Fragment {
    private ContactViewModel contactViewModel;
    private ContactAdapter contactAdapter;

    public ContactListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactViewModel = new ViewModelProvider( requireActivity()).get(ContactViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        contactAdapter = new ContactAdapter(new ArrayList<>(), (ContactAdapter.ItemEventListener) requireActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(contactAdapter);

        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> contactAdapter.setContactList(contacts));

        return view;
    }

}
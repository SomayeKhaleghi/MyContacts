package app.demo.mycontacts.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

        Log.d("somaye", "ListFragment, onCreateView, savedInstanceState= "+ ((savedInstanceState==null)?"null":"not null"));
        contactViewModel = new ViewModelProvider( requireActivity()).get(ContactViewModel.class);
        //  loadPhoneContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("somaye", "ListFragment, onCreateView, savedInstanceState= "+ ((savedInstanceState==null)?"null":"not null"));
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

      /*  if (savedInstanceState!=null)
            return  view;*/

        contactAdapter = new ContactAdapter(new ArrayList<>(), (ContactAdapter.ItemEventListener) requireActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(contactAdapter);


        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> contactAdapter.setContactList(contacts));



        return view;
    }

   /* private void loadPhoneContacts() {
        contactViewModel.fetchUniqueContacts();
    }*/


    @Override
    public void onResume() {
        Log.d("somaye", "ListFragment, onResume");
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("somaye", "ListFragment, onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("somaye", "ListFragment, onViewStateRestored, savedInstanceState="+((savedInstanceState==null)?"null": "not null"));
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.d("somaye", "ListFragment, onPause");
        super.onPause();
    }
}
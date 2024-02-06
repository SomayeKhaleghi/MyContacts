package app.demo.mycontacts.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import app.demo.mycontacts.R;
import app.demo.mycontacts.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
private List<Contact> contactList;
private ItemEventListener itemEventListener;

public ContactAdapter(List<Contact> contactList, ItemEventListener itemEventListener){
    this.contactList = contactList;
    this.itemEventListener = itemEventListener;
}

@NonNull
@Override
public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
    return new ContactViewHolder(itemView);
}

@Override
public void onBindViewHolder(@NonNull ContactViewHolder holder, int position){
    Contact contact = contactList.get(position);
    holder.bindContact(contact);
}

@Override
public int getItemCount(){
    return contactList.size();
}

@SuppressLint("NotifyDataSetChanged")
public void setContactList(List<Contact> contacts){
    this.contactList = contacts;
    Log.d("ContactAdapter", "setContactList, notifyDataSetChanged()");
    notifyDataSetChanged();
}
public class ContactViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewPhoneNumber;
    TextView textViewId;

    ContactViewHolder(@NonNull View itemView){
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
        textViewId = itemView.findViewById(R.id.textViewId);
    }


    public void bindContact(final Contact contact){
        textViewName.setText(contact.getName());
        textViewPhoneNumber.setText(contact.getPhoneNumber());
        textViewId.setText(contact.getId());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
              itemEventListener.onItemClick(contact, getAdapterPosition());
            }
        });
    }
}

public interface ItemEventListener {
    void onItemClick(Contact contact, int position);
}

}
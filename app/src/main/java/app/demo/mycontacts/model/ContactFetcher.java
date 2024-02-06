package app.demo.mycontacts.model;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactFetcher {

    public static List<Contact> fetchUniqueContacts(Context context) {
        List<Contact> uniqueContacts = new ArrayList<>();
        Set<String> uniqueContactIds = new HashSet<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (uniqueContactIds.add(contactId)) {
                    @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactPhoneNumber = getContactPhoneNumber(contentResolver, contactId);
                    String skypeId = ""; // Implement method to retrieve Skype ID
                    String telegramId = ""; // Implement method to retrieve Telegram ID
                    String email = ""; // Implement method to retrieve email
                    Contact contact = new Contact(contactId, contactName, contactPhoneNumber);
                    uniqueContacts.add(contact);
                    Log.d("somaye", "id: " +contactId + ", name:" +contactName+", phone:"+contactPhoneNumber);
                }
            }
            cursor.close();
        }


        Log.d("somaye2020", "row count: " +uniqueContactIds.size());
        return uniqueContacts;
    }

    @SuppressLint("Range")
    private static String getContactPhoneNumber(ContentResolver contentResolver, String contactId) {
        Cursor phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        String phoneNumber = "";
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }

        return phoneNumber;
    }
}

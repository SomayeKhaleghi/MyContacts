package app.demo.mycontacts.model;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
                    String contactPhoneNumber =getContactPhoneNumber(contentResolver, contactId);
                    String contactEmail =getContactEmail(contentResolver, contactId);

                    Contact contact = new Contact(contactId, contactName, contactPhoneNumber, contactEmail);
                    uniqueContacts.add(contact);

                    Log.d("Somaye" , "name:"+ contactName +", phone:"+ contactPhoneNumber + ", email"+ contactEmail);
                }
            }
            cursor.close();
        }

        return uniqueContacts;
    }

    @SuppressLint("Range")
    private static String getContactPhoneNumber(ContentResolver contentResolver, String contactId) {
        return getContactInfo(contentResolver , ContactsContract.CommonDataKinds.Phone.CONTENT_URI , ContactsContract.CommonDataKinds.Phone.CONTACT_ID, contactId ,  ContactsContract.CommonDataKinds.Phone.NUMBER);
    }

    private static String getContactEmail(ContentResolver contentResolver, String contactId) {

        return getContactInfo(contentResolver , ContactsContract.CommonDataKinds.Email.CONTENT_URI , ContactsContract.CommonDataKinds.Email.CONTACT_ID, contactId ,  ContactsContract.CommonDataKinds.Email.ADDRESS);
    }



    @SuppressLint("Range")
    private static String getContactInfo(ContentResolver contentResolver, Uri uri,String selection,  String contactId, String columnName) {
        Cursor phoneCursor = contentResolver.query(
                uri,
                null,
                selection + " = ?",
                new String[]{contactId},
                null
        );

        String info = "";
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            info = phoneCursor.getString(phoneCursor.getColumnIndex(columnName));
            phoneCursor.close();
        }

        return info;
    }

}

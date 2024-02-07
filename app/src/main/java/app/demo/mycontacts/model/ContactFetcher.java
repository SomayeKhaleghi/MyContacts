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

public static List<Contact> fetchUniqueContacts(Context context){
    List<Contact> uniqueContacts = new ArrayList<>();
    Set<String> uniqueContactIds = new HashSet<>();
    ContentResolver contentResolver = context.getContentResolver();
    Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (uniqueContactIds.add(contactId)) {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactPhoneNumber = "";//getContactPhoneNumber(contentResolver, contactId);
                String contactEmail = "";//getContactEmail(contentResolver, contactId);
                Contact contact = new Contact(contactId, contactName, contactPhoneNumber, contactEmail);
                uniqueContacts.add(contact);
                //Log.d("Somaye" , "name:"+ contactName +", phone:"+ contactPhoneNumber + ", email"+ contactEmail);
            }
        }
        cursor.close();
    }
    return uniqueContacts;
}


public static List<Contact> fetchUniqueContact(Context context, String id){
    List<Contact> uniqueContacts = new ArrayList<>();
    Set<String> uniqueContactIds = new HashSet<>();
    ContentResolver contentResolver = context.getContentResolver();
    Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (!id.equals(contactId))
                continue;

            if (uniqueContactIds.add(contactId)) {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactPhoneNumber = getContactPhoneNumber(contentResolver, contactId);
                String contactEmail = getContactEmail(contentResolver, contactId);
                String getContactSkypeId = getContactSkypeId(contentResolver, contactId);
                String getContactTelegramId = getContactTelegramId(contentResolver, contactId);
                String getWhatsAppId = getContactWhatsappId(contentResolver, contactId);

                Log.d("Somaye" , "name:"+ contactName +", phone:"+ contactPhoneNumber + ", email"+ contactEmail+ ", skype:" + getContactSkypeId+", telegram:"+ getContactTelegramId + ", whatsapp:"+ getWhatsAppId);
                break;
            }
        }
        cursor.close();
    }
    return uniqueContacts;
}

@SuppressLint("Range")
private static String getContactPhoneNumber(ContentResolver contentResolver, String contactId){
    return getContactInfo(contentResolver, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, contactId, ContactsContract.CommonDataKinds.Phone.NUMBER);
}

private static String getContactEmail(ContentResolver contentResolver, String contactId){
    return getContactInfo(contentResolver, ContactsContract.CommonDataKinds.Email.CONTENT_URI, ContactsContract.CommonDataKinds.Email.CONTACT_ID, contactId, ContactsContract.CommonDataKinds.Email.ADDRESS);
}


@SuppressLint("Range")
private static String getContactInfo(ContentResolver contentResolver, Uri uri, String selection, String contactId, String columnName){
    Cursor phoneCursor = contentResolver.query(uri, null, selection + " = ?", new String[]{contactId}, null);
    String info = "";
    if (phoneCursor != null && phoneCursor.moveToFirst()) {
        info = phoneCursor.getString(phoneCursor.getColumnIndex(columnName));
        phoneCursor.close();
    }
    return info;
}

@SuppressLint("Range")
private static String getContactSkypeId(ContentResolver contentResolver, String contactId){
    String skypeId = "";
    Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.Im.PROTOCOL + " = ?", new String[]{contactId, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE, String.valueOf(ContactsContract.CommonDataKinds.Im.PROTOCOL_SKYPE)}, null);
    if (cursor != null && cursor.moveToFirst()) {
        skypeId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
        cursor.close();
    }
    return skypeId;
}

@SuppressLint("Range")
public static String getContactTelegramId(ContentResolver contentResolver, String contactId) {
    String telegramId = null;
    Cursor cursor = null;

    try {
        cursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.CONTACT_ID + " = ? AND " +
                        ContactsContract.Data.MIMETYPE + " = ?",
                new String[]{contactId, "vnd.android.cursor.item/im"},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            telegramId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1));
        }
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }

    return telegramId;
}

@SuppressLint("Range")
public static String getContactWhatsappId(ContentResolver contentResolver, String contactId) {
    String whatsappId = null;
    Cursor cursor = null;

    try {
        cursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.CONTACT_ID + " = ? AND " +
                        ContactsContract.Data.MIMETYPE + " = ?",
                new String[]{contactId, "vnd.android.cursor.item/vnd.com.whatsapp.profile"},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            whatsappId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1));
        }
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }

    return whatsappId;
}
}

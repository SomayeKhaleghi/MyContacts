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
        Set<Integer> uniqueContactIds = new HashSet<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") Integer contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (uniqueContactIds.add(contactId)) {
                    @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Contact contact = new Contact(contactId, contactName);
                    uniqueContacts.add(contact);

                }
            }
            cursor.close();
        }
        return uniqueContacts;
    }

    public static ContactDetail fetchUniqueContact(Context context, Contact contact) {
        ContactDetail contactDetail = new ContactDetail(contact);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") Integer contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (!contact.getId().equals(contactId))
                    continue;

                String contactPhoneNumber = getContactPhoneNumber(contentResolver, contactId);
                String contactEmail = getContactEmail(contentResolver, contactId);
                String getContactTelegramId = getContactTelegramId(contentResolver, contactId);
                String getWhatsAppId = getContactWhatsappId(contentResolver, contactId);
                contactDetail.setEmail(contactEmail);
                contactDetail.setPhoneNumber(contactPhoneNumber);
                contactDetail.setWhatsApp(getWhatsAppId);
                contactDetail.setTelegram(getContactTelegramId);

                break;
            }
            cursor.close();
        }
        return contactDetail;
    }

    @SuppressLint("Range")
    private static String getContactPhoneNumber(ContentResolver contentResolver, Integer contactId) {
        return getContactInfo(contentResolver, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, contactId, ContactsContract.CommonDataKinds.Phone.NUMBER);
    }

    private static String getContactEmail(ContentResolver contentResolver, Integer contactId) {
        return getContactInfo(contentResolver, ContactsContract.CommonDataKinds.Email.CONTENT_URI, ContactsContract.CommonDataKinds.Email.CONTACT_ID, contactId, ContactsContract.CommonDataKinds.Email.ADDRESS);
    }


    @SuppressLint("Range")
    private static String getContactInfo(ContentResolver contentResolver, Uri uri, String selection, Integer contactId, String columnName) {
        Cursor phoneCursor = contentResolver.query(uri, null, selection + " = ?", new String[]{contactId.toString()}, null);
        String info = "";
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            info = phoneCursor.getString(phoneCursor.getColumnIndex(columnName));
            phoneCursor.close();
        }
        return info;
    }

    @SuppressLint("Range")
    public static String getContactTelegramId(ContentResolver contentResolver, Integer contactId) {
        String telegramId = null;
        Cursor cursor = null;

        try {
            cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + " = ? AND " +
                            ContactsContract.Data.MIMETYPE + " = ?",
                           new String[]{contactId.toString(), "vnd.android.cursor.item/vnd.org.telegram.messenger.android.profile"},
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
    public static String getContactWhatsappId(ContentResolver contentResolver, Integer contactId) {
        String whatsappId = null;
        Cursor cursor = null;

        try {
            cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + " = ? AND " +
                            ContactsContract.Data.MIMETYPE + " = ?",
                    new String[]{contactId.toString(), "vnd.android.cursor.item/vnd.com.whatsapp.profile"},
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

        if (whatsappId!=null && whatsappId.length()>0 && whatsappId.contains("@")) {
            String[] parts = whatsappId.split("@");
            return parts[0];
        }
        return whatsappId;
    }
}

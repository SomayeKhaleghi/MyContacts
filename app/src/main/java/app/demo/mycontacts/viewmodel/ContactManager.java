package app.demo.mycontacts.viewmodel;

import java.util.HashMap;
import java.util.List;
import app.demo.mycontacts.model.Contact;
import app.demo.mycontacts.repository.ContactRepository;

public class ContactManager {
    private ContactRepository contactRepository;
    private HashMap<String, Contact> contactHashMap;

    public ContactManager(ContactRepository contactRepository) {
        contactHashMap = new HashMap<>();
        this.contactRepository = contactRepository;
    }

    public void updateHashMap(List<Contact> deviceContacts) {
        contactHashMap.clear();
        for (Contact contact : deviceContacts) {
            contactHashMap.put(contact.getId(), contact);
        }
    }

    public void compareAndUpdateDatabase(List<Contact> databaseContacts) {
        for (Contact dbContact : databaseContacts) {
            String contactId = dbContact.getId();

            if (contactHashMap.containsKey(contactId)) {
                Contact deviceContact = contactHashMap.get(contactId);
                if (!dbContact.equals(deviceContact)) {
                    updateContactInDatabase(deviceContact);
                }

                contactHashMap.remove(contactId);
            } else {
                deleteContactFromDatabase(dbContact);
            }
        }


    }

    public void addHashMapContactIntoDatabase() {
        for (Contact newContact : contactHashMap.values()) {
            insertContactIntoDatabase(newContact);
        }
    }

    private void insertContactIntoDatabase(Contact contact) {
        contactRepository.insertContact(contact);
    }

    private void updateContactInDatabase(Contact contact) {
        contactRepository.updateContact(contact);
    }

    private void deleteContactFromDatabase(Contact contact) {
        contactRepository.deleteContacts(contact);
    }
}

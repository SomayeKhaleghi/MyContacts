package app.demo.mycontacts.model;

import androidx.annotation.NonNull;
public class ContactDetail {
    @NonNull
    private Contact contact;
    private String phoneNumber = "";
    private String email = "";
    private String whatsApp = "";
    private String telegram = "";

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public Contact getContact() {
        return contact;
    }

    public void setContact(@NonNull Contact contact) {
        this.contact = contact;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ContactDetail(@NonNull Contact contact) {
        this.contact = contact;
    }

}
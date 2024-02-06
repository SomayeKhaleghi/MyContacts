package app.demo.mycontacts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {
@PrimaryKey(autoGenerate = true)
private int id;

@ColumnInfo(name = "name")
private String name;

@ColumnInfo(name = "phone_number")
private String phoneNumber;


public void setId(int id){
    this.id = id;
}
public int getId(){
    return id;
}
public String getName(){

    return name;
}
public void setName(String name){
    this.name = name;
}
public void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
}

public String getPhoneNumber(){
    return phoneNumber;
}
public Contact(String name, String phoneNumber){
    this.name = name;
    this.phoneNumber = phoneNumber;
}
}
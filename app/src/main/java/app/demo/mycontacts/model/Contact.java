package app.demo.mycontacts.model;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
>>>>>>> origin/master
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
@PrimaryKey(autoGenerate = false)
@NonNull
private String id = "";

@ColumnInfo(name = "name")
@NonNull
private String name = "";

@ColumnInfo(name = "phone_number")
@NonNull
private String phoneNumber = "";


@ColumnInfo(name = "exist")
@NonNull
private boolean exist = false;



public void setId(@NonNull String id){
    this.id = id;
}
public String getId(){
    return id;
}
public String getName(){
    return name;
}
public void setName(@NonNull String name){
    this.name = name;
}
public boolean isExist(){
    return exist;
}
public void setExist(boolean exist){
    this.exist = exist;
}
public void setPhoneNumber(@NonNull String phoneNumber){
    this.phoneNumber = phoneNumber;
}

public String getPhoneNumber(){
    return phoneNumber;
}
public Contact(){
    int x = 0;
}
public Contact(@NonNull String id, @NonNull String name, @NonNull String phoneNumber){
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
}

public Contact(@NonNull String id, @NonNull String name, @NonNull String phoneNumber, boolean exist){
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.exist  = exist;
}

}
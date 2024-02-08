package app.demo.mycontacts.model;

import androidx.annotation.NonNull;
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


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public Contact() {
    }

    public Contact(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
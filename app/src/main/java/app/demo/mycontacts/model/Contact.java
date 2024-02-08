package app.demo.mycontacts.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey
    @NonNull
    private Integer id ;

    @ColumnInfo(name = "name")
    @NonNull
    private String name = "";

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Contact(@NonNull Integer id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
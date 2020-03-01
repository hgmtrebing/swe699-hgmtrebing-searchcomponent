package us.hgmtrebing.swe699.database.jdbc;

import lombok.Data;
import java.io.Serializable;

@Data
public class Cuisine implements Serializable {
    private int id;
    private String name;

    public Cuisine (String name) {
        this.name = name;
    }

}

package countryservice.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "country", schema = "country")
public class Country {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    public Country() {
    }

    public Country(String code, String name) {
    }


    public Country(String code, String name, String capital, Integer population) {
        this.code = code;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
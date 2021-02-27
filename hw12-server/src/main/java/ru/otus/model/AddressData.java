package ru.otus.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressData {
    public AddressData(String street){
        this.street = street;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}

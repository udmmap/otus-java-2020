package ru.otus.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true)
    AddressData addressDataSet;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<PhoneData> phoneDataSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public AddressData getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressData addressDataSet) {
        this.addressDataSet = addressDataSet;
    }

    public List<PhoneData> getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(List<PhoneData> phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + addressDataSet.toString() +
                ", phone=" + phoneDataSet.toString() +
                '}';
    }
}

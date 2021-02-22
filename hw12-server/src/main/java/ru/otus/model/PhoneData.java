package ru.otus.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneData {

    public PhoneData(String number, User user){
        this.number = number;
        this.user = user;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}

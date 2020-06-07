package gradation.implementation.datatier.entities;

import gradation.implementation.presentationtier.form.*;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private Short number;
    @Column(length = 60, nullable = false)
    private String street;
    @Column(nullable = false)
    private Integer postalCode;
    @Column(length = 60, nullable = false)
    private String city;
    @Column(length = 80, nullable = false)
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Address(){}

    public Address(ActivityForm activityForm) {
        this.number = activityForm.getNumber();
        this.street = activityForm.getStreet();
        this.postalCode = activityForm.getPostalCode();
        this.city = activityForm.getCity();
        this.country = activityForm.getCountry();
    }

    public void update(ActivityForm activityForm){
        this.number = activityForm.getNumber();
        this.street = activityForm.getStreet();
        this.postalCode = activityForm.getPostalCode();
        this.city = activityForm.getCity();
        this.country = activityForm.getCountry();
    }
}

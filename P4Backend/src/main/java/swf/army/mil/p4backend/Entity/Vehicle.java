package swf.army.mil.p4backend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer year;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Boolean isUsed;
    public Vehicle() {
    }

    public Vehicle(String make, String model, Integer year, Double price, Boolean isUsed) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isUsed = isUsed;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }



}

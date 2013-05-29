package org.nooon.core.model.entity;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "year", length = 100)
    private String year;

    @Column(name = "license_number", length = 20)
    private String licenseNumber;

    @Column(name = "vin", length = 50)
    private String vin; // vehicle identification number

    @Column(name = "engine_number", length = 50)
    private String engineNumber;

    @OneToOne
    @JoinColumn(name = "owner")
    private Person owner;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}

package org.nooon.core.model.entity;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "state")
public class State extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "fuel", length = 10)
    private String fuel;

    @Column(name = "km", length = 10)
    private String km;

    @Column(name = "visual_damage", length = 2000)
    private String visualDamage;

    @Column(name = "technical_validity")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar technicalValidity;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getVisualDamage() {
        return visualDamage;
    }

    public void setVisualDamage(String visualDamage) {
        this.visualDamage = visualDamage;
    }

    public Calendar getTechnicalValidity() {
        return technicalValidity;
    }

    public void setTechnicalValidity(Calendar technicalValidity) {
        this.technicalValidity = technicalValidity;
    }

}

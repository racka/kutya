package org.nooon.core.model.entity;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "worksheet")
public class Worksheet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "name", length = 50)
    private String name;

    @Basic(optional = false)
    @Column(name = "comment", length = 2000)
    private String comment;


    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created = Calendar.getInstance();

    @OneToOne
    @JoinColumn(name = "initial_state")
    private State initialState;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "worksheet", fetch = FetchType.EAGER)
    private List<Work> works;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }
}

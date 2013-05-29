package org.nooon.core.model.entity;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "person")
@XmlRootElement
public class Person extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "family_name", length = 50)
    private String familyName;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public String toString() {
        return firstName + " " + familyName;
    }
}

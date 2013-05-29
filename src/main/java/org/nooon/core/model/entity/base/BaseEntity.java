package org.nooon.core.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable, Comparable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    protected String id;

    @Basic(optional = false)
    @Version
	protected Long version;

    @Column(name = "active")
    protected Boolean active;
	
	public BaseEntity() {
        id = UUID.randomUUID().toString();
        active = true;
	}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return (this.toString() == null ? "" : this.toString()).compareTo(o.toString() == null ? "" : o.toString());
    }
}

package org.nooon.core.model.entity;

import org.nooon.core.model.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "part")
public class Part extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", length = 2000)
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "discount")
    private Long discount;

    @ManyToOne
    @JoinColumn(name = "work")
    private Work work;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}

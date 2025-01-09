package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class ItemSubclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subclassName;

    @ManyToOne
    @JoinColumn(name = "item_class_id")
    private ItemClass itemClass;

    public ItemClass getItemClass() {
        return itemClass;
    }

    public void setItemClass(ItemClass itemClass) {
        this.itemClass = itemClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubclassName() {
        return subclassName;
    }

    public void setSubclassName(String subclassName) {
        this.subclassName = subclassName;
    }
}


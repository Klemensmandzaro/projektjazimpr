package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setName;

    @ElementCollection
    @CollectionTable(name = "item_set_effects", joinColumns = @JoinColumn(name = "item_set_id"))
    @Column(name = "effect")
    private List<String> effects = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public List<String> getEffects() {
        return effects;
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }

    public String wrapEffects() {
        return String.join(", ", effects);
    }
}


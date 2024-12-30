package org.example.model;

import jakarta.persistence.*;



@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long blizzardId;
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private ItemClass itemClass;

    @ManyToOne(cascade = CascadeType.ALL)
    private ItemSubclass itemSubclass;

    @OneToOne(cascade = CascadeType.ALL)
    private ItemStats itemStats;

    @OneToOne(cascade = CascadeType.ALL)
    private ItemMedia itemMedia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_set_id")
    private ItemSet itemSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlizzardId() {
        return blizzardId;
    }

    public void setBlizzardId(Long blizzardId) {
        this.blizzardId = blizzardId;
    }

    public ItemSet getItemSet() {
        return itemSet;
    }

    public void setItemSet(ItemSet itemSet) {
        this.itemSet = itemSet;
    }

    public ItemStats getItemStats() {
        return itemStats;
    }

    public void setItemStats(ItemStats itemStats) {
        this.itemStats = itemStats;
    }

    public ItemMedia getItemMedia() {
        return itemMedia;
    }

    public void setItemMedia(ItemMedia itemMedia) {
        this.itemMedia = itemMedia;
    }

    public ItemSubclass getItemSubclass() {
        return itemSubclass;
    }

    public void setItemSubclass(ItemSubclass itemSubclass) {
        this.itemSubclass = itemSubclass;
    }

    public ItemClass getItemClass() {
        return itemClass;
    }

    public void setItemClass(ItemClass itemClass) {
        this.itemClass = itemClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


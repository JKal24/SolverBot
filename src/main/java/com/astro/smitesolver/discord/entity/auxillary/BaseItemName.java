package com.astro.smitesolver.discord.entity.auxillary;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "base_item_name")
public class BaseItemName {

    @Id
    private int itemID;

    private String itemName;

    private Integer itemTier;

    public BaseItemName(int itemID, String itemName, Integer itemTier) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemTier = itemTier;
    }

    public BaseItemName() { }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemTier() {
        return itemTier;
    }

    public void setItemTier(Integer itemTier) {
        this.itemTier = itemTier;
    }
}

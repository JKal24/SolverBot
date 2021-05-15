package com.astro.smitesolver.discord.entity.auxillary;

import com.astro.smitesolver.smite.entity.items.BaseItemInfo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "base_item_data")
public class BaseItemData {

    @Id
    private int itemID;

    private String itemName;

    private Integer itemTier;

    private String itemDescription;

    private Integer upgradePrice;

    private Integer fullPrice;

    private String upgradeTree;

    private String restrictedRoles;

    private String itemIconURL;

    public BaseItemData(BaseItemInfo info, BaseItemInfo... childInfo) {
        this.itemID = info.getItemID();
        this.itemName = info.getItemName();
        this.itemTier = info.getItemTier();

        this.itemDescription = info.getItemDescription().toString();

        this.upgradePrice = info.getPrice();
        this.fullPrice = upgradePrice;
        StringBuilder upgradeTreeBuilder = new StringBuilder("[");

        for (BaseItemInfo child : childInfo) {
            fullPrice += child.getPrice();
            upgradeTreeBuilder.append(child.getItemName());
        }

        upgradeTreeBuilder.append("]");
        this.upgradeTree = upgradeTreeBuilder.toString();

        this.restrictedRoles = info.getRestrictedRoles();

        this.itemIconURL = info.getItemIconURL();
    }

    public BaseItemData() { }

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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(Integer upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public Integer getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Integer fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getUpgradeTree() {
        return upgradeTree;
    }

    public void setUpgradeTree(String upgradeTree) {
        this.upgradeTree = upgradeTree;
    }

    public String getRestrictedRoles() {
        return restrictedRoles;
    }

    public void setRestrictedRoles(String restrictedRoles) {
        this.restrictedRoles = restrictedRoles;
    }

    public String getItemIconURL() {
        return itemIconURL;
    }

    public void setItemIconURL(String itemIconURL) {
        this.itemIconURL = itemIconURL;
    }
}

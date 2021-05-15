package com.astro.smitesolver.discord.entity.auxillary;

import com.astro.smitesolver.smite.entity.characters.GodInfo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="god_data")
public class GodData {

    @Id
    private Integer godID;

    private String godName;

    private String godData;

    private String ability1_description;

    private String ability2_description;

    private String ability3_description;

    private String ability4_description;

    private String ability5_description;

    private String attributes;

    public GodData(GodInfo info) {
        this.godName = info.getName();
        this.godID = info.getGodID();

        this.godData = info.getName() + "[Pantheon=" + info.getPantheon() + ", Roles=" + info.getRoles()
                + ", Type=" + info.getType() + ", Pros=" + info.getPros() + "]";

        this.ability1_description = info.getAbility1Name() + "[" + info.getAbility1().getAbility() + "]";
        this.ability2_description = info.getAbility2Name() + "[" + info.getAbility2().getAbility() + "]";
        this.ability3_description = info.getAbility3Name() + "[" + info.getAbility3().getAbility() + "]";
        this.ability4_description = info.getAbility4Name() + "[" + info.getAbility4().getAbility() + "]";
        this.ability5_description = info.getAbility5Name() + "[" + info.getAbility5().getAbility() + "]";

        String power = info.getPhysicalPower() == 0 ?
                ", Magical Power=" + info.getMagicalPower() + ", Magical Power Per Level=" + info.getMagicalPowerPerLevel() :
                ", Physical Power=" + info.getMagicalPower() + ", Physical Power Per Level=" + info.getMagicalPowerPerLevel();

        this.attributes = "Attributes=[Attack Speed=" + info.getAttackSpeed() + ", Attack Speed Per Level=" + info.getAttackSpeedPerLevel()
                + ", Health=" + info.getHealth() + ", Health Per Level=" + info.getHealthPerLevel() + ", HP5=" + info.getHealthPerFive()
                + ", HP5 Per Level=" + info.getHp5PerLevel() + ", Mana=" + info.getMana() + ", Mana Per Level=" + info.getManaPerLevel()
                + ", MP5=" + info.getManaPerFive() + ", MP5 Per Level=" + info.getMp5PerLevel() + power + ", Magic Protection=" + info.getMagicProtection()
                + ", Magic Protection Per Level=" + info.getMagicalPowerPerLevel() + ", Physical Protection=" + info.getPhysicalProtection()
                + ", Physical Protection Per Level=" + info.getPhysicalProtectionPerLevel() + "Speed=" + info.getSpeed() + "]";
    }

    public GodData() { }

    public Integer getGodID() {
        return godID;
    }

    public void setGodID(Integer godID) {
        this.godID = godID;
    }

    public String getGodName() {
        return godName;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public String getGodData() {
        return godData;
    }

    public void setGodData(String godData) {
        this.godData = godData;
    }

    public String getAbility1_description() {
        return ability1_description;
    }

    public void setAbility1_description(String ability1_description) {
        this.ability1_description = ability1_description;
    }

    public String getAbility2_description() {
        return ability2_description;
    }

    public void setAbility2_description(String ability2_description) {
        this.ability2_description = ability2_description;
    }

    public String getAbility3_description() {
        return ability3_description;
    }

    public void setAbility3_description(String ability3_description) {
        this.ability3_description = ability3_description;
    }

    public String getAbility4_description() {
        return ability4_description;
    }

    public void setAbility4_description(String ability4_description) {
        this.ability4_description = ability4_description;
    }

    public String getAbility5_description() {
        return ability5_description;
    }

    public void setAbility5_description(String ability5_description) {
        this.ability5_description = ability5_description;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}

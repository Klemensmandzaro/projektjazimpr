package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class ItemStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer Strength;
    private Integer Agility;
    private Integer Intellect;
    private Integer Stamina;
    private Integer CriticalStrike;
    private Integer Mastery;
    private Integer Versatility;
    private Integer Haste;
    private Integer Armor;
    private Integer Block;
    private Integer Dodge;
    private Integer HealthRegeneration;
    private String OtherType;
    private Integer Arcane_resistance;
    private Integer Fire_resistance;
    private Integer Frost_resistance;
    private Integer Nature_resistance;
    private Integer Shadow_resistance;
    private Integer Crit_ranged;
    private Integer Parry;
    private Integer Spell_power;
    private Integer Spirit;
    private Integer Damage_min;
    private Integer Damage_max;
    private Integer Attack_speed;
    private Double DPS;
    private Integer Attack_power;
    private Integer Ranged_attack_power;
    private Integer Crafting_speed;
    private Integer Deftness;
    private Integer Finesse;
    private Integer Ingenuity;
    private Integer Multicraft;
    private Integer Perception;
    private Integer Resourcefulness;
    private Integer Avoidance;
    private Integer Lifesteal;
    private Integer Speed;
    private Integer Sturdiness;
    private Integer Corruption_resistance;
    private Integer Extra_armor;
    private Integer Mana;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStrength() {
        return Strength;
    }

    public void setStrength(Integer strength) {
        Strength = strength;
    }

    public Integer getAgility() {
        return Agility;
    }

    public void setAgility(Integer agility) {
        Agility = agility;
    }

    public Integer getIntellect() {
        return Intellect;
    }

    public void setIntellect(Integer intellect) {
        Intellect = intellect;
    }

    public Integer getStamina() {
        return Stamina;
    }

    public void setStamina(Integer stamina) {
        Stamina = stamina;
    }

    public Integer getCriticalStrike() {
        return CriticalStrike;
    }

    public void setCriticalStrike(Integer criticalStrike) {
        CriticalStrike = criticalStrike;
    }

    public Integer getMastery() {
        return Mastery;
    }

    public void setMastery(Integer mastery) {
        Mastery = mastery;
    }

    public Integer getVersatility() {
        return Versatility;
    }

    public void setVersatility(Integer versatility) {
        Versatility = versatility;
    }

    public Integer getHaste() {
        return Haste;
    }

    public void setHaste(Integer haste) {
        Haste = haste;
    }

    public Integer getArmor() {
        return Armor;
    }

    public void setArmor(Integer armor) {
        Armor = armor;
    }

    public Integer getBlock() {
        return Block;
    }

    public void setBlock(Integer block) {
        Block = block;
    }

    public Integer getDodge() {
        return Dodge;
    }

    public void setDodge(Integer dodge) {
        Dodge = dodge;
    }

    public Integer getHealthRegeneration() {
        return HealthRegeneration;
    }

    public void setHealthRegeneration(Integer healthRegeneration) {
        HealthRegeneration = healthRegeneration;
    }

    public String getOtherType() {
        return OtherType;
    }

    public void setOtherType(String otherType) {
        OtherType = otherType;
    }

    public Integer getArcane_resistance() {
        return Arcane_resistance;
    }

    public void setArcane_resistance(Integer arcane_resistance) {
        Arcane_resistance = arcane_resistance;
    }

    public Integer getFire_resistance() {
        return Fire_resistance;
    }

    public void setFire_resistance(Integer fire_resistance) {
        Fire_resistance = fire_resistance;
    }

    public Integer getFrost_resistance() {
        return Frost_resistance;
    }

    public void setFrost_resistance(Integer frost_resistance) {
        Frost_resistance = frost_resistance;
    }

    public Integer getNature_resistance() {
        return Nature_resistance;
    }

    public void setNature_resistance(Integer nature_resistance) {
        Nature_resistance = nature_resistance;
    }

    public Integer getShadow_resistance() {
        return Shadow_resistance;
    }

    public void setShadow_resistance(Integer shadow_resistance) {
        Shadow_resistance = shadow_resistance;
    }

    public Integer getCrit_ranged() {
        return Crit_ranged;
    }

    public void setCrit_ranged(Integer crit_ranged) {
        Crit_ranged = crit_ranged;
    }

    public Integer getParry() {
        return Parry;
    }

    public void setParry(Integer parry) {
        Parry = parry;
    }

    public Integer getSpell_power() {
        return Spell_power;
    }

    public void setSpell_power(Integer spell_power) {
        Spell_power = spell_power;
    }

    public Integer getSpirit() {
        return Spirit;
    }

    public void setSpirit(Integer spirit) {
        Spirit = spirit;
    }

    public Integer getDamage_min() {
        return Damage_min;
    }

    public void setDamage_min(Integer damage_min) {
        Damage_min = damage_min;
    }

    public Integer getDamage_max() {
        return Damage_max;
    }

    public void setDamage_max(Integer damage_max) {
        Damage_max = damage_max;
    }

    public Integer getAttack_speed() {
        return Attack_speed;
    }

    public void setAttack_speed(Integer attack_speed) {
        Attack_speed = attack_speed;
    }

    public Double getDPS() {
        return DPS;
    }

    public void setDPS(Double DPS) {
        this.DPS = DPS;
    }

    public Integer getAttack_power() {
        return Attack_power;
    }

    public void setAttack_power(Integer attack_power) {
        Attack_power = attack_power;
    }

    public Integer getRanged_attack_power() {
        return Ranged_attack_power;
    }

    public void setRanged_attack_power(Integer ranged_attack_power) {
        Ranged_attack_power = ranged_attack_power;
    }

    public Integer getCrafting_speed() {
        return Crafting_speed;
    }

    public void setCrafting_speed(Integer crafting_speed) {
        Crafting_speed = crafting_speed;
    }

    public Integer getDeftness() {
        return Deftness;
    }

    public void setDeftness(Integer deftness) {
        Deftness = deftness;
    }

    public Integer getFinesse() {
        return Finesse;
    }

    public void setFinesse(Integer finesse) {
        Finesse = finesse;
    }

    public Integer getIngenuity() {
        return Ingenuity;
    }

    public void setIngenuity(Integer ingenuity) {
        Ingenuity = ingenuity;
    }

    public Integer getMulticraft() {
        return Multicraft;
    }

    public void setMulticraft(Integer multicraft) {
        Multicraft = multicraft;
    }

    public Integer getPerception() {
        return Perception;
    }

    public void setPerception(Integer perception) {
        Perception = perception;
    }

    public Integer getResourcefulness() {
        return Resourcefulness;
    }

    public void setResourcefulness(Integer resourcefulness) {
        Resourcefulness = resourcefulness;
    }

    public Integer getAvoidance() {
        return Avoidance;
    }

    public void setAvoidance(Integer avoidance) {
        Avoidance = avoidance;
    }

    public Integer getLifesteal() {
        return Lifesteal;
    }

    public void setLifesteal(Integer life_steal) {
        Lifesteal = life_steal;
    }

    public Integer getSpeed() {
        return Speed;
    }

    public void setSpeed(Integer speed) {
        Speed = speed;
    }

    public Integer getSturdiness() {
        return Sturdiness;
    }

    public void setSturdiness(Integer sturdiness) {
        Sturdiness = sturdiness;
    }

    public Integer getCorruption_resistance() {
        return Corruption_resistance;
    }

    public void setCorruption_resistance(Integer corruption_resistance) {
        Corruption_resistance = corruption_resistance;
    }

    public Integer getExtra_armor() {
        return Extra_armor;
    }

    public void setExtra_armor(Integer extra_armor) {
        Extra_armor = extra_armor;
    }

    public Integer getMana() {
        return Mana;
    }

    public void setMana(Integer mana) {
        Mana = mana;
    }
}


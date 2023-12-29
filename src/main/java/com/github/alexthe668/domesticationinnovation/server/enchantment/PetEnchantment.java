package com.github.alexthe668.domesticationinnovation.server.enchantment;

import com.github.alexthe668.domesticationinnovation.DomesticationMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class PetEnchantment extends Enchantment {

    private int levels;
    private int minXP;
    private String registryName;

    protected PetEnchantment(String name, Rarity r, int levels, int minXP) {
        super(r, DIEnchantmentRegistry.CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        this.levels = levels;
        this.minXP = minXP;
        this.registryName = name;
    }

    public int getMinCost(int i) {
        int minCost;
        if ( DomesticationMod.CONFIG.isEnchantLootOnly(this)){
            minCost = 25;
        } else {
            minCost = minXP + (i - 1) * minXP;
        }
        return minCost;
    }

    public int getMaxCost(int i) {
                int maxCost;
        if ( DomesticationMod.CONFIG.isEnchantLootOnly(this)){
            maxCost = 50;
        } else {
            maxCost = super.getMinCost(i) + 5;
        }
        return maxCost;
    }

    public int getMaxLevel() {
        int maxLevel;
        if ( DomesticationMod.CONFIG.isEnchantLootOnly(this)){
            maxLevel = 1;
        } else {
            maxLevel = levels;
        }
        return maxLevel;
    }
    
    public boolean isTreasureOnly() {
        return DomesticationMod.CONFIG.isEnchantLootOnly(this);
    }

    protected boolean checkCompatibility(Enchantment enchantment) {
        return this != enchantment && DIEnchantmentRegistry.areCompatible(this, enchantment);
    }

    public boolean isTradeable() {
        return super.isTradeable() && DomesticationMod.CONFIG.isEnchantEnabled(this);
    }

    public boolean isDiscoverable() {
        return super.isDiscoverable() && DomesticationMod.CONFIG.isEnchantEnabled(this);
    }

    public boolean isAllowedOnBooks() {
        return super.isAllowedOnBooks() && DomesticationMod.CONFIG.isEnchantEnabled(this);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return super.canApplyAtEnchantingTable(stack) && DomesticationMod.CONFIG.isEnchantEnabled(this);
    }

    public String getName(){
        return registryName;
    }
}

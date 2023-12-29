package com.github.alexthe668.domesticationinnovation;

import com.github.alexthe668.domesticationinnovation.server.enchantment.DIEnchantmentRegistry;
import com.github.alexthe668.domesticationinnovation.server.enchantment.PetEnchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ForgeConfigSpec;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DIConfig {

    public final ForgeConfigSpec.BooleanValue trinaryCommandSystem;
    public final ForgeConfigSpec.BooleanValue tameableAxolotl;
    public final ForgeConfigSpec.BooleanValue tameableHorse;
    public final ForgeConfigSpec.BooleanValue tameableFox;
    public final ForgeConfigSpec.BooleanValue tameableRabbit;
    public final ForgeConfigSpec.BooleanValue tameableFrog;
    public final ForgeConfigSpec.BooleanValue swingThroughPets;
    public final ForgeConfigSpec.BooleanValue rottenApple;
    public final ForgeConfigSpec.BooleanValue petBedRespawns;
    public final ForgeConfigSpec.BooleanValue collarTag;
    public final ForgeConfigSpec.BooleanValue rabbitsScareRavagers;
    public final ForgeConfigSpec.BooleanValue animalTamerVillager;
    public final ForgeConfigSpec.IntValue petstoreVillageWeight;

    public final ForgeConfigSpec.BooleanValue petCurseEnchantmentsLootOnly;
    public final ForgeConfigSpec.DoubleValue sinisterCarrotLootChance;
    public final ForgeConfigSpec.DoubleValue bubblingLootChance;
    public final ForgeConfigSpec.DoubleValue vampirismLootChance;
    public final ForgeConfigSpec.DoubleValue voidCloudLootChance;
    public final ForgeConfigSpec.DoubleValue oreScentingLootChance;
    public final ForgeConfigSpec.DoubleValue muffledLootChance;
    public final ForgeConfigSpec.DoubleValue blazingProtectionLootChance;

    private final Map<String, ForgeConfigSpec.BooleanValue> enabledEnchantments = new HashMap<>();
    private final Map<String, ForgeConfigSpec.BooleanValue> lootOnlyEnchantments = new HashMap<>();


    public DIConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("general");
        trinaryCommandSystem = builder.comment("true if wolves, cats, parrots, foxes, axolotls, etc can be set to wander, sit or follow").translation("trinary_command_system").define("trinary_command_system", true);
        tameableAxolotl = builder.comment("true if axolotls are fully tameable (axolotl must be tamed with tropical fish)").translation("tameable_axolotls").define("tameable_axolotls", true);
        tameableHorse = builder.comment("true if horses, donkeys, llamas, etc can be given enchants, beds, etc").translation("tameable_horse").define("tameable_horse", true);
        tameableFox = builder.comment("true if foxes are fully tameable (fox must be tamed via breeding)").translation("tameable_fox").define("tameable_fox", true);
        tameableRabbit = builder.comment("true if rabbits are fully tameable (rabbit must be tamed with carrots)").translation("tameable_rabbit").define("tameable_rabbit", true);
        tameableFrog = builder.comment("true if frogs are fully tameable (rabbit must be tamed with spider eyes)").translation("tameable_rabbit").define("tameable_frog", true);
        swingThroughPets = builder.comment("true if attacks do not register on pets from their owners and go through them to attack a mob behind them").translation("swing_through_pets").define("swing_through_pets", true);
        rottenApple = builder.comment("true if apples can turn into rotten apples if they despawn").translation("rotten_apple").define("rotten_apple", true);
        petBedRespawns = builder.comment("true if mobs can respawn in pet beds the next morning after they die").translation("pet_bed_respawns").define("pet_bed_respawns", true);
        collarTag = builder.comment("true if collar tag functionality are enabled. If this is disabled, there is no way to enchant mobs!").translation("collar_tags").define("collar_tags", true);
        rabbitsScareRavagers = builder.comment("true if rabbits scare ravagers like they used to do").translation("rabbits_scare_ravagers").define("rabbits_scare_ravagers", true);
        animalTamerVillager = builder.comment("true if animal tamer villagers are enabled. Their work station is a pet bed").translation("animal_tamer_villager").define("animal_tamer_villager", true);
        petstoreVillageWeight = builder.comment("the spawn weight of the pet store in villages, set to 0 to disable it entirely").translation("petstore_village_weight").defineInRange("petstore_village_weight", 17, 0, 1000);
        builder.pop();
        builder.push("loot");
        petCurseEnchantmentsLootOnly = builder.comment("true if pet curse enchantments should only appear in loot, and not the enchanting table.").translation("pet_curse_enchantments_loot_only").define("pet_curse_enchantments_loot_only", true);
        sinisterCarrotLootChance = builder.comment("percent chance of woodland mansion loot table containing sinister carrot:").translation("sinister_carrot_loot_chance").defineInRange("sinister_carrot_loot_chance", 0.3D, 0.0, 1.0D);
        bubblingLootChance = builder.comment("percent chance of burried treasure loot table containing Bubbling book:").translation("bubbling_loot_chance").defineInRange("bubbling_loot_chance", 0.65D, 0.0, 1.0D);
        vampirismLootChance = builder.comment("percent chance of woodland mansion loot table containing Vampire book:").translation("vampirism_loot_chance").defineInRange("vampirism_loot_chance", 0.22D, 0.0, 1.0D);
        voidCloudLootChance = builder.comment("percent chance of end city loot table containing Void Cloud book:").translation("void_cloud_loot_chance").defineInRange("void_cloud_loot_chance", 0.19D, 0.0, 1.0D);
        oreScentingLootChance = builder.comment("percent chance of mineshaft loot table containing Ore Scenting book:").translation("ore_scenting_loot_chance").defineInRange("ore_scenting_loot_chance", 0.15D, 0.0, 1.0D);
        muffledLootChance = builder.comment("percent chance of ancient city loot table containing Muffled book:").translation("muffled_loot_chance").defineInRange("muffled_loot_chance", 0.19D, 0.0, 1.0D);
        blazingProtectionLootChance = builder.comment("percent chance of nether fortress loot table containing Blazing Protection book:").translation("ore_scenting_loot_chance").defineInRange("blazing_protection_loot_chance", 0.2D, 0.0, 1.0D);
        builder.pop();
        builder.push("enchantments");
        try {
            for (Field f : DIEnchantmentRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof PetEnchantment) {
                    String registryName = ((PetEnchantment) obj).getName();
                    String name = registryName + "_enabled";
                    enabledEnchantments.put(registryName, builder.comment("true if " + registryName.replace("_", " ") + " enchant is enabled, false if disabled").translation(name).define(name, true));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        builder.pop();
        builder.push("loot_only_enchantments");
        try {
            for (Field f : DIEnchantmentRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof PetEnchantment) {
                    String registryName = ((PetEnchantment) obj).getName();
                    String name = registryName + "_lootOnly";
                    lootOnlyEnchantments.put(registryName, builder.comment("true if " + registryName.replace("_", " ") + " enchant is loot only, false if available at enchantment table").translation(name).define(name, true));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        builder.pop();
    }

    public boolean isEnchantEnabled(Enchantment enchantment){
        return enchantment instanceof PetEnchantment && isEnchantEnabled(((PetEnchantment)enchantment).getName());
    }

    public boolean isEnchantEnabled(String enchantment){
        ForgeConfigSpec.BooleanValue entry = enabledEnchantments.get(enchantment);
        return entry == null || entry.get();
    }

    public boolean isEnchantLootOnly(Enchantment enchantment){
        return enchantment instanceof PetEnchantment && isEnchantLootOnly(((PetEnchantment)enchantment).getName());
    }

    public boolean isEnchantLootOnly(String enchantment){
        ForgeConfigSpec.BooleanValue entry = lootOnlyEnchantments.get(enchantment);
        return entry == null || entry.get();
    }
}

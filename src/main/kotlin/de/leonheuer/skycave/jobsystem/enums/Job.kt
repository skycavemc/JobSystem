package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Job(val friendlyName: String, val icon: Material, val description: String) {

    FORESTER("Förster", Material.GOLDEN_AXE, ""),
    FARMER("Landwirt", Material.WHEAT, ""),
    MINER("Minenarbeiter", Material.DIAMOND_PICKAXE, ""),
    //ALCHEMIST("Alchemist", Material.BREWING_STAND, ""),
    NETHER_WORKER("Höllenarbeiter", Material.BLAZE_POWDER, ""),
    COOK("Koch", Material.COOKED_BEEF, ""),
    HUNTER("Jäger", Material.BOW, ""),
    ;

    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(item: ItemStack): Job? {
            return values().firstOrNull { item.itemMeta.displayName.endsWith(it.friendlyName) && item.type == it.icon }
        }
    }

}
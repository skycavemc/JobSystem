package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Job(
    val friendlyName: String,
    val icon: Material,
    val sellContent: String
    ) {

    FORESTER("Förster", Material.GOLDEN_AXE, "Stämme, Setzlinge, Laub"),
    FARMER("Landwirt", Material.WHEAT, "Wachsende Items, zbsp. Weizen"),
    MINER("Minenarbeiter", Material.DIAMOND_PICKAXE, "Erze und Bruchstein mit besseren Preisen"),
    //ALCHEMIST("Alchemist", Material.BREWING_STAND, ""),
    NETHER_WORKER("Höllenarbeiter", Material.BLAZE_POWDER, "Netheritems und -blöcke"),
    COOK("Koch", Material.COOKED_BEEF, "Essbares, zbsp. gebratenes Fleisch"),
    HUNTER("Jäger", Material.BOW, "Mobdrops, zbsp. Knochen"),
    ;

    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(item: ItemStack): Job? {
            return values().firstOrNull { item.itemMeta.displayName.endsWith(it.friendlyName) && item.type == it.icon }
        }
    }

}
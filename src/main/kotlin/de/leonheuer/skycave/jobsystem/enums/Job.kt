package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Job(val friendlyName: String, val icon: Material) {

    FORESTER("Förster", Material.OAK_LOG),
    MINER("Minenarbeiter", Material.DIAMOND_PICKAXE),
    FARMER("Landwirt", Material.WHEAT),
    BOTANIST("Botaniker", Material.POPPY),
    DIVER("Taucher", Material.TROPICAL_FISH),
    NETHER_WORKER("Höllenarbeiter", Material.NETHER_BRICKS),
    ;

    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(item: ItemStack): Job? {
            return values().firstOrNull { item.itemMeta.displayName.endsWith(it.friendlyName) && item.type == it.icon }
        }
    }

}
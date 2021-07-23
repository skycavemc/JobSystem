package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class GlobalItem (
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Double,
) {

    IRON_INGOT("Eisen", Material.IRON_INGOT, 1, 1.0),
    GOLD_INGOT("Gold", Material.GOLD_INGOT, 1, 5.0),
    REDSTONE("Redstone", Material.REDSTONE, 1, 6.0),

    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack): GlobalItem? {
            return values().firstOrNull { itemStack.itemMeta.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material }
        }
    }

}
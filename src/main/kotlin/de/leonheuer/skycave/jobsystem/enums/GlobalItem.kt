package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class GlobalItem (
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Int,
    val fixedAmount: Boolean,
) {

    IRON_INGOT("Eisenerz", Material.IRON_INGOT, 1, 1, false),

    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack): GlobalItem? {
            return values().firstOrNull { itemStack.itemMeta.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material }
        }
    }

}
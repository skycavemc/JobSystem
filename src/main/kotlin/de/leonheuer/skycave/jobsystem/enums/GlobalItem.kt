package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material

enum class GlobalItem (
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Int,
    val fixedAmount: Boolean,
) {

    IRON_INGOT("Eisenerz", Material.IRON_INGOT, 1, 1, false),

}
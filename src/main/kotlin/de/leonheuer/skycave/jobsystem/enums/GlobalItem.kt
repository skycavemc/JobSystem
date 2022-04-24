package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material

enum class GlobalItem (
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Double,
) {

    COAL("Kohle", Material.COAL, 64, 8.0),
    IRON_INGOT("Eisen", Material.IRON_INGOT, 1, 1.0),
    GOLD_INGOT("Gold", Material.GOLD_INGOT, 1, 5.0),
    LAPIS_LAZULI("Lapis", Material.LAPIS_LAZULI, 64, 160.0),
    REDSTONE("Redstone", Material.REDSTONE, 1, 4.0),
    DIAMOND("Diamant", Material.DIAMOND, 1, 50.0),
    COBBLESTONE("Bruchstein", Material.COBBLESTONE, 2304, 100.0),
    ;

}
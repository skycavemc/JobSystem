package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material

enum class Job(val friendlyName: String, val icon: Material) {

    FORESTER("Förster", Material.OAK_LOG),
    MINER("Minenarbeiter", Material.DIAMOND_PICKAXE),
    FARMER("Landwirt", Material.WHEAT),
    BOTANIST("Botaniker", Material.POPPY),
    DIVER("Taucher", Material.TROPICAL_FISH),
    NETHER_WORKER("Höllenarbeiter", Material.NETHER_BRICKS),

}
package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class JobSpecificItem(
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Double,
    val job: Job
    ) {

    // forester items
    OAK_SAPLING("Eichensetzling", Material.OAK_SAPLING, 1, 1.0, Job.FORESTER),
    DARK_OAK_SAPLING("Schwarzeichensetzling", Material.DARK_OAK_SAPLING, 1, 1.0, Job.FORESTER),
    SPRUCE_SAPLING("Fichtensetzling", Material.SPRUCE_SAPLING, 1, 1.0, Job.FORESTER),
    BIRCH_SAPLING("Birkensetzling", Material.BIRCH_SAPLING, 1, 1.0, Job.FORESTER),
    ACACIA_SAPLING("Akaziensetzling", Material.ACACIA_SAPLING, 1, 1.0, Job.FORESTER),
    JUNGLE_SAPLING("Tropischer Setzling", Material.JUNGLE_SAPLING, 1, 1.0, Job.FORESTER),
    OAK_LOG("Eichenstamm", Material.OAK_LOG, 1, 1.0, Job.FORESTER),
    DARK_OAK_LOG("Scharzeichenstamm", Material.DARK_OAK_LOG, 1, 1.0, Job.FORESTER),
    SPRUCE_LOG("Fichtenstamm", Material.SPRUCE_LOG, 1, 1.0, Job.FORESTER),
    BIRCH_LOG("Birkenstamm", Material.BIRCH_LOG, 1, 1.0, Job.FORESTER),
    ACACIA_LOG("Akazienstamm", Material.ACACIA_LOG, 1, 1.0, Job.FORESTER),
    JUNGLE_LOG("Dschungelholz Stamm", Material.JUNGLE_LOG, 1, 1.0, Job.FORESTER),
    BROWN_MUSHROOM("Brauner Pilz", Material.BROWN_MUSHROOM, 1, 1.0, Job.FORESTER),
    RED_MUSHROOM("Roter Pilz", Material.RED_MUSHROOM, 1, 1.0, Job.FORESTER),
    CRIMSON_FUNGUS("Karmesinpilz", Material.CRIMSON_FUNGUS, 1, 1.0, Job.FORESTER),
    WARPED_FUNGUS("Wirrpilz", Material.WARPED_FUNGUS, 1, 1.0, Job.FORESTER),
    CRIMSON_STEM("Karmesinstiel", Material.CRIMSON_STEM, 1, 1.0, Job.FORESTER),
    WARPED_STEM("Wirrstiel", Material.WARPED_STEM, 1, 1.0, Job.FORESTER),

    // miner items
    IRON_ORE("Eisenerz", Material.IRON_ORE, 1, 2.2, Job.MINER),
    GOLD_ORE("Golderz", Material.GOLD_ORE, 1, 7.0, Job.MINER),
    DIAMOND("Diamant", Material.DIAMOND, 1, 55.0, Job.MINER),
    LAPIS_LAZULI("Lapis", Material.LAPIS_LAZULI, 1, 3.0, Job.MINER),
    COAL("Kohle", Material.COAL, 64, 8.0, Job.MINER),
    COBBLESTONE("Bruchstein", Material.COBBLESTONE, 2304, 200.0, Job.MINER),

    // farmer items
    CARROT("Karotte", Material.CARROT, 1, 1.0, Job.FARMER),

    // botanist items
    POPPY("Botaniker", Material.POPPY, 1, 1.0, Job.BOTANIST),

    // diver items
    PRISMARINE_SHARD("Prismarinscherbe", Material.PRISMARINE_SHARD, 1, 1.0, Job.DIVER),

    // nether worker items

    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack, job: Job): JobSpecificItem? {
            return values().firstOrNull { itemStack.itemMeta.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material && job == it.job }
        }
    }

}
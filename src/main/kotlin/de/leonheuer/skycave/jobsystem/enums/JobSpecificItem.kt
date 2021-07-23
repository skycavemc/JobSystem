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
    OAK_LOG("Eichenstamm", Material.OAK_LOG, 1, 1.0, Job.FORESTER),

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
    CRIMSON_FUNGUS("Karmesinpilz", Material.CRIMSON_FUNGUS, 1, 1.0, Job.NETHER_WORKER),

    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack, job: Job): JobSpecificItem? {
            return values().firstOrNull { itemStack.itemMeta.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material && job == it.job }
        }
    }

}
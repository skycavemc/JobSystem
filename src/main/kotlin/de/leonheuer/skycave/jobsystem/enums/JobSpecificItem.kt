package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class JobSpecificItem(
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Int,
    val job: Job
    ) {

    // forester items
    OAK_LOG("Eichenstamm", Material.OAK_LOG, 1, 1, Job.FORESTER),

    // miner items
    DIAMOND("Diamant", Material.DIAMOND, 1, 1, Job.MINER),

    // farmer items
    CARROT("Karotte", Material.CARROT, 1, 1, Job.FARMER),

    // botanist items
    POPPY("Botaniker", Material.POPPY, 1, 1, Job.BOTANIST),

    // diver items
    PRISMARINE_SHARD("Prismarinscherbe", Material.PRISMARINE_SHARD, 1, 1, Job.DIVER),

    // nether worker items
    CRIMSON_FUNGUS("Karmesinpilz", Material.CRIMSON_FUNGUS, 1, 1, Job.NETHER_WORKER),

    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack, job: Job): JobSpecificItem? {
            return values().firstOrNull { itemStack.itemMeta.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material && job == it.job }
        }
    }

}
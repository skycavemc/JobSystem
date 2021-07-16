package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.Material

enum class JobSpecificItem(
    val friendlyName: String,
    val material: Material,
    val amount: Int,
    val price: Int,
    val fixedAmount: Boolean,
    val job: Job?
    ) {

    // forester items
    OAK_LOG("Eichenstamm", Material.OAK_LOG, 1, 1, false, Job.FORESTER),

    // miner items
    DIAMOND("Diamant", Material.DIAMOND, 1, 1, false, Job.MINER),

    // farmer items
    CARROT("Karotte", Material.CARROT, 1, 1, false, Job.FARMER),

    // botanist items
    POPPY("Botaniker", Material.POPPY, 1, 1, false, Job.BOTANIST),

    // diver items
    PRISMARINE_SHARD("Prismarinscherbe", Material.PRISMARINE_SHARD, 1, 1, false, Job.DIVER),

    // nether worker items
    CRIMSON_FUNGUS("Karmesinpilz", Material.CRIMSON_FUNGUS, 1, 1, false, Job.NETHER_WORKER),


}
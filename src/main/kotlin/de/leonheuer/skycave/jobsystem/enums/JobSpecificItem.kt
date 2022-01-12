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
    OAK_SAPLING("Eichensetzling", Material.OAK_SAPLING, 1, 5.0, Job.FORESTER),
    DARK_OAK_SAPLING("Schwarzeichensetzling", Material.DARK_OAK_SAPLING, 1, 5.0, Job.FORESTER),
    SPRUCE_SAPLING("Fichtensetzling", Material.SPRUCE_SAPLING, 1, 5.0, Job.FORESTER),
    BIRCH_SAPLING("Birkensetzling", Material.BIRCH_SAPLING, 1, 5.0, Job.FORESTER),
    ACACIA_SAPLING("Akaziensetzling", Material.ACACIA_SAPLING, 1, 5.0, Job.FORESTER),
    JUNGLE_SAPLING("Tropenbaumsetzling", Material.JUNGLE_SAPLING, 1, 5.0, Job.FORESTER),
    OAK_LOG("Eichenstamm", Material.OAK_LOG, 1, 6.0, Job.FORESTER),
    DARK_OAK_LOG("Scharzeichenstamm", Material.DARK_OAK_LOG, 1, 6.0, Job.FORESTER),
    SPRUCE_LOG("Fichtenstamm", Material.SPRUCE_LOG, 1, 4.0, Job.FORESTER),
    BIRCH_LOG("Birkenstamm", Material.BIRCH_LOG, 1, 6.0, Job.FORESTER),
    ACACIA_LOG("Akazienstamm", Material.ACACIA_LOG, 1, 7.0, Job.FORESTER),
    JUNGLE_LOG("Tropenbaumstamm", Material.JUNGLE_LOG, 1, 5.0, Job.FORESTER),
    OAK_LEAVES("Eichenlaub", Material.OAK_LEAVES, 64, 4.0, Job.FORESTER),
    DARK_OAK_LEAVES("Schwarzeichenlaub", Material.DARK_OAK_LEAVES, 64, 4.0, Job.FORESTER),
    SPRUCE_LEAVES("Fichtennadeln", Material.SPRUCE_LEAVES, 64, 4.0, Job.FORESTER),
    BIRCH_LEAVES("Birkenlaub", Material.BIRCH_LEAVES, 64, 4.0, Job.FORESTER),
    ACACIA_LEAVES("Akazienlaub", Material.ACACIA_LEAVES, 64, 4.0, Job.FORESTER),
    JUNGLE_LEAVES("Tropenbaumlaub", Material.JUNGLE_LEAVES, 64, 4.0, Job.FORESTER),
    STICK("Stock", Material.STICK, 64, 5.0, Job.FORESTER),

    // farmer items
    WHEAT("Weizen", Material.WHEAT, 64, 64.0, Job.FARMER),
    BEETROOT("Rote Beete", Material.BEETROOT, 64, 40.0, Job.FARMER),
    POTATO("Kartoffel", Material.POTATO, 64, 40.0, Job.FARMER),
    POISONOUS_POTATO("Giftige Kartoffel", Material.POISONOUS_POTATO, 1, 2.0, Job.FARMER),
    CARROT("Karotte", Material.CARROT, 64, 40.0, Job.FARMER),
    SUGAR_CANE("Zuckerrohr", Material.SUGAR_CANE, 64, 10.0, Job.FARMER),
    CACTUS("Kaktus", Material.CACTUS, 64, 3.0, Job.FARMER),
    PUMPKIN("Kürbis", Material.PUMPKIN, 64, 5.0, Job.FARMER),
    MELON("Melone", Material.MELON, 64, 7.0, Job.FARMER),
    APPLE("Apfel", Material.APPLE, 64, 3.0, Job.FARMER),
    SWEET_BERRIES("Süßbeeren", Material.SWEET_BERRIES, 64, 32.0, Job.FARMER),
    BAMBOO("Bambus", Material.BAMBOO, 64, 6.0, Job.FARMER),
    CHORUS_FRUIT("Chorusfrucht", Material.CHORUS_FRUIT, 64, 32.0, Job.FARMER),

    // miner items
    COAL("Kohle", Material.COAL, 64, 8.0, Job.MINER),
    IRON_ORE("Eisenerz", Material.IRON_ORE, 1, 2.2, Job.MINER),
    GOLD_ORE("Golderz", Material.GOLD_ORE, 1, 7.0, Job.MINER),
    LAPIS_LAZULI("Lapis", Material.LAPIS_LAZULI, 64, 200.0, Job.MINER),
    REDSTONE("Redstone", Material.REDSTONE, 1, 6.0, Job.MINER),
    DIAMOND("Diamant", Material.DIAMOND, 1, 55.0, Job.MINER),
    COBBLESTONE("Bruchstein", Material.COBBLESTONE, 2304, 200.0, Job.MINER),

    // alchemist items


    // nether worker items
    BLAZE_ROD("Lohenrute", Material.BLAZE_ROD, 1, 2.0, Job.NETHER_WORKER),
    GHAST_TEAR("Ghastträne", Material.GHAST_TEAR, 1, 150.0, Job.NETHER_WORKER),
    NETHER_BRICKS("Netherziegel", Material.NETHER_BRICKS, 64, 10.0, Job.NETHER_WORKER),
    RED_NETHER_BRICKS("Rote Netherziegel", Material.RED_NETHER_BRICKS, 64, 10.0, Job.NETHER_WORKER),
    QUARTZ("Quarz", Material.QUARTZ, 1, 4.0, Job.NETHER_WORKER),
    CRIMSON_STEM("Karmesinstiel", Material.CRIMSON_STEM, 1, 6.0, Job.NETHER_WORKER),
    WARPED_STEM("Wirrstiel", Material.WARPED_STEM, 1, 6.0, Job.NETHER_WORKER),
    CRIMSON_FUNGUS("Karmesinpilz", Material.CRIMSON_FUNGUS, 1, 5.0, Job.NETHER_WORKER),
    WARPED_FUNGUS("Wirrpilz", Material.WARPED_FUNGUS, 1, 5.0, Job.NETHER_WORKER),

    // cook items
    COOKED_RABBIT("Gebratenes Kaninchen", Material.COOKED_RABBIT, 1, 7.0, Job.COOK),
    COOKED_MUTTON("Gebratenes Hammelfleisch", Material.COOKED_MUTTON, 1, 7.0, Job.COOK),
    COOKED_BEEF("Steak", Material.COOKED_BEEF, 1, 7.0, Job.COOK),
    COOKED_PORKCHOP("Gebratenes Schweinefleisch", Material.COOKED_PORKCHOP, 1, 7.0, Job.COOK),
    COOKED_CHICKEN("Gebratenes Hühnchen", Material.COOKED_CHICKEN, 1, 5.0, Job.COOK),
    BAKED_POTATO("Ofenkartoffel", Material.BAKED_POTATO, 1, 3.0, Job.COOK),
    CAKE("Kuchen", Material.CAKE, 1, 15.0, Job.COOK),
    PUMPKIN_PIE("Kürbiskuchen", Material.PUMPKIN_PIE, 64, 10.0, Job.COOK),
    MUSHROOM_STEW("Pilzsuppe", Material.MUSHROOM_STEW, 1, 3.0, Job.COOK),
    RABBIT_STEW("Kaninchenragout", Material.RABBIT_STEW, 1, 3.0, Job.COOK),
    BEETWOOT_SOUP("Rote Beete Suppe", Material.BEETROOT_SOUP, 1, 3.0, Job.COOK),
    TROPICAL_FISH("Tropenfisch", Material.TROPICAL_FISH, 1, 4.0, Job.COOK),
    COOKED_SALMON("Gebratener Lachs", Material.COOKED_SALMON, 1, 4.0, Job.COOK),
    COOKED_COD("Gebratener Kabeljau", Material.COOKED_COD, 1, 4.0, Job.COOK),

    // hunter items
    BONE("Knochen", Material.BONE, 64, 8.0, Job.HUNTER),
    ARROW("Pfeil", Material.ARROW, 64, 8.0, Job.HUNTER),
    GUNPOWDER("Schwarzpulver", Material.GUNPOWDER, 64, 8.0, Job.HUNTER),
    STRING("Faden", Material.STRING, 64, 8.0, Job.HUNTER),
    SLIME("Schleimball", Material.SLIME_BALL, 64, 16.0, Job.HUNTER),
    ROTTEN_FLESH("Verrottetes Fleisch", Material.ROTTEN_FLESH, 64, 8.0, Job.HUNTER),
    SPIDER_EYE("Spinnenauge", Material.SPIDER_EYE, 1, 2.0, Job.HUNTER),
    CREEPER_HEAD("Creeperkopf", Material.CREEPER_HEAD, 1, 150.0, Job.HUNTER),
    ZOMBIE_HEAD("Zombiekopf", Material.ZOMBIE_HEAD, 1, 150.0, Job.HUNTER),
    SKELETON_SKULL("Skelettschädel", Material.SKELETON_SKULL, 1, 150.0, Job.HUNTER),
    RABBIT_HIDE("Kaninchenfell", Material.RABBIT_HIDE, 1, 8.0, Job.HUNTER),
    RABBIT_FOOT("Hasenpfote", Material.RABBIT_FOOT, 1, 8.0, Job.HUNTER),
    LEATHER("Leder", Material.LEATHER, 1, 8.0, Job.HUNTER),
    FEATHER("Feder", Material.FEATHER, 1, 1.0, Job.HUNTER),
    INK_SAC("Tintensack", Material.INK_SAC, 64, 16.0, Job.HUNTER),
    ;


    companion object {
        @Suppress("Deprecation")
        fun fromItemStack(itemStack: ItemStack, job: Job): JobSpecificItem? {
            return values().firstOrNull { itemStack.itemMeta!!.displayName.endsWith(it.friendlyName) &&
                    itemStack.type == it.material && job == it.job }
        }
    }

}
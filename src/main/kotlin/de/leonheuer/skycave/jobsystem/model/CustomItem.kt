package de.leonheuer.skycave.jobsystem.model

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CustomItem(material: Material, amount: Int) {

    val itemStack = ItemStack(material, amount)

    @Suppress("Deprecation")
    fun setName(name: String): CustomItem {
        val meta = itemStack.itemMeta
        meta.setDisplayName(name)
        itemStack.itemMeta = meta
        return this
    }

    @Suppress("Deprecation")
    fun setLore(vararg lore: String): CustomItem {
        val meta = itemStack.itemMeta
        val lines = ArrayList<String>()
        lore.forEach { lines.add(it) }
        meta.lore = lines
        itemStack.itemMeta = meta
        return this
    }

    fun addFlag(flag: ItemFlag): CustomItem {
        itemStack.addItemFlags(flag)
        return this
    }

    fun addFlags(vararg flags: ItemFlag): CustomItem {
        itemStack.addItemFlags(*flags)
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): CustomItem {
        val meta = itemStack.itemMeta
        meta.isUnbreakable = unbreakable
        itemStack.itemMeta = meta
        return this
    }

    fun addEnchant(enchant: Enchantment, level: Int): CustomItem {
        val meta = itemStack.itemMeta
        meta.addEnchant(enchant, level, true)
        itemStack.itemMeta = meta
        return this
    }

}
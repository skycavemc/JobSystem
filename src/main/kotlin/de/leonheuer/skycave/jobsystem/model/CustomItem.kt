package de.leonheuer.skycave.jobsystem.model

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CustomItem(material: Material, amount: Int) {

    val itemStack = ItemStack(material, amount)

    @Suppress("Deprecation")
    fun setName(name: String): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.setDisplayName(name)
        itemStack.itemMeta = meta
        return this
    }

    @Suppress("Deprecation")
    fun setLore(vararg lore: String): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        val lines = ArrayList<String>()
        lore.forEach { lines.add(it) }
        meta.lore = lines
        itemStack.itemMeta = meta
        return this
    }

    @Suppress("Deprecation")
    fun setLore(lore: List<String>): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.lore = lore
        itemStack.itemMeta = meta
        return this
    }

    fun addFlag(flag: ItemFlag): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.addItemFlags(flag)
        itemStack.itemMeta = meta
        return this
    }

    fun addFlags(vararg flags: ItemFlag): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.addItemFlags(*flags)
        itemStack.itemMeta = meta
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.isUnbreakable = unbreakable
        itemStack.itemMeta = meta
        return this
    }

    fun addEnchant(enchant: Enchantment, level: Int): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.addEnchant(enchant, level, true)
        itemStack.itemMeta = meta
        return this
    }

}
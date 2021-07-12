package de.leonheuer.skycave.jobsystem.model

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CustomItem(material: Material, amount: Int) {

    val itemStack = ItemStack(material, amount)

    fun setName(name: String): CustomItem {
        val meta = itemStack.itemMeta
        meta.displayName(Component.text(name))
        itemStack.itemMeta = meta
        return this
    }

    fun setName(name: Component): CustomItem {
        val meta = itemStack.itemMeta
        meta.displayName(name)
        itemStack.itemMeta = meta
        return this
    }

    fun setLore(vararg lore: String): CustomItem {
        val meta = itemStack.itemMeta
        val lines = ArrayList<Component>()
        lore.forEach { lines.add(Component.text(it)) }
        meta.lore(lines)
        itemStack.itemMeta = meta
        return this
    }

    fun setLore(vararg lore: Component): CustomItem {
        val meta = itemStack.itemMeta
        val lines = ArrayList<Component>()
        lines.addAll(lore)
        meta.lore(lines)
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
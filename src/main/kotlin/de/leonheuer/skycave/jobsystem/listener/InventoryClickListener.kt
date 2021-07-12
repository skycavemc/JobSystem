package de.leonheuer.skycave.jobsystem.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickListener: Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player) {
            return
        }
    }

}
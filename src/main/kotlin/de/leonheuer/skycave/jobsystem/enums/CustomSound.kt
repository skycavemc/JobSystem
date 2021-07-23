package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.Sound
import org.bukkit.entity.Player

enum class CustomSound(private val sound: Sound, private val pitch: Float) {

    ERROR(Sound.ENTITY_ITEM_BREAK, 0.7f),
    SUCCESS(Sound.ENTITY_PLAYER_LEVELUP, 1.2f),
    CLICK(Sound.UI_BUTTON_CLICK, 1.0f),
    VILLAGER_OPEN(Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f),
    ;

    fun playTo(player: Player) {
        player.playSound(player.location, sound, JobSystem.MASTER_VOLUME, pitch)
    }

}
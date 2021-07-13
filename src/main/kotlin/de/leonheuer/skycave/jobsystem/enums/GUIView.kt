package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.ChatColor

enum class GUIView(private val title: String) {

    JOBS("&8» &1Job Auswahl"),
    SELL("&8» &1Item Ankauf"),
    SELL_PERSONAL("&8» &1Persönlicher Item Ankauf"),
    ;

    fun getTitle(): String {
        return ChatColor.translateAlternateColorCodes('&', title)
    }

}
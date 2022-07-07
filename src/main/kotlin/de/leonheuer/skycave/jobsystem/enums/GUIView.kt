package de.leonheuer.skycave.jobsystem.enums

import org.bukkit.ChatColor

enum class GUIView(private val title: String) {

    MAIN("&8» &1Hauptmenü"),
    JOBS("&8» &1Job Auswahl"),
    SELL("&8» &1Item Ankauf"),
    SELL_PERSONAL("&8» &1Persönlicher Item Ankauf"),
    DAILY("&8» &1Tägliche Quests"),
    WEEKLY("&8» &1Wöchentliche Quests"),
    MASS("&8» &1Massenquests"),
    CONFIRM("&8» &1Bestätigung")
    ;

    fun getTitle(): String {
        return ChatColor.translateAlternateColorCodes('&', title)
    }

}
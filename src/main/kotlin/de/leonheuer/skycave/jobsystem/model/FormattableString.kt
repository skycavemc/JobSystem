package de.leonheuer.skycave.jobsystem.model

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.ChatColor

class FormattableString(val base: String) {

    private var result = base

    fun addPrefix(): FormattableString {
        result = JobSystem.PREFIX + result
        return this
    }

    fun replace(old: String, new: String): FormattableString {
        result.replaceFirst(old, new)
        return this
    }

    fun replaceAll(old: String, new: String): FormattableString {
        result.replace(old, new)
        return this
    }

    fun get(formatted: Boolean = true): String {
        return if (formatted) {
            ChatColor.translateAlternateColorCodes('&', result)
        } else {
            result
        }
    }

}
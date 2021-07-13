package de.leonheuer.skycave.jobsystem.model

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.ChatColor

class FormattableString(val base: String) {

    private var result = base

    fun replace(old: String, new: String): FormattableString {
        result = result.replaceFirst(old, new)
        return this
    }

    fun replaceAll(old: String, new: String): FormattableString {
        result = result.replace(old, new)
        return this
    }

    fun get(prefix: Boolean = true, formatted: Boolean = true): String {
        if (prefix) {
            result = JobSystem.PREFIX + result
        }
        return if (formatted) {
            ChatColor.translateAlternateColorCodes('&', result)
        } else {
            result
        }
    }

}
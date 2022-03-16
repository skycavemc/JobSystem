package de.leonheuer.skycave.jobsystem.model

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

/**
 * Class that acts like a Bukkit YamlConfiguration and additionally remembers the source it is loaded from.
 * Automatically saves any changes made to it.
 */
class AutoSaveConfig(var source: File) : YamlConfiguration() {

    init {
        load(source)
    }

    /**
     * Loads from the given source file and remembers the source.
     * @param file The source file
     */
    override fun load(file: File) {
        try {
            super.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
        source = file
    }

    /**
     * Stores the value at the given path of the YAML configuration. Automatically saves the changes.
     * @param path Path to the object
     * @param value Object to store
     */
    override fun set(path: String, value: Any?) {
        super.set(path, value)
        save()
    }

    /**
     * Saves the YamlConfiguration to the source that has been set.
     */
    fun save() {
        try {
            save(source)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
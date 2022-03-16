package de.leonheuer.skycave.jobsystem.util

import com.google.common.io.Files
import com.google.common.io.Resources
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

object FileUtils {

    /**
     * Copies the resource of the Bukkit plugin into the data folder of the Bukkit plugin.
     * @param main The main class of the Bukkit plugin
     * @param resourceName The file name of the resource
     * @return Whether the operation succeeded. Will also return true if the file already exists in the data folder.
     */
    @Suppress("UnstableApiUsage")
    fun copyResource(main: JavaPlugin, resourceName: String): Boolean {
        val destination = File(main.dataFolder, resourceName)
        val resource = main.javaClass.classLoader.getResource(resourceName)
        if (resource == null) {
            main.logger.severe("The resource $resourceName does not exist.")
            return false
        }
        if (destination.exists()) {
            main.logger.info("The file $resourceName already exists.")
            return true
        }
        try {
            destination.createNewFile()
            Resources.asByteSource(resource).copyTo(Files.asByteSink(destination))
            main.logger.info("The file $resourceName has been created.")
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

}
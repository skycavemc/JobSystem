package de.leonheuer.skycave.jobsystem

import de.leonheuer.skycave.jobsystem.command.JobCommand
import de.leonheuer.skycave.jobsystem.listener.PlayerInteractListener
import de.leonheuer.skycave.jobsystem.listener.PlayerJoinListener
import de.leonheuer.skycave.jobsystem.manager.DataManager
import de.leonheuer.skycave.jobsystem.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class JobSystem: JavaPlugin() {

    companion object {
        const val PREFIX = "&b&l| &bJob&3System &8» "
    }

    lateinit var dataManager: DataManager
        private set
    lateinit var playerManager: PlayerManager
        private set

    override fun onEnable() {
        dataManager = DataManager(this)
        playerManager = PlayerManager()

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerInteractListener(this), this)
        pm.registerEvents(PlayerJoinListener(this), this)

        getCommand("job")!!.setExecutor(JobCommand(this))
    }

    override fun onDisable() {
        dataManager.saveUsers()
        dataManager.saveNPC()
    }

}
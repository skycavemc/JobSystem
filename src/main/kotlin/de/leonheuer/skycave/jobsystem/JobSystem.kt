package de.leonheuer.skycave.jobsystem

import de.leonheuer.mcguiapi.gui.GUIFactory
import de.leonheuer.skycave.jobsystem.command.GSellCommand
import de.leonheuer.skycave.jobsystem.command.JobCommand
import de.leonheuer.skycave.jobsystem.command.SellCommand
import de.leonheuer.skycave.jobsystem.listener.PlayerInteractListener
import de.leonheuer.skycave.jobsystem.listener.PlayerJoinLeaveListener
import de.leonheuer.skycave.jobsystem.manager.DataManager
import de.leonheuer.skycave.jobsystem.manager.PlayerManager
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class JobSystem: JavaPlugin() {

    companion object {
        const val PREFIX = "&b&l| &bJob&3System &8» "
        const val MASTER_VOLUME = 1.0f
    }

    lateinit var dataManager: DataManager
        private set
    lateinit var playerManager: PlayerManager
        private set
    lateinit var economy: Economy
        private set
    lateinit var guiFactory: GUIFactory
        private set

    override fun onEnable() {
        dataManager = DataManager(this)
        Bukkit.getOnlinePlayers().forEach(dataManager::registerUser)
        playerManager = PlayerManager()
        economy = server.servicesManager.getRegistration(Economy::class.java)!!.provider
        guiFactory = GUIFactory(this)

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerInteractListener(this), this)
        pm.registerEvents(PlayerJoinLeaveListener(this), this)

        getCommand("job")!!.setExecutor(JobCommand(this))
        getCommand("sell")!!.setExecutor(SellCommand())
        getCommand("gsell")!!.setExecutor(GSellCommand())
    }

    override fun onDisable() {
        dataManager.unregisterAllUsers()
        dataManager.saveNPC()
    }

}
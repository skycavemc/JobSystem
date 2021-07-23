package de.leonheuer.skycave.jobsystem

import de.leonheuer.skycave.jobsystem.command.GSellCommand
import de.leonheuer.skycave.jobsystem.command.JobCommand
import de.leonheuer.skycave.jobsystem.command.SellCommand
import de.leonheuer.skycave.jobsystem.listener.InventoryClickListener
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
    }

    lateinit var dataManager: DataManager
        private set
    lateinit var playerManager: PlayerManager
        private set
    lateinit var economy: Economy
        private set

    override fun onEnable() {
        dataManager = DataManager(this)
        playerManager = PlayerManager()
        economy = server.servicesManager.getRegistration(Economy::class.java)!!.provider

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerInteractListener(this), this)
        pm.registerEvents(PlayerJoinLeaveListener(this), this)
        pm.registerEvents(InventoryClickListener(this), this)

        getCommand("job")!!.setExecutor(JobCommand(this))
        getCommand("sell")!!.setExecutor(SellCommand())
        getCommand("gsell")!!.setExecutor(GSellCommand())
    }

    override fun onDisable() {
        dataManager.unregisterAllUsers()
        dataManager.saveNPC()
    }

}
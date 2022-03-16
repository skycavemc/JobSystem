package de.leonheuer.skycave.jobsystem

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import de.leonheuer.mcguiapi.gui.GUIFactory
import de.leonheuer.skycave.jobsystem.codecs.JobCodec
import de.leonheuer.skycave.jobsystem.codecs.LocalDateTimeCodec
import de.leonheuer.skycave.jobsystem.codecs.UUIDCodec
import de.leonheuer.skycave.jobsystem.codecs.UserCodecProvider
import de.leonheuer.skycave.jobsystem.commands.GSellCommand
import de.leonheuer.skycave.jobsystem.commands.JobCommand
import de.leonheuer.skycave.jobsystem.commands.SellCommand
import de.leonheuer.skycave.jobsystem.listeners.PlayerInteractListener
import de.leonheuer.skycave.jobsystem.model.AutoSaveConfig
import de.leonheuer.skycave.jobsystem.model.User
import de.leonheuer.skycave.jobsystem.util.FileUtils
import net.milkbowl.vault.economy.Economy
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class JobSystem: JavaPlugin() {

    companion object {
        const val PREFIX = "&b&l| &bJob&3System &8» "
        const val MASTER_VOLUME = 1.0f
    }

    val npcSetMode = ArrayList<UUID>()
    val calculateAmount = HashMap<UUID, Int>()
    val sellAmount = HashMap<UUID, Int>()
    lateinit var config: AutoSaveConfig
        private set
    lateinit var economy: Economy
        private set
    lateinit var guiFactory: GUIFactory
        private set
    lateinit var users: MongoCollection<User>
        private set
    private lateinit var mongoClient: MongoClient

    override fun onEnable() {
        // resources
        if (!reloadResources()) {
            server.pluginManager.disablePlugin(this)
            logger.severe("Unable to load all plugin resources.")
            return
        }

        // register instances
        economy = server.servicesManager.getRegistration(Economy::class.java)!!.provider
        guiFactory = GUIFactory(this)

        // database
        val codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
            CodecRegistries.fromCodecs(JobCodec(), LocalDateTimeCodec(), UUIDCodec()),
            CodecRegistries.fromProviders(UserCodecProvider()),
            MongoClientSettings.getDefaultCodecRegistry()
        )
        val clientSettings = MongoClientSettings.builder().codecRegistry(codecRegistry).build()
        mongoClient = MongoClients.create(clientSettings)
        val db = mongoClient.getDatabase("job_system")
        users = db.getCollection("users", User::class.java)

        // listeners
        val pm = Bukkit.getPluginManager()
        pm.registerEvents(PlayerInteractListener(this), this)

        // commands
        registerCommand("job", JobCommand(this))
        registerCommand("sell", SellCommand())
        registerCommand("gsell", GSellCommand())
    }

    override fun onDisable() {
        mongoClient.close()
    }

    private fun registerCommand(command: String, executor: CommandExecutor) {
        val cmd = getCommand(command)
        if (cmd == null) {
            logger.severe("Couldn't find an entry for command $command in plugin.yml")
            return
        }
        cmd.setExecutor(executor)
    }

    /**
     * Reloads all configurations of the plugin.
     * Copies resources of the plugin in the data folder if they are missing.
     * @return Whether reloading succeeded.
     */
    fun reloadResources(): Boolean {
        if (!dataFolder.isDirectory && !dataFolder.mkdirs()) {
            return false
        }
        config = if (FileUtils.copyResource(this, "config.yml")) {
            AutoSaveConfig(File(dataFolder, "config.yml"))
        } else {
            return false
        }
        return true
    }

    fun getJobChangeFee(): Double {
        return config.getDouble("job_change_fee")
    }

}
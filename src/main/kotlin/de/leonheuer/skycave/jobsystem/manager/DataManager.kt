package de.leonheuer.skycave.jobsystem.manager

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.model.NPC
import de.leonheuer.skycave.jobsystem.model.User
import de.leonheuer.skycave.jobsystem.util.DataUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.util.*

class DataManager(main: JobSystem) {

    private val userList = ArrayList<User>()
    private val path = File(main.dataFolder.path + "/")
    private val userPath = File(main.dataFolder.path + "/players/")
    var npc: NPC? = null

    init {
        if (!userPath.exists()) {
            userPath.mkdirs()
        }
        loadNPC()
    }

    private fun loadNPC() {
        if (!path.exists()) {
            return
        }
        val file = File(path, "npc.json")
        if (!file.exists()) {
            return
        }
        try {
            val reader = FileReader(file)
            val obj = JSONParser().parse(reader) as JSONObject
            val locationObj = obj["location"] as JSONObject
            val location = Location(
                Bukkit.getWorld(UUID.fromString(locationObj["world"] as String)),
                locationObj["x"] as Double,
                locationObj["y"] as Double,
                locationObj["z"] as Double
            )
            npc = NPC(location, EntityType.valueOf(obj["entityType"] as String))
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun registerUser(player: Player) {
        val user = DataUtil.getUser(player.uniqueId)
        if (user == null) {
            userList.add(DataUtil.createAndGetUser(player))
            return
        }
        userList.add(user)
    }

    fun isRegistered(uuid: UUID): Boolean {
        return userList.any { it.uuid == uuid }
    }

    fun getRegisteredUser(player: Player): User {
        return userList.first { it.uuid == player.uniqueId }
    }

    fun unregisterUser(uuid: UUID) {
        val user = userList.firstOrNull { it.uuid == uuid }
        if (user != null) {
            DataUtil.saveUser(user)
        }
        userList.remove(user)
    }

    fun unregisterAllUsers() {
        if (userList.isEmpty()) {
            return
        }
        if (!userPath.exists()) {
            userPath.mkdirs()
        }
        userList.forEach {
            unregisterUser(it.uuid)
        }
    }

    fun saveNPC() {
        if (npc == null) {
            return
        }
        try {
            val file = File(path, "npc.json")
            if (!file.exists()) {
                file.createNewFile()
            }

            val obj = JSONObject()
            val location = JSONObject()
            location["world"] = npc!!.location.world.uid.toString()
            location["x"] = npc!!.location.x
            location["y"] = npc!!.location.y
            location["z"] = npc!!.location.z
            obj["location"] = location
            obj["entityType"] = npc!!.entityType.toString()

            val writer = FileWriter(file)
            writer.write(obj.toJSONString())
            writer.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
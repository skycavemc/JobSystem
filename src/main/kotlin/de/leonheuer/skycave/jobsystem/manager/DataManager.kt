package de.leonheuer.skycave.jobsystem.manager

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.model.NPC
import de.leonheuer.skycave.jobsystem.model.User
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
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
    private val path = File(main.dataFolder.path)
    private val userPath = File(main.dataFolder.path + "/players/")
    var npc: NPC? = null

    init {
        loadUsers()
        loadNPC()
    }

    private fun loadUsers() {
        if (!userPath.exists()) {
            userPath.mkdirs()
        }
        if (userPath.listFiles() == null || userPath.listFiles()!!.isEmpty()) {
            return
        }
        val parser = JSONParser()
        userPath.listFiles()!!.filter { it.isFile }.forEach {
            try {
                val reader = FileReader(it)
                val user = parser.parse(reader) as JSONObject
                userList.add(User(
                    UUID.fromString(user["uuid"] as String),
                    Job.valueOf(user["job"] as String),
                    LocalDateTime.parse(user["jobChangeDate"] as String),
                    (user["freeJobChanges"] as Long).toInt()
                ))
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

    fun getUser(uuid: UUID): User? {
        return userList.firstOrNull { it.uuid == uuid }
    }

    fun createUser(uuid: UUID, job: Job) {
        val user = User(uuid, job, LocalDateTime.now(),Job.values().size + 1)
        userList.add(user)
        saveUser(user)
    }

    fun saveUsers() {
        if (userList.isEmpty()) {
            return
        }
        if (!userPath.exists()) {
            userPath.mkdirs()
        }
        userList.forEach {
            saveUser(it)
        }
    }

    fun saveUser(user: User) {
        try {
            val file = File(userPath, "${user.uuid}.json")
            if (!file.exists()) {
                file.createNewFile()
            }

            val obj = JSONObject()
            obj["uuid"] = user.uuid.toString()
            obj["job"] = user.job.toString()
            obj["jobChangeDate"] = user.jobChangeDate.toString()
            obj["freeJobChanges"] = user.freeJobChanges

            val writer = FileWriter(file)
            writer.write(obj.toJSONString())
            writer.flush()
        } catch (e: IOException) {
            e.printStackTrace()
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
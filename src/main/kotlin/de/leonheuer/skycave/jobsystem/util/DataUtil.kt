package de.leonheuer.skycave.jobsystem.util

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.model.User
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.util.*

object DataUtil {

    private val main = JavaPlugin.getPlugin(JobSystem::class.java)
    private val userPath = File(main.dataFolder.path + "/players/")
    private val parser = JSONParser()

    fun getUser(uuid: UUID): User? {
        val file = File(userPath, "$uuid.json")
        if (!file.exists()) {
            return null
        }

        try {
            val reader = FileReader(file)
            val user = parser.parse(reader) as JSONObject

            val rawJob = user["job"] as String
            val job = if (rawJob == "null") {
                null
            } else {
                Job.valueOf(rawJob)
            }

            val rawDate = user["jobChangeDate"] as String
            val date = if (rawDate == "null") {
                null
            } else {
                LocalDateTime.parse(rawDate)
            }

            return User(UUID.fromString(user["uuid"] as String), job, date, (user["freeJobChanges"] as Long).toInt())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun createAndGetUser(player: Player): User {
        val user = User(player.uniqueId, null, null, Job.values().size)
        saveUser(user)
        return user
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

}
package de.leonheuer.skycave.jobsystem.util

import com.mongodb.client.MongoCollection
import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.model.OldUser
import de.leonheuer.skycave.jobsystem.model.User
import de.leonheuer.skycave.jobsystem.model.UserLevel
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.time.LocalDateTime
import java.util.*

object LegacyAdapter {

    private val main = JavaPlugin.getPlugin(JobSystem::class.java)
    private val userPath = File("${main.dataFolder.path}/old_data/players/")
    private val parser = JSONParser()

    fun importUsers(users: MongoCollection<User>) {
        if (!userPath.isDirectory) {
            return
        }
        val files = userPath.listFiles() ?: return
        var success = 0
        var skipped = 0
        for (file in files) {
            val uuid = UUID.fromString(file.name.replace(".json", ""))
            val user = getUser(uuid) ?: continue
            if (user.job == null && user.jobChangeDate == null) {
                skipped++
                continue
            }
            val newUser = User(uuid, user.job, user.jobChangeDate, user.freeJobChanges, UserLevel(0.0))
            users.insertOne(newUser)
            success++
        }
        main.logger.info("Imported $success of ${files.size} users. ($skipped skipped)")
    }

    private fun getUser(uuid: UUID): OldUser? {
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

            return OldUser(UUID.fromString(user["uuid"] as String), job, date, (user["freeJobChanges"] as Long).toInt())
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

}
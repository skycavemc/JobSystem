package de.leonheuer.skycave.jobsystem.model

import de.leonheuer.skycave.jobsystem.enums.Job
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.util.UUID

class User() {

    @BsonId
    lateinit var id: ObjectId
    lateinit var uuid: UUID
    var job: Job? = null
    @BsonProperty(value = "last_job_change")
    var lastJobChange: LocalDateTime? = null
    @BsonProperty(value = "free_job_changes")
    var freeJobChanges: Int = 0
    var level: UserLevel = UserLevel(0.0)

    constructor(uuid: UUID, job: Job?, lastJobChange: LocalDateTime?, freeJobChanges: Int, experience: UserLevel) : this() {
        this.uuid = uuid
        this.job = job
        this.lastJobChange = lastJobChange
        this.freeJobChanges = freeJobChanges
        this.level = experience
    }

}
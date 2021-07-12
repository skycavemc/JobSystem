package de.leonheuer.skycave.jobsystem.model

import de.leonheuer.skycave.jobsystem.enums.Job
import java.time.LocalDateTime
import java.util.*

data class User(
    val uuid: UUID,
    var job: Job,
    var jobChangeDate: LocalDateTime,
    var freeJobChanges: Int,
    )

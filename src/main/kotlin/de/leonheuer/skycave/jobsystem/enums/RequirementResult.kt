package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.JobSystem
import org.bukkit.plugin.java.JavaPlugin

enum class RequirementResult(val title: String) {

    FIRST("Erster Job"),
    USE_FREE("Kostenloser Jobwechsel"),
    PAY("Zahle ${JavaPlugin.getPlugin(JobSystem::class.java).getJobChangeFee()}$"),
    NO_MONEY(""),

}
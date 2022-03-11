package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.JobSystem

enum class RequirementResult(val title: String) {

    FIRST("Erster Job"),
    USE_FREE("Kostenloser Jobwechsel"),
    PAY("Zahle " + JobSystem.JOB_CHANGE_FEE + "$"),
    NO_MONEY(""),

}
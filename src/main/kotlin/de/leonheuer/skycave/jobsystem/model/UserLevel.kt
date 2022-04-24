package de.leonheuer.skycave.jobsystem.model

class UserLevel(experience: Double) {

    var experience: Double
        private set

    init {
        if (experience < 0) {
            throw IllegalArgumentException("Experience must be a positive value")
        }
        this.experience = experience
    }

    fun getLevel(): Long {
        var level = 1L
        var required = requiredFor(level)
        while (experience >= required) {
            level++
            required = requiredFor(level)
        }
        return level
    }

    fun requiredFor(level: Long): Long {
        return (level - 1) * 1000 + 1000
    }

    fun getProgression(): Double {
        return experience - requiredFor(getLevel())
    }

    fun addExperience(experience: Double) {
        this.experience = this.experience + experience
    }

}
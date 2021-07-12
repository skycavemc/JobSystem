package de.leonheuer.skycave.jobsystem.model

import org.bukkit.Location
import org.bukkit.entity.EntityType

data class NPC(
    val location: Location,
    val entityType: EntityType
)

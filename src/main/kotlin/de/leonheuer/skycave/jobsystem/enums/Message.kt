package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.model.FormattableString

enum class Message(private val message: String) {

    UNKNOWN_COMMAND("&cUnbekannter Befehl. Siehe /job help"),

    // job admin help
    JOB_ADMIN_HELP_SET_NPC("&e/job setnpc &8» &7Setzt den NPC für den Ankauf"),
    JOB_ADMIN_HELP_CANCEL("&e/job cancel &8» &7Bricht das Setzen des NPCs ab"),
    JOB_ADMIN_HELP_HELP("&e/job help &8» &7Zeigt Hilfe an"),

    // job admin commands
    JOB_ADMIN_SET_NPC_BEGIN("&7Klicke nun den gewünschten NPC an. &eNutze /job cancel zum abbrechen."),
    JOB_ADMIN_SET_NPC_BEGIN_ALREADY("&cSetzen des NPCs abgebrochen."),
    JOB_ADMIN_SET_NPC_SUCCESS("&7Ankauf NPC erfolgreich auf &a%type &7bei &ax: %x, y: %y, z: %z &7gesetzt."),
    JOB_ADMIN_CANCEL("&cSetzen des NPCs abgebrochen."),
    JOB_ADMIN_CANCEL_NOT("&cDu bist nicht dabei, einen NPC zu setzen."),

    // npc right click
    JOB_UNSET("&cBitte wähle zuerst einen Job (/job), um Zugriff auf den Ankauf zu bekommen."),

    // job selector click
    JOB_CHANGE_SUCCESS("&aDu hast deinen Beruf gewechselt! Neuer Beruf: &2%job"),
    JOB_CHANGE_WAIT("&cDu musst 48 Stunden zwischen jedem Berufswechsel warten."),

    ;

    fun getString(): FormattableString {
        return FormattableString(message)
    }

}
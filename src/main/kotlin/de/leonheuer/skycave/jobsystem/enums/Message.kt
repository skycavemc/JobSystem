package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.model.FormattableString

enum class Message(private val message: String) {

    UNKNOWN_COMMAND("&cUnbekannter Befehl. Siehe /job help"),
    INTERNAL_ERROR("&cBei der Jobauswahl ist ein Fehler aufgetreten."),

    // job admin help
    JOB_ADMIN_HELP_SET_NPC("&e/job setnpc &8» &7Setzt den NPC für das GUI"),
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
    JOB_CHANGE_USE_FREE("&eDu hast deinen Job kostenlos gewechselt. Es verbleiben %amount kostenlose Wechsel."),
    JOB_CHANGE_PAY("&eDu hast deinen Job für 100.000$ gewechselt."),
    JOB_CHANGE_NO_MONEY("&cDu hast nicht genug Geld um deinen Beruf zu wechseln."),
    JOB_CHANGE_ABORT("&cKostenpflichtiger Wechsel des Jobs abgebrochen."),

    // sell item messages
    SELL_NOT_ENOUGH("&cDu hast nicht genug %name."),
    SELL_SOLD("&7Du hast &a%amountx %name &7für &a%reward$ &7verkauft."),
    SELL_JOB_REQUIRED("&cDu brauchst einen Job, um dieses Item zu verkaufen."),
    ;

    fun getString(): FormattableString {
        return FormattableString(message)
    }

}
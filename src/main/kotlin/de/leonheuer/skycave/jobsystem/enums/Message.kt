package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.model.ColoredStringBuilder

enum class Message(private val message: String) {

    UNKNOWN_COMMAND("&cUnbekannter Befehl. Siehe /job help"),

    // job admin help
    JOB_ADMIN_HELP_SET_NPC("&e/job setnpc &8» &7Setzt den NPC für das GUI"),
    JOB_ADMIN_HELP_CANCEL("&e/job cancel &8» &7Bricht das Setzen des NPCs ab"),
    JOB_ADMIN_HELP_IMPORT("&e/job import &8» &7Importiert alte Daten"),
    JOB_ADMIN_HELP_HELP("&e/job help &8» &7Zeigt Hilfe an"),

    // job admin commands
    JOB_ADMIN_SET_NPC_BEGIN("&7Klicke nun den gewünschten NPC an. &eNutze /job cancel zum abbrechen."),
    JOB_ADMIN_SET_NPC_BEGIN_ALREADY("&cSetzen des NPCs abgebrochen."),
    JOB_ADMIN_SET_NPC_SUCCESS("&7Ankauf NPC erfolgreich auf &a%type &7bei &ax: %x, y: %y, z: %z &7gesetzt."),
    JOB_ADMIN_CANCEL("&cSetzen des NPCs abgebrochen."),
    JOB_ADMIN_CANCEL_NOT("&cDu bist nicht dabei, einen NPC zu setzen."),
    JOB_ADMIN_IMPORT("&aImport durchgeführt, siehe Konsole für Ergebnisse."),

    // npc right click
    JOB_UNSET("&cBitte wähle zuerst einen Job (/job), um Zugriff auf den Ankauf zu bekommen."),

    // job selector click
    JOB_CHANGE_SUCCESS("&aDu hast deinen Beruf gewechselt! Neuer Beruf: &2%job"),
    JOB_CHANGE_WAIT("&cDu musst 48 Stunden zwischen jedem Berufswechsel warten."),
    JOB_CHANGE_USE_FREE("&eDu hast deinen Job kostenlos gewechselt. Es verbleiben %amount kostenlose Wechsel."),
    JOB_CHANGE_PAY("&eDu hast deinen Job für %cost$ gewechselt."),
    JOB_CHANGE_NO_MONEY("&cDu hast nicht genug Geld um deinen Beruf zu wechseln."),
    JOB_CHANGE_ABORT_FREE("&cKostenloser Wechsel des Jobs abgebrochen."),
    JOB_CHANGE_ABORT("&cKostenpflichtiger Wechsel des Jobs abgebrochen."),
    JOB_CHANGE_ALREADY("&cDu übst bereits den Job %job aus."),

    // sell item messages
    SELL_NOT_ENOUGH("&cDu hast nicht genug %name."),
    SELL_SOLD("&7Du hast &a%amountx %name &7für &a%reward$ &7verkauft."),
    ;

    fun getString(): ColoredStringBuilder {
        return ColoredStringBuilder(message)
    }

}
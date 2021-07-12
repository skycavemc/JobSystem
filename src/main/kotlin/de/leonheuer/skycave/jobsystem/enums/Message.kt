package de.leonheuer.skycave.jobsystem.enums

import de.leonheuer.skycave.jobsystem.model.FormattableString

enum class Message(val string: FormattableString) {

    UNKNOWN_COMMAND(FormattableString("&cUnbekannter Befehl. Siehe /job help")),

    // job admin help
    JOB_ADMIN_HELP_SET_NPC(FormattableString("&e/job setnpc &8» &7Setzt den NPC für den Ankauf")),
    JOB_ADMIN_HELP_CANCEL(FormattableString("&e/job cancel &8» &7Bricht das Setzen des NPCs ab")),
    JOB_ADMIN_HELP_HELP(FormattableString("&e/job help &8» &7Zeigt Hilfe an")),

    // job admin commands
    JOB_ADMIN_SET_NPC_BEGIN(FormattableString("&7Klicke nun den gewünschten NPC an. &eNutze /job cancel zum abbrechen.")),
    JOB_ADMIN_SET_NPC_SUCCESS(FormattableString("&7Ankauf NPC erfolgreich auf &a%type &7bei &ax: %x, y: %y, z: %z &7gesetzt.")),
    JOB_ADMIN_CANCEL(FormattableString("&cSetzen des NPCs abgebrochen.")),

    // npc right click
    JOB_UNSET(FormattableString("&cBitte wähle zuerst einen Job (/job), um Zugriff auf den Ankauf zu bekommen.")),

}
package de.leonheuer.skycave.jobsystem.command

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class JobCommand(private val main: JobSystem): CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cThis command is for players only.")
            return true
        }
        if (args.isNotEmpty() && sender.hasPermission("skybee.jobsystem.admin")) {
            when (args[0].lowercase()) {
                "setnpc" -> {
                    main.playerManager.npcSetMode.add(sender.uniqueId)
                    sender.sendMessage(Message.JOB_ADMIN_SET_NPC_BEGIN.string.addPrefix().get())
                }
                "cancel" -> {
                    main.playerManager.npcSetMode.remove(sender.uniqueId)
                    sender.sendMessage(Message.JOB_ADMIN_CANCEL.string.addPrefix().get())
                }
                "help" -> {
                    sender.sendMessage(Message.JOB_ADMIN_HELP_SET_NPC.string.addPrefix().get())
                    sender.sendMessage(Message.JOB_ADMIN_HELP_CANCEL.string.addPrefix().get())
                    sender.sendMessage(Message.JOB_ADMIN_HELP_HELP.string.addPrefix().get())
                }
                else -> {
                    sender.sendMessage(Message.UNKNOWN_COMMAND.string.addPrefix().get())
                }
            }
            return true
        }

        Util.openSelector(sender)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>
    ): MutableList<String>? {
        if (!sender.hasPermission("skybee.jobsystem.admin")) {
            return null
        }

        val arguments = ArrayList<String>()
        val completions = ArrayList<String>()

        if (args.size == 1) {
            arguments.add("setnpc")
            arguments.add("cancel")
            arguments.add("help")
            StringUtil.copyPartialMatches(args[0], arguments, completions)
        }

        completions.sort()
        return completions
    }
}
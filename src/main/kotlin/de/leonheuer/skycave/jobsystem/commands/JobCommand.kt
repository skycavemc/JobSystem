package de.leonheuer.skycave.jobsystem.commands

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.GUIView
import de.leonheuer.skycave.jobsystem.enums.Message
import de.leonheuer.skycave.jobsystem.util.LegacyAdapter
import de.leonheuer.skycave.jobsystem.util.Utils
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
                    val list = main.npcSetMode
                    if (list.contains(sender.uniqueId)) {
                        list.remove(sender.uniqueId)
                        sender.sendMessage(Message.JOB_ADMIN_SET_NPC_BEGIN_ALREADY.getString().get())
                    } else {
                        list.add(sender.uniqueId)
                        sender.sendMessage(Message.JOB_ADMIN_SET_NPC_BEGIN.getString().get())
                    }
                }
                "cancel" -> {
                    val list = main.npcSetMode
                    if (list.contains(sender.uniqueId)) {
                        main.npcSetMode.remove(sender.uniqueId)
                        sender.sendMessage(Message.JOB_ADMIN_CANCEL.getString().get())
                    } else {
                        sender.sendMessage(Message.JOB_ADMIN_CANCEL_NOT.getString().get())
                    }
                }
                "import" -> {
                    LegacyAdapter.importUsers(main.users)
                    sender.sendMessage(Message.JOB_ADMIN_IMPORT.getString().get())
                }
                "help" -> {
                    sender.sendMessage(Message.JOB_ADMIN_HELP_SET_NPC.getString().get(prefix = false))
                    sender.sendMessage(Message.JOB_ADMIN_HELP_CANCEL.getString().get(prefix = false))
                    sender.sendMessage(Message.JOB_ADMIN_HELP_IMPORT.getString().get(prefix = false))
                    sender.sendMessage(Message.JOB_ADMIN_HELP_HELP.getString().get(prefix = false))
                }
                else -> {
                    sender.sendMessage(Message.UNKNOWN_COMMAND.getString().get())
                }
            }
            return true
        }

        Utils.openGUI(sender, GUIView.JOBS)
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
            arguments.add("import")
            arguments.add("help")
            StringUtil.copyPartialMatches(args[0], arguments, completions)
        }

        completions.sort()
        return completions
    }
}
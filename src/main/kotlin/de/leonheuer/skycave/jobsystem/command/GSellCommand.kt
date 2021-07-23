package de.leonheuer.skycave.jobsystem.command

import de.leonheuer.skycave.jobsystem.enums.GUIView
import de.leonheuer.skycave.jobsystem.util.Util
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GSellCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cThis command is for players only.")
            return true
        }
        Util.openGUI(sender, GUIView.SELL)
        return true
    }

}
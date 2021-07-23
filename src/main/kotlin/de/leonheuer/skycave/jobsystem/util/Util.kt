package de.leonheuer.skycave.jobsystem.util

import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.*
import de.leonheuer.skycave.jobsystem.model.CustomItem
import de.leonheuer.skycave.jobsystem.model.User
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.time.format.DateTimeFormatter
import java.util.*

object Util {

    private val main = JavaPlugin.getPlugin(JobSystem::class.java)

    @Suppress("Deprecation")
    fun openGUI(player: Player, view: GUIView) {
        val inv = Bukkit.createInventory(player, 54, view.getTitle())
        placeholdersByPattern(inv, 0, "bnnnbnnnb")
        placeholdersByPattern(inv, 5, "bbbbbbbbb")

        val user = main.dataManager.getRegisteredUser(player)
        val uuid = player.uniqueId

        val date = user.jobChangeDate
        if (date == null) {
            inv.setItem(1, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§cnoch nie")
                .itemStack)
        } else {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yy hh:mm")
            inv.setItem(1, CustomItem(Material.CLOCK, 1)
                .setName("§6Letzter Berufswechsel:")
                .setLore("§b${formatter.format(date)}",
                    "", "§7Du kannst alle 48 Stunden", "§7deinen Beruf wechseln")
                .itemStack)
        }

        val job = user.job
        if (job == null) {
            inv.setItem(2, CustomItem(Material.BARRIER, 1)
                .setName("§6Dein Beruf:")
                .setLore("§cDu hast keinen Beruf")
                .itemStack)
        } else {
            inv.setItem(2, CustomItem(job.icon, 1)
                .setName("§6Dein Beruf:")
                .setLore("§b${job.friendlyName}", "", "§7Dein Beruf bestimmt, welche Items",
                    "§7du bei dem persönlichen", "§7Ankauf verkaufen kannst.")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .itemStack)
        }

        inv.setItem(3, CustomItem(Material.PAPER, 1)
            .setName("§6Kostenlose Berufswechsel:")
            .setLore("§7Du kannst noch §b${user.freeJobChanges} mal", "§7kostenlos deinen Beruf wechseln.",
                "", "§7Hast du keinen kostenlosen Wechsel", "§7mehr übrig, musst du §6100.000$ §7zahlen.")
            .itemStack)
        inv.setItem(5, CustomItem(Material.FLETCHING_TABLE, 1)
            .setName("§6Berufe")
            .setLore("§7Öffnet die Auswahl der Berufe")
            .itemStack)
        inv.setItem(6, CustomItem(Material.IRON_INGOT, 1)
            .setName("§6Allgemeiner Ankauf")
            .setLore("§7Öffnet den Ankauf")
            .itemStack)
        inv.setItem(7, CustomItem(Material.GOLD_INGOT, 1)
            .setName("§6Persönlicher Ankauf")
            .setLore("§7Öffnet einen Ankauf, der je", "§7nach Beruf variiert.")
            .itemStack)

        if (view != GUIView.JOBS) {
            inv.setItem(48, CustomItem(Material.MAGENTA_GLAZED_TERRACOTTA, 1).setName("§bUmrechnung")
                .setLore("§7Rechne die Preise der Items", "§7automatisch um. Umrechnen in:",
                    getCalcAmountLine(uuid, 1),
                    getCalcAmountLine(uuid, 64),
                    getCalcAmountLine(uuid, 2304),
                    "",
                    "§7Zum Durchschalten klicken"
                ).itemStack)
            inv.setItem(50, CustomItem(Material.SUNFLOWER, 1).setName("§bVerkauf")
                .setLore("§7Lege fest, wie viele Items", "§7auf einmal verkauft werden:",
                    getSellAmountLine(uuid, 1),
                    getSellAmountLine(uuid, 64),
                    getSellAmountLine(uuid, 2304),
                    "",
                    "§7Zum Durchschalten klicken"
                ).itemStack)
        }

        when (view) {
            GUIView.JOBS -> {
                var slot = 19
                Job.values().forEach {
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }
                    inv.setItem(slot, CustomItem(it.icon, 1)
                        .setName("§b${it.friendlyName}").setLore("§7Klicke für Berufswechsel")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES).itemStack)
                    slot++
                }
            }
            GUIView.SELL -> {
                val calc = if (!main.playerManager.calculateAmount.containsKey(uuid)) {
                    1
                } else {
                    main.playerManager.calculateAmount[uuid]!!
                }
                var slot = 19
                GlobalItem.values().forEach {
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }
                    inv.setItem(slot, CustomItem(it.material, it.amount).setName("§6${it.friendlyName}")
                        .setLore("§8- §7Umrechnung §8-",
                            "§eAnzahl: §6$calc", "§ePreis: §6${(it.price / it.amount) * calc}$")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .itemStack)
                    slot++
                }
            }
            GUIView.SELL_PERSONAL -> {
                if (job == null) {
                    player.sendMessage(Message.JOB_UNSET.getString().get())
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f)
                    return
                }

                val calc = if (!main.playerManager.calculateAmount.containsKey(uuid)) {
                    1
                } else {
                    main.playerManager.calculateAmount[uuid]!!
                }
                var slot = 19
                JobSpecificItem.values().filter { it.job == user.job }.forEach {
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }
                    inv.setItem(slot, CustomItem(it.material, it.amount).setName("§6${it.friendlyName}")
                        .setLore("§8- §7Umrechnung §8-",
                            "§eAnzahl: §6$calc", "§ePreis: §6${(it.price / it.amount) * calc}$")
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .itemStack)
                    slot++
                }
            }
            else -> throw IllegalArgumentException()
        }

        player.openInventory(inv)
    }

    private fun getCalcAmountLine(uuid: UUID, amount: Int): String {
        val actual = main.playerManager.calculateAmount.getOrDefault(uuid, 1)
        val prefix = if (actual == amount) {
            "§a▸ §2"
        } else {
            "§c "
        }
        return "$prefix$amount Stück"
    }

    private fun getSellAmountLine(uuid: UUID, amount: Int): String {
        val actual = main.playerManager.sellAmount.getOrDefault(uuid, 1)
        val prefix = if (actual == amount) {
            "§a▸ §2"
        } else {
            "§c "
        }
        return "$prefix$amount Stück"
    }

    fun setNextCalcAmount(uuid: UUID) {
        val map = main.playerManager.calculateAmount
        when (map.getOrDefault(uuid, 1)) {
            1 -> map[uuid] = 64
            64 -> map[uuid] = 2304
            else -> map[uuid] = 1
        }
    }

    fun setNextSellAmount(uuid: UUID) {
        val map = main.playerManager.sellAmount
        when (map.getOrDefault(uuid, 1)) {
            1 -> map[uuid] = 64
            64 -> map[uuid] = 2304
            else -> map[uuid] = 1
        }
    }

    @Suppress("Deprecation")
    fun openConfirmGUI(player: Player, job: Job) {
        val inv = Bukkit.createInventory(player, 27, GUIView.CONFIRM.getTitle())
        inv.setItem(11, CustomItem(Material.LIME_CONCRETE, 1)
            .setName("§aBestätige").setLore("§7Job zu ${job.friendlyName} ändern").itemStack)
        inv.setItem(15, CustomItem(Material.RED_CONCRETE, 1)
            .setName("§aLehne ab").setLore("§7Job zu ${job.friendlyName} ändern").itemStack)
        player.openInventory(inv)
    }

    @Suppress("Deprecation")
    fun extractJobFromItemMeta(item: ItemStack): Job? {
        val lore = item.itemMeta.lore ?: return null
        val jobName = lore[0].split(" ")[3]
        return Job.values().firstOrNull { it.friendlyName == jobName }
    }

    private fun placeholdersByPattern(inv: Inventory, startRow: Int, vararg pattern: String) {
        var lineCount = startRow * 9
        for (line in pattern) {
            if (line.length != 9) {
                continue
            }
            val parts = line.split("")
            var slot = lineCount
            for (identifier in parts) {
                if (identifier.length != 1) {
                    continue
                }
                val mat = patternToMaterial(identifier)
                if (mat != null) {
                    inv.setItem(slot, CustomItem(mat, 1).setName("§0").itemStack)
                }
                slot++
            }
            lineCount += 9
        }
    }

    private fun patternToMaterial(identifier: String): Material? {
        return when (identifier) {
            "n" -> null
            "w" -> Material.WHITE_STAINED_GLASS_PANE
            "b" -> Material.BLACK_STAINED_GLASS_PANE
            else -> null
        }
    }

    fun getRequirementResult(player: Player, user: User): RequirementResult {
        if (user.freeJobChanges > 0) {
            return RequirementResult.USE_FREE
        }
        if (main.economy.getBalance(player) >= 100000.0) {
            return RequirementResult.PAY
        }
        return RequirementResult.NO_MONEY
    }

    fun sellItem(player: Player, item: JobSpecificItem) {
        val maxAmount = main.playerManager.sellAmount.getOrDefault(player.uniqueId, 1)
        var amount = getItemAmount(player.inventory, item.material)
        if (amount == 0) {
            player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
            player.sendMessage(Message.SELL_NOT_ENOUGH.getString().replace("%name", item.friendlyName).get())
            return
        }
        if (maxAmount < amount) {
            amount = maxAmount
        }

        val reward = item.price * (amount.toDouble() / item.amount)
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
        player.sendMessage(Message.SELL_SOLD.getString()
            .replace("%amount", amount.toString())
            .replace("%name", item.friendlyName)
            .replace("%reward", reward.toString())
            .get())
        player.inventory.removeItem(ItemStack(item.material, amount))
        main.economy.depositPlayer(player, reward)
        main.logger.info("${player.name} (UUID: ${player.uniqueId}) sold ${amount}x ${item.material} " +
                "for $reward to the admin shop")
    }

    fun sellItem(player: Player, item: GlobalItem) {
        val maxAmount = main.playerManager.sellAmount.getOrDefault(player.uniqueId, 1)
        var amount = getItemAmount(player.inventory, item.material)
        if (amount == 0) {
            player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
            player.sendMessage(Message.SELL_NOT_ENOUGH.getString().replace("%name", item.friendlyName).get())
            return
        }
        if (maxAmount < amount) {
            amount = maxAmount
        }

        val reward = item.price * (amount.toDouble() / item.amount)
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
        player.sendMessage(Message.SELL_SOLD.getString()
            .replace("%amount", amount.toString())
            .replace("%name", item.friendlyName)
            .replace("%reward", reward.toString())
            .get())
        player.inventory.removeItem(ItemStack(item.material, amount))
        main.economy.depositPlayer(player, reward)
        main.logger.info("${player.name} (UUID: ${player.uniqueId}) sold ${amount}x ${item.material} " +
                "for $reward to the admin shop")
    }

    private fun getItemAmount(inv: Inventory, material: Material): Int {
        var amount = 0
        inv.filter { it != null && it.type == material }.forEach { amount += it.amount }
        return amount
    }

}
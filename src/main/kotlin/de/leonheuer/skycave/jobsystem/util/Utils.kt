package de.leonheuer.skycave.jobsystem.util

import com.mongodb.client.model.Filters
import de.leonheuer.mcguiapi.gui.GUIPattern
import de.leonheuer.mcguiapi.utils.ItemBuilder
import de.leonheuer.skycave.jobsystem.JobSystem
import de.leonheuer.skycave.jobsystem.enums.*
import de.leonheuer.skycave.jobsystem.model.User
import de.leonheuer.skycave.jobsystem.model.UserLevel
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.text.DecimalFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {

    private val main = JavaPlugin.getPlugin(JobSystem::class.java)

    fun openGUI(player: Player, view: GUIView) {
        val pattern = GUIPattern.ofPattern("bbbbbbbbb")
            .withMaterial('b', ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name("§0").asItem())
        val gui = main.guiFactory.createGUI(6, view.getTitle())
            .formatPattern(pattern.startAtLine(1))
            .formatPattern(pattern.startAtLine(6))

        val filter = Filters.eq("uuid", player.uniqueId)
        var user = main.users.find(filter).first()
        if (user == null) {
            user = User(player.uniqueId, null, null, 6, UserLevel(0.0))
            main.users.insertOne(user)
        }
        val uuid = player.uniqueId
        val date = user.lastJobChange
        val job = user.job

        if (date == null) {
            gui.setItem(1, 2, ItemBuilder.of(Material.CLOCK)
                .name("§6Letzter Berufswechsel:")
                .description("§cnoch nie")
                .asItem())
        } else {
            val dtf = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
            gui.setItem(1, 2, ItemBuilder.of(Material.CLOCK)
                .name("§6Letzter Berufswechsel:")
                .description(
                    "§b${dtf.format(date)}", "",
                    "§7Du kannst alle 48 Stunden", "§7deinen Beruf wechseln"
                ).asItem())
        }

        if (job == null) {
            gui.setItem(1,3, ItemBuilder.of(Material.BARRIER)
                .name("§6Dein Beruf:")
                .description("§cDu hast keinen Beruf")
                .asItem())
        } else {
            val item = ItemBuilder.of(job.icon)
                .name("§6Dein Beruf:")
                .description(
                    "§b${job.friendlyName}", "", "§7Dein Beruf bestimmt, welche Items",
                    "§7du bei dem persönlichen", "§7Ankauf verkaufen kannst."
                ).asItem()
            item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            gui.setItem(1, 3, item)
        }

        gui.setItem(1, 4, ItemBuilder.of(Material.PAPER)
            .name("§6Kostenlose Berufswechsel:")
            .description(
                "§7Du kannst noch §b${user.freeJobChanges} mal", "§7kostenlos deinen Beruf wechseln.",
                "", "§7Hast du keinen kostenlosen Wechsel", "§7mehr übrig, musst du §6${main.getJobChangeFee()}$ §7zahlen."
            ).asItem()
        ).setItem(5, ItemBuilder.of(Material.FLETCHING_TABLE)
            .name("§6Berufe")
            .description("§7Öffnet die Auswahl der Berufe")
            .asItem()
        ) {
            if (view != GUIView.JOBS) {
                CustomSound.CLICK.playTo(player)
                openGUI(player, GUIView.JOBS)
            }
        }.setItem(6, ItemBuilder.of(Material.IRON_INGOT)
            .name("§6Allgemeiner Ankauf")
            .description("§7Öffnet den Ankauf")
            .asItem()
        ){
            if (view != GUIView.SELL) {
                CustomSound.CLICK.playTo(player)
                openGUI(player, GUIView.SELL)
            }
        }.setItem(7, ItemBuilder.of(Material.GOLD_INGOT)
            .name("§6Persönlicher Ankauf")
            .description("§7Öffnet einen Ankauf, der je", "§7nach Beruf variiert.")
            .asItem()
        ){
            if (view != GUIView.SELL_PERSONAL) {
                CustomSound.CLICK.playTo(player)
                openGUI(player, GUIView.SELL_PERSONAL)
            }
        }

        if (view != GUIView.JOBS) {
            gui.setItem(6, 4, ItemBuilder.of(Material.MAGENTA_GLAZED_TERRACOTTA)
                .name("§bUmrechnung")
                .description(
                    "§7Rechne die Preise der Items", "§7automatisch um. Umrechnen in:",
                    getCalcAmountLine(uuid, 1),
                    getCalcAmountLine(uuid, 64),
                    getCalcAmountLine(uuid, 2304),
                    "", "§7Zum Durchschalten klicken"
                ).asItem()
            ) {
                CustomSound.CLICK.playTo(player)
                setNextCalcAmount(player.uniqueId)
                openGUI(player, view)
            }.setItem(6, 6, ItemBuilder.of(Material.SUNFLOWER)
                .name("§bVerkauf")
                .description(
                    "§7Lege fest, wie viele Items", "§7auf einmal verkauft werden:",
                    getSellAmountLine(uuid, 1),
                    getSellAmountLine(uuid, 64),
                    getSellAmountLine(uuid, 2304),
                    "", "§7Zum Durchschalten klicken"
                ).asItem()
            ) {
                CustomSound.CLICK.playTo(player)
                setNextSellAmount(player.uniqueId)
                openGUI(player, view)
            }
        }

        when (view) {
            GUIView.JOBS -> {
                var slot = 19
                for (j: Job in Job.values()) {
                    // margin
                    if (slot.mod(9) == 0) {
                        slot ++
                    } else if ((slot + 1).mod(9) == 0) {
                        slot += 2
                    }

                    val item = ItemBuilder.of(j.icon)
                        .name("§b${j.friendlyName}")
                        .description(
                            "§7Klicke für Berufswechsel", "",
                            "§aItems für den persönlichen Ankauf:", "§e§o${j.sellContent}"
                        ).asItem()
                    item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

                    gui.setItem(slot, item) {
                        val result = getRequirementResult(player, user)
                        if (result == RequirementResult.FIRST) {
                            openConfirmGUI(player, j, result)
                            return@setItem
                        }

                        if (j == job) {
                            CustomSound.ERROR.playTo(player)
                            player.sendMessage(
                                Message.JOB_CHANGE_ALREADY.getString().replaceAll("%job", job.friendlyName).get()
                            )
                            return@setItem
                        }

                        if (Duration.between(date, LocalDateTime.now()).toHours() < 48) {
                            CustomSound.ERROR.playTo(player)
                            player.sendMessage(Message.JOB_CHANGE_WAIT.getString().get())
                            return@setItem
                        }

                        when (result) {
                            RequirementResult.NO_MONEY -> {
                                CustomSound.ERROR.playTo(player)
                                player.sendMessage(Message.JOB_CHANGE_NO_MONEY.getString().get())
                            }
                            else -> {
                                openConfirmGUI(player, j, result)
                            }
                        }
                    }
                    slot++
                }
            }
            GUIView.SELL -> {
                val calc = main.calculateAmount.getOrDefault(uuid, 1)
                val fmt = DecimalFormat("#.##")
                var slot = 9
                for (i: GlobalItem in GlobalItem.values()) {
                    val price = fmt.format((i.price / i.amount) * calc)
                    val item = ItemBuilder.of(i.material)
                        .name("§6${i.friendlyName}")
                        .description(
                            "§8- §7Umrechnung §8-",
                            "§eAnzahl: §6$calc", "§ePreis: §6$price$"
                        ).asItem()
                    item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    gui.setItem(slot, item) { sellItem(player, i) }
                    slot++
                }
            }
            GUIView.SELL_PERSONAL -> {
                if (job == null) {
                    player.sendMessage(Message.JOB_UNSET.getString().get())
                    CustomSound.ERROR.playTo(player)
                    return
                }

                val calc = main.calculateAmount.getOrDefault(uuid, 1)
                val fmt = DecimalFormat("#.##")
                var slot = 9
                for (i: JobSpecificItem in JobSpecificItem.values()) {
                    if (i.job != job) {
                        continue
                    }
                    val price = fmt.format((i.price / i.amount) * calc)
                    val item = ItemBuilder.of(i.material)
                        .name("§6${i.friendlyName}")
                        .description(
                            "§8- §7Umrechnung §8-",
                            "§eAnzahl: §6$calc", "§ePreis: §6$price$"
                        ).asItem()
                    item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    gui.setItem(slot, item) { sellItem(player, i) }
                    slot++
                }
            }
            else -> throw IllegalArgumentException()
        }

        gui.show(player)
    }

    private fun getCalcAmountLine(uuid: UUID, amount: Int): String {
        val actual = main.calculateAmount.getOrDefault(uuid, 1)
        val prefix = if (actual == amount) {
            "§a▸ §2"
        } else {
            "§c "
        }
        return "$prefix$amount Stück"
    }

    private fun getSellAmountLine(uuid: UUID, amount: Int): String {
        val actual = main.sellAmount.getOrDefault(uuid, 1)
        val prefix = if (actual == amount) {
            "§a▸ §2"
        } else {
            "§c "
        }
        return "$prefix$amount Stück"
    }

    private fun setNextCalcAmount(uuid: UUID) {
        val map = main.calculateAmount
        when (map.getOrDefault(uuid, 1)) {
            1 -> map[uuid] = 64
            64 -> map[uuid] = 2304
            else -> map[uuid] = 1
        }
    }

    private fun setNextSellAmount(uuid: UUID) {
        val map = main.sellAmount
        when (map.getOrDefault(uuid, 1)) {
            1 -> map[uuid] = 64
            64 -> map[uuid] = 2304
            else -> map[uuid] = 1
        }
    }

    private fun openConfirmGUI(player: Player, job: Job, result: RequirementResult) {
        val filter = Filters.eq("uuid", player.uniqueId)
        var user = main.users.find(filter).first()
        if (user == null) {
            user = User(player.uniqueId, null, null, 6, UserLevel(0.0))
            main.users.insertOne(user)
        }

        val pattern = GUIPattern.ofPattern("_GGG_RRR_")
            .withMaterial('G', ItemBuilder.of(Material.LIME_STAINED_GLASS_PANE).name("§0").asItem())
            .withMaterial('R', ItemBuilder.of(Material.RED_STAINED_GLASS_PANE).name("§0").asItem())
            .withMaterial('_', null)
        main.guiFactory.createGUI(3, GUIView.CONFIRM.getTitle())
            .formatPattern(pattern.startAtLine(1))
            .formatPattern(pattern.startAtLine(2))
            .formatPattern(pattern.startAtLine(3))
            .setItem(2, 3, ItemBuilder.of(Material.LIME_CONCRETE)
                .name("§aBestätige")
                .description(
                    "§7${result.title}", "§7Dein Job wird zu ${job.friendlyName} geändert."
                ).asItem()
            ) {
                when (result) {
                    RequirementResult.PAY -> {
                        user.job = job
                        user.lastJobChange = LocalDateTime.now()
                        main.users.replaceOne(filter, user)
                        if (main.economy.withdrawPlayer(player, main.getJobChangeFee()).transactionSuccess()) {
                            CustomSound.SUCCESS.playTo(player)
                            player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                                .replace("%job", job.friendlyName).get())
                            player.sendMessage(Message.JOB_CHANGE_PAY.getString()
                                .replace("%fee", "" + main.getJobChangeFee()).get())
                        } else {
                            CustomSound.ERROR.playTo(player)
                        }
                    }
                    RequirementResult.FIRST -> {
                        user.job = job
                        user.lastJobChange = LocalDateTime.now()
                        main.users.replaceOne(filter, user)
                        CustomSound.SUCCESS.playTo(player)
                        player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString().replace("%job", job.friendlyName).get())
                        openGUI(player, GUIView.JOBS)
                    }
                    RequirementResult.USE_FREE -> {
                        user.job = job
                        user.lastJobChange = LocalDateTime.now()
                        user.freeJobChanges -= 1
                        main.users.replaceOne(filter, user)
                        CustomSound.SUCCESS.playTo(player)
                        player.sendMessage(Message.JOB_CHANGE_SUCCESS.getString()
                            .replace("%job", job.friendlyName).get())
                        player.sendMessage(Message.JOB_CHANGE_USE_FREE.getString()
                            .replace("%amount", user.freeJobChanges.toString()).get())
                        openGUI(player, GUIView.JOBS)
                    }
                    else -> {}
                }
            }.setItem(15, ItemBuilder.of(Material.RED_CONCRETE)
                .name("§cLehne ab")
                .description("§7Dein Job wird nicht geändert.")
                .asItem()
            ) {
                if (user.freeJobChanges == 0) {
                    player.sendMessage(Message.JOB_CHANGE_ABORT.getString().get())
                } else {
                    player.sendMessage(Message.JOB_CHANGE_ABORT_FREE.getString().get())
                }
                CustomSound.ERROR.playTo(player)
                openGUI(player, GUIView.JOBS)
            }.show(player)
    }

    private fun getRequirementResult(player: Player, user: User): RequirementResult {
        if (user.lastJobChange == null) {
            return RequirementResult.FIRST
        }
        if (user.freeJobChanges > 0) {
            return RequirementResult.USE_FREE
        }
        if (main.economy.getBalance(player) >= main.getJobChangeFee()) {
            return RequirementResult.PAY
        }
        return RequirementResult.NO_MONEY
    }

    private fun sellItem(player: Player, item: JobSpecificItem) {
        val maxAmount = main.sellAmount.getOrDefault(player.uniqueId, 1)
        var amount = getItemAmount(player.inventory, item.material)
        if (amount == 0 || !player.inventory.containsAtLeast(ItemStack(item.material, 1), 1)) {
            CustomSound.ERROR.playTo(player)
            player.sendMessage(Message.SELL_NOT_ENOUGH.getString().replace("%name", item.friendlyName).get())
            return
        }
        if (maxAmount < amount) {
            amount = maxAmount
        }

        val reward = item.price * (amount.toDouble() / item.amount)
        val formatter = DecimalFormat("#.##")
        CustomSound.SUCCESS.playTo(player)
        player.sendMessage(Message.SELL_SOLD.getString()
            .replace("%amount", amount.toString())
            .replace("%name", item.friendlyName)
            .replace("%reward", formatter.format(reward))
            .get())
        player.inventory.removeItemAnySlot(ItemStack(item.material, amount))
        main.economy.depositPlayer(player, reward)
        main.logger.info("${player.name} (UUID: ${player.uniqueId}) sold ${amount}x ${item.material} " +
                "for $reward to the admin shop")
    }

    private fun sellItem(player: Player, item: GlobalItem) {
        val maxAmount = main.sellAmount.getOrDefault(player.uniqueId, 1)
        var amount = getItemAmount(player.inventory, item.material)
        if (amount == 0 || !player.inventory.containsAtLeast(ItemStack(item.material, 1), 1)) {
            CustomSound.ERROR.playTo(player)
            player.sendMessage(Message.SELL_NOT_ENOUGH.getString().replace("%name", item.friendlyName).get())
            return
        }
        if (maxAmount < amount) {
            amount = maxAmount
        }

        val reward = item.price * (amount.toDouble() / item.amount)
        val formatter = DecimalFormat("#.##")
        CustomSound.SUCCESS.playTo(player)
        player.sendMessage(Message.SELL_SOLD.getString()
            .replace("%amount", amount.toString())
            .replace("%name", item.friendlyName)
            .replace("%reward", formatter.format(reward))
            .get())
        player.inventory.removeItemAnySlot(ItemStack(item.material, amount))
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
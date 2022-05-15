package net.moon.vouchers.command;

import net.moon.vouchers.VoucherPlugin;
import net.moon.vouchers.utils.CC;
import net.moon.vouchers.voucher.Voucher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.refinedev.fethmusmioma.annotation.Command;
import xyz.refinedev.fethmusmioma.annotation.Parameter;
import xyz.refinedev.fethmusmioma.annotation.SubCommand;

import java.util.Arrays;

public class VoucherCommand {

    private final VoucherPlugin instance;

    public VoucherCommand(VoucherPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "voucher", permission = "voucher.admin")
    public void execute(Player player) {
        CC.chat(Arrays.asList("&8&m--------------------------------",
                "&3/voucher open &7- &fOpen the vouchers GUI",
                "&3/voucher give <player> <voucherName> <amount>",
                "&8&m--------------------------------"))
                .forEach(player::sendMessage);
    }
    
    @SubCommand(label = "open", permission = "voucher.admin", parent = "voucher")
    public void executeOpen(Player player) {
        this.instance.getVoucherHandler().openMenu(player);
    }
    
    @SubCommand(label = "give", permission = "voucher.admin", parent = "voucher")
    public void executeGive(CommandSender sender, @Parameter(name = "player") Player player, @Parameter(name = "voucherName")Voucher voucher, @Parameter(name = "amount") Integer amount) {
        if (player == null) {
            sender.sendMessage(CC.chat("&cThat player is not online."));
            return;
        }

        if (voucher == null) {
            sender.sendMessage(CC.chat("&cThat voucher does not exist."));
            return;
        }

        if (amount == null || amount < 1) {
            sender.sendMessage(CC.chat("&cThe voucher amount must be greater-than or equal to one."));
            return;
        }

        ItemStack itemStack = voucher.getItemStack();
        itemStack.setAmount(amount);

        sender.sendMessage(CC.chat("&3Moon&bPvP&8: &fYou have given &e" + player.getName() + " &f" + amount + " " + voucher.getName() + " vouchers."));
        player.getInventory().addItem(itemStack);
    }
}

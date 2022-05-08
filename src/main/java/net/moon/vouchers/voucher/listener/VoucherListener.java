package net.moon.vouchers.voucher.listener;

import net.moon.vouchers.VoucherPlugin;
import net.moon.vouchers.voucher.Voucher;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class VoucherListener implements Listener {

    private final VoucherPlugin instance;

    public VoucherListener(VoucherPlugin instance) {
        this.instance = instance;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        Optional<Voucher> optional = this.instance.getVoucherHandler().getVoucherWithItemStack(event.getItem());

        if (!optional.isPresent()) {
            return;
        }

        Voucher voucher = optional.get();

        for (String command : voucher.getCommands()) {
            this.instance.getServer().dispatchCommand(this.instance.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
        }

        if (itemStack.getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(new ItemStack(Material.AIR));
        }
    }
}

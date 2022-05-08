package net.moon.vouchers.command;

import net.moon.vouchers.VoucherPlugin;
import org.bukkit.entity.Player;
import xyz.refinedev.fethmusmioma.annotation.Command;

public class VoucherCommand {

    private final VoucherPlugin instance;

    public VoucherCommand(VoucherPlugin instance) {
        this.instance = instance;
    }

    @Command(label = "voucher", permission = "voucher.admin")
    public void execute(Player player) {
        this.instance.getVoucherHandler().openMenu(player);
    }
}

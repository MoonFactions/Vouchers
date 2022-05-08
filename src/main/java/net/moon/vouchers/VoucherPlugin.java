package net.moon.vouchers;

import com.samjakob.spigui.SpiGUI;
import lombok.Getter;
import net.moon.vouchers.command.VoucherCommand;
import net.moon.vouchers.voucher.VoucherHandler;
import net.moon.vouchers.voucher.listener.VoucherListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.fethmusmioma.CommandHandler;

@Getter
public class VoucherPlugin extends JavaPlugin {

    private SpiGUI spiGUI;

    private VoucherHandler voucherHandler;

    public void onEnable() {
        this.spiGUI = new SpiGUI(this);
        this.voucherHandler = new VoucherHandler(this);

        this.registerListeners();
        this.registerCommands();
    }

    private void registerListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new VoucherListener(this), this);
    }

    private void registerCommands() {
        CommandHandler commandHandler = new CommandHandler(this, "vouchers");
        commandHandler.registerCommand(new VoucherCommand(this));
    }

}

package net.moon.vouchers.voucher;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import net.moon.vouchers.VoucherPlugin;
import net.moon.vouchers.utils.CC;
import net.moon.vouchers.utils.Config;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class VoucherHandler {

    private final Map<String, Voucher> voucherMap = new HashMap<>();

    private final VoucherPlugin instance;

    private Config config;


    public VoucherHandler(VoucherPlugin instance) {
        this.instance = instance;
        this.config = new Config(this.instance, "vouchers");
        this.load();
    }

    public Optional<Voucher> getVoucher(String name) {
        if (voucherMap.containsKey(name.toUpperCase())) {
            return Optional.of(voucherMap.get(name.toUpperCase()));
        }
        return Optional.empty();
    }

    public Optional<Voucher> getVoucherWithItemStack(ItemStack itemStack) {
        for (Voucher voucher : this.voucherMap.values()) {
            if (voucher.getItemStack().isSimilar(itemStack)) {
                return Optional.of(voucher);
            }
        }
        return Optional.empty();
    }

    public List<String> getVoucherNames() {
        return this.voucherMap.values().stream().map(Voucher::getName).collect(Collectors.toList());
    }


    public void openMenu(Player player) {
        SGMenu sgMenu = this.instance.getSpiGUI().create(CC.chat(this.config.getString("voucher_menu_title")), this.config.getInt("voucher_menu_rows"));
        for (Voucher voucher : this.voucherMap.values()) {
            SGButton sgButton = new SGButton(voucher.getItemStack())
                    .withListener(event -> {
                        ItemStack itemStack = event.getCurrentItem();

                        if (itemStack == null) {
                            event.setCancelled(true);
                            return;
                        }

                        Optional<Voucher> optional = this.getVoucherWithItemStack(itemStack);

                        if (!optional.isPresent()) {
                            event.setCancelled(true);
                            return;
                        }

                        player.getInventory().addItem(itemStack);
                        event.setCancelled(true);
                    });

            sgMenu.addButton(sgButton);
        }
        player.openInventory(sgMenu.getInventory());
    }

    public void load() {
        ConfigurationSection section = this.config.getConfigurationSection("vouchers");
        for (String key : section.getKeys(false)) {
            Voucher voucher = new Voucher(Material.getMaterial(section.getString(key + ".material")), (short) section.getInt(key + ".data"), section.getString(key + ".name"), section.getString(key + ".display_name"), section.getStringList(key + ".lore"), section.getStringList(key + ".commands"));
            voucherMap.put(voucher.getName().toUpperCase(), voucher);
        }

    }

}

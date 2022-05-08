package net.moon.vouchers.voucher;

import lombok.Getter;
import lombok.Setter;
import net.moon.vouchers.utils.CC;
import net.moon.vouchers.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public class Voucher {

    private Material material;

    private short data;

    private String name;

    private String displayName;

    private List<String> lore;

    private List<String> commands;

    public Voucher(Material material, short data, String name, String displayName, List<String> lore, List<String> commands) {
        this.material = material;
        this.data = data;
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
        this.commands = commands;
    }

    public ItemStack getItemStack() {
        return new ItemBuilder(this.material)
                .setData(this.data)
                .setName(this.displayName)
                .setLore(this.lore)
                .build();
    }
}

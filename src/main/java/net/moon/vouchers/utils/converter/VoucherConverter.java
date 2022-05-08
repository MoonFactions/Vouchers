package net.moon.vouchers.utils.converter;

import net.moon.vouchers.VoucherPlugin;
import net.moon.vouchers.voucher.Voucher;
import org.bukkit.command.CommandSender;
import xyz.refinedev.fethmusmioma.converter.IConverter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VoucherConverter implements IConverter<Voucher> {

    private final VoucherPlugin instance;

    public VoucherConverter(VoucherPlugin instance) {
        this.instance = instance;
    }

    @Override
    public Voucher fromString(String s, CommandSender commandSender) {
        Optional<Voucher> optional = this.instance.getVoucherHandler().getVoucher(s);

        return optional.orElse(null);
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender) {
        return this.instance.getVoucherHandler().getVoucherNames();
    }
}

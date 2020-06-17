package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Item;

public class UltimateStackerSupport {

    @Getter
    @Setter
    public static boolean enabled = false;

    public static int getItemAmount(Item item) {
        if(UltimateStackerSupport.isEnabled())
            return com.songoda.ultimatestacker.UltimateStacker.getActualItemAmount(item);
        return item.getItemStack().getAmount();
    }

}

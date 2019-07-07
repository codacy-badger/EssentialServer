package io.github.coachluck.utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("unused")
public class InventoryHelper {

    public static void removeItemFromInventory(PlayerInventory inventory, Material type, int amount) {
        for (ItemStack is : inventory.getContents()) {
            if (is != null && is.getType() == type) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.remove(is);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

    public boolean obtainInventoryContents(PlayerInventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type)
                return true;
        }
        return false;
    }
}
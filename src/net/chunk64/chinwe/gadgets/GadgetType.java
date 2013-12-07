package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum GadgetType
{
	PISTOL(BondUtils.setInfo(Material.TRIPWIRE_HOOK, "&6Silenced Walther PPK", Arrays.asList("&aYour gun of choice"), null)),
	PEN(BondUtils.setInfo(Material.BLAZE_ROD, "&6Ballpoint Pen", Arrays.asList("&aBe careful about clicking it..."), null));

	private ItemStack itemStack;

	GadgetType(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}


	public ItemStack getItemStack()
	{
		return itemStack;
	}
}

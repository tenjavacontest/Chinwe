package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum GadgetType
{
	PISTOL(BondUtils.setInfo(Material.TRIPWIRE_HOOK, "Silenced Walther PPK", Arrays.asList("Your gun of choice", "&7&oLeft click to shoot to kill", "&7&oYou have 8 shots, after which you will reload"))),
	PEN(BondUtils.setInfo(Material.BLAZE_ROD, "Ballpoint Pen", Arrays.asList("Write carefully", "&7&oClicking it three times will activate the 10 second fuse", "&7&oYou can throw it away if you like", "&7&oHolding on isn't recommended"))),
	WATCH(BondUtils.setInfo(Material.WATCH, "Omega Seamaster Wristwatch", Arrays.asList("Spectacularly tells the time", "&7&oIf you sneak and left click, you will shoot a grappling hook where you're looking", "&7&o   - Its range is 64m", "&7&oIf you sneak and right click, you will toggle the laser", "&7&o   - Hold it over each block for a tad to destroy it"))),
	TUXEDO(BondUtils.setInfo(Material.CHAINMAIL_CHESTPLATE, "Tuxedo",Arrays.asList("What a gentleman"))),
	MARTINI(BondUtils.setInfo(Material.POTION, "Martini", Arrays.asList("Shaken, not stirred")));

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

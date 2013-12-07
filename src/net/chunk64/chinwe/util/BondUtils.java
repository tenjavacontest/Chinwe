package net.chunk64.chinwe.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class BondUtils
{

	public static final String PREFIX = "§8[§6007§8] ";

	/**
	 * Standard messaging with prefix and & colour codes
	 */
	public static void message(CommandSender sender, String msg)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', msg));
	}

	/**
	 * @param itemStack          The itemstack to edit
	 * @param name        The optional name
	 * @param description The optional lore
	 * @param colour      The optional leather armour colour
	 * @return
	 */
	public static ItemStack setInfo(ItemStack itemStack, String name, List<String> description, Color colour)
	{
		ItemMeta meta = itemStack.getItemMeta();

		if (name != null)
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

		if (description != null)
		{
			for (int i = 0; i < description.size(); i++)
				description.set(i, ChatColor.translateAlternateColorCodes('&', description.get(i)));
			meta.setLore(description);
		}

		if (colour != null)
		{
			// must be leather
			if (itemStack.getType().toString().startsWith("LEATHER_"))
			{
				LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
				leatherArmorMeta.setColor(colour);
//				itemStack.setItemMeta(leatherArmorMeta);
			}
		}

		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack setInfo(Material material, String name, List<String> description, Color colour)
	{
		return setInfo(new ItemStack(material), name, description, colour);
	}

}

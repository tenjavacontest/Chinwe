package net.chunk64.chinwe.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BondUtils
{

	public static final String PREFIX = "§8[§5B§8] ";
	private static final Set<Material> TRANSPARENT = new HashSet<>();

	static
	{
		TRANSPARENT.add(Material.AIR);
		TRANSPARENT.add(Material.SAPLING);
		TRANSPARENT.add(Material.POWERED_RAIL);
		TRANSPARENT.add(Material.DETECTOR_RAIL);
		TRANSPARENT.add(Material.LONG_GRASS);
		TRANSPARENT.add(Material.DEAD_BUSH);
		TRANSPARENT.add(Material.YELLOW_FLOWER);
		TRANSPARENT.add(Material.RED_ROSE);
		TRANSPARENT.add(Material.BROWN_MUSHROOM);
		TRANSPARENT.add(Material.RED_MUSHROOM);
		TRANSPARENT.add(Material.TORCH);
		TRANSPARENT.add(Material.REDSTONE_WIRE);
		TRANSPARENT.add(Material.SEEDS);
		TRANSPARENT.add(Material.SIGN_POST);
		TRANSPARENT.add(Material.WOODEN_DOOR);
		TRANSPARENT.add(Material.LADDER);
		TRANSPARENT.add(Material.RAILS);
		TRANSPARENT.add(Material.WALL_SIGN);
		TRANSPARENT.add(Material.LEVER);
		TRANSPARENT.add(Material.STONE_PLATE);
		TRANSPARENT.add(Material.IRON_DOOR_BLOCK);
		TRANSPARENT.add(Material.WOOD_PLATE);
		TRANSPARENT.add(Material.REDSTONE_TORCH_OFF);
		TRANSPARENT.add(Material.REDSTONE_TORCH_ON);
		TRANSPARENT.add(Material.STONE_BUTTON);
		TRANSPARENT.add(Material.SUGAR_CANE_BLOCK);
		TRANSPARENT.add(Material.DIODE_BLOCK_OFF);
		TRANSPARENT.add(Material.DIODE_BLOCK_ON);
		TRANSPARENT.add(Material.TRAP_DOOR);
		TRANSPARENT.add(Material.PUMPKIN_STEM);
		TRANSPARENT.add(Material.MELON_STEM);
		TRANSPARENT.add(Material.VINE);
		TRANSPARENT.add(Material.NETHER_WARTS);
		TRANSPARENT.add(Material.WATER);
		TRANSPARENT.add(Material.STATIONARY_WATER);
		TRANSPARENT.add(Material.LAVA);
		TRANSPARENT.add(Material.STATIONARY_LAVA);
		TRANSPARENT.add(Material.WATER_LILY);
		TRANSPARENT.add(Material.STEP);
		TRANSPARENT.add(Material.CROPS);
		TRANSPARENT.add(Material.POTATO);
		TRANSPARENT.add(Material.CARROT);
	}


	/**
	 * Standard messaging with prefix and & colour codes
	 */

	public static void message(CommandSender sender, String msg)
	{
		sender.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', msg));
	}

	/**
	 * @param itemStack   The ItemStack to edit
	 * @param name        The optional name
	 * @param description The optional lore
	 * @return The edited ItemStack
	 */
	public static ItemStack setInfo(ItemStack itemStack, String name, List<String> description)
	{
		ItemMeta meta = itemStack.getItemMeta();

		if (name != null)
			meta.setDisplayName(ChatColor.DARK_AQUA + ChatColor.translateAlternateColorCodes('&', name));

		if (description != null)
		{
			for (int i = 0; i < description.size(); i++)
				description.set(i, ChatColor.BLUE + ChatColor.translateAlternateColorCodes('&', description.get(i)));
			meta.setLore(description);
		}

//		if (colour != null)
//		{
//			// must be leather
//			if (itemStack.getType().toString().startsWith("LEATHER_"))
//			{
//				LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
//				leatherArmorMeta.setColor(colour);
//			}
//		}

		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack setInfo(Material material, String name, List<String> description)
	{
		return setInfo(new ItemStack(material), name, description);
	}

	/**
	 * Gets BlockFace corresponding to the direction you're looking in
	 */
	public static BlockFace getDirection(Location location)
	{
		float yaw = location.getYaw();
		int angle = Math.abs(45 * (Math.round(yaw / 45)));
		BlockFace blockFace;

		// TODO more precision
		switch (angle)
		{
			case 0:
			case 360:
				blockFace = BlockFace.SOUTH;
				break;
			case 45:
				blockFace = BlockFace.SOUTH_WEST;
				break;
			case 90:
				blockFace = BlockFace.WEST;
				break;
			case 135:
				blockFace = BlockFace.NORTH_WEST;
				break;
			case 180:
				blockFace = BlockFace.NORTH;
				break;
			case 225:
				blockFace = BlockFace.NORTH_EAST;
				break;
			case 270:
				blockFace = BlockFace.EAST;
				break;
			case 315:
				blockFace = BlockFace.SOUTH_EAST;
				break;
			default:
				blockFace = BlockFace.SOUTH; // why not
		}

		return blockFace.getOppositeFace();
	}

	/**
	 * Returns true if the given material is transparent, otherwise false
	 */
	public static boolean isTransparent(Material material)
	{
		return TRANSPARENT.contains(material);
	}


}

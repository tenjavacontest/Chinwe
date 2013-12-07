package net.chunk64.chinwe.gadgets;

import org.bukkit.Material;

public enum GadgetType
{
	PISTOL(Material.FENCE_GATE), PEN(Material.BLAZE_ROD);

	private Material material;

	GadgetType(Material material)
	{
		this.material = material;
	}

	public Material getMaterial()
	{
		return material;
	}
}

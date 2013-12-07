package net.chunk64.chinwe.commands;

import org.bukkit.permissions.PermissionDefault;

public enum Permission
{

	BOND("join MI6", true), BONDADMIN(null, false), LASER("use the laser", false), GRAPPLE("use the grapple", true), PEN("use the explosive pen", false), GUN_MURDER("murder other players", false);

	private static final String PLUGIN_NAME = "bond";
	private String message;
	private boolean defaultPerm;

	Permission(String message, boolean defaultPerm)
	{
		this.message = message;
		this.defaultPerm = defaultPerm;
	}

	public String getMessage()
	{
		return message == null ? "do that!" : message;
	}

	@Override
	public String toString()
	{
		return name().toLowerCase().replace("_", "");
	}

	public boolean isDefault()
	{
		return defaultPerm;
	}

	public org.bukkit.permissions.Permission getPermission()
	{
		return new org.bukkit.permissions.Permission(PLUGIN_NAME + "." + toString(), defaultPerm ? PermissionDefault.TRUE : PermissionDefault.OP);
	}

}

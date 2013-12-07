package net.chunk64.chinwe.commands;

import org.bukkit.permissions.PermissionDefault;

public enum Permission
{

	BOND(null, false);

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

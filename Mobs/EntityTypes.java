package com.opensourcery.minecraft;

public enum EntityTypes
{
    //NAME("Entity name", Entity ID, yourcustomclass.class);
    CUSTOM_ZOMBIE("Zombie", 54, CustomZombie.class); //You can add as many as you want.

    private EntityTypes(String name, int id, Class<? extends Entity> custom)
    {
        addToMaps(custom, name, id);
    }

  	public static void spawnEntity(Entity entity, Location loc)
   	{
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
   	}

    private static void addToMaps(Class clazz, String name, int id)
    {
        //getPrivateField is the method from above.
        //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
        ((Map)getPrivateField("c", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, clazz);
        ((Map)getPrivateField("d", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(clazz, name);
        //((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        ((Map)getPrivateField("f", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }
}
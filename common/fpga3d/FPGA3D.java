package fpga3d;

import fpga3d.block.ModBlocks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import fpga3d.core.proxy.CommonProxy;
import fpga3d.network.PacketHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
@NetworkMod(channels = {Reference.MOD_CHANNEL}, clientSideRequired=true, serverSideRequired=false, packetHandler = PacketHandler.class)
public class FPGA3D {
	
    @Instance(Reference.MOD_ID)
    public static FPGA3D instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{        
		// Initialize mod blocks
        ModBlocks.init();
	}
	
	@Init
    public void load(FMLInitializationEvent event)
	{
		// Register the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        
        // Initialize mod tile entities
        proxy.registerTileEntities();
    }
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}

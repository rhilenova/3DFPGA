package fpga3d;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fpga3d.block.ModBlocks;
import fpga3d.core.proxy.CommonProxy;
import fpga3d.creativetab.CreativeTabFPGA;
import fpga3d.network.PacketHandler;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
@NetworkMod(channels = {Reference.MOD_CHANNEL}, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class FPGA3D
{
    @Instance(Reference.MOD_ID)
    public static FPGA3D instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static CreativeTabs tabsFPGA = new CreativeTabFPGA(
                                                              CreativeTabs
                                                                      .getNextID(),
                                                              Reference.MOD_ID);

    private static String[] localeFiles = {"/assets/fpga3d/lang/en_US.xml"};

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Initialize mod blocks
        ModBlocks.init();

        // For every file specified in the Localization library class, load them
        // into the Language Registry
        for (String localizationFile : localeFiles)
        {
            String locale = localizationFile.substring(localizationFile
                    .lastIndexOf('/') + 1, localizationFile.lastIndexOf('.'));
            LanguageRegistry.instance().loadLocalization(localizationFile,
                                                         locale, true);
        }
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        // Register the GUI Handler
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);

        // Initialize mod tile entities
        proxy.registerTileEntities();
    }
}

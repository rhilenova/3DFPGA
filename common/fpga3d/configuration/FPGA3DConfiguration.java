package fpga3d.configuration;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import fpga3d.Reference;

import net.minecraftforge.common.Configuration;

public class FPGA3DConfiguration
{
    public static Configuration configuration;
    
    public static void init(File configFile)
    {
        configuration = new Configuration(configFile);
        
        try
        {
            configuration.load();
            
            Reference.BlockIDs.FPGA = configuration.getBlock(Reference.Strings.FPGA_NAME, Reference.BlockIDs.FPGA_DEFAULT).getInt(Reference.BlockIDs.FPGA_DEFAULT);
        }
        catch (Exception e)
        {
            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its configuration");
        }
        finally
        {
            configuration.save();
        }
    }
    
    public static void set(String categoryName, String propertyName, String newValue)
    {
        configuration.load();
        
        if (configuration.getCategoryNames().contains(categoryName))
        {
            if (configuration.getCategory(categoryName).containsKey(propertyName))
            {
                configuration.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        
        configuration.save();
    }
}

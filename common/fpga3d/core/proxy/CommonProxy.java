package fpga3d.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import fpga3d.Reference;
import fpga3d.client.gui.GuiScreenFPGA;
import fpga3d.inventory.ContainerFPGA;
import fpga3d.tileentity.TileEntityFPGA;

public class CommonProxy implements IGuiHandler
{
    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityFPGA.class,
                                        Reference.Strings.FPGA_NAME);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        if (ID == Reference.GuiIDs.FPGA)
        {
            return new ContainerFPGA();
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        if (ID == Reference.GuiIDs.FPGA)
        {
            TileEntityFPGA tile = (TileEntityFPGA) world.getBlockTileEntity(x,
                                                                            y,
                                                                            z);
            return new GuiScreenFPGA(tile);
        }
        return null;
    }
}

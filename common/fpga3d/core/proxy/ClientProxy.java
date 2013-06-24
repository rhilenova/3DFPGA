package fpga3d.core.proxy;

import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import fpga3d.tileentity.TileEntityFPGA;

public class ClientProxy extends CommonProxy
{
	@Override
	public void handleTileEntityPacket(int x, int y, int z, int dim, int[] connections)
	{
		TileEntity tile = null;
		// TODO Why doesn't the CommonProxy run for this case?
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			tile = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dim).getBlockTileEntity(x, y, z);
		}
		else
		{
			if (FMLClientHandler.instance().getClient().theWorld.getWorldInfo().getDimension() == dim)
			{
				tile = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(x, y, z);
			}
		}
		
		if (tile == null || !(tile instanceof TileEntityFPGA)) {
			return;
		}
		
		TileEntityFPGA tile_fpga = (TileEntityFPGA)tile;
		tile_fpga.setConnections(connections);
	}
}

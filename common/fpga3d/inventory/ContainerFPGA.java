package fpga3d.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerFPGA extends Container
{
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}

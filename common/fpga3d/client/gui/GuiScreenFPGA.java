package fpga3d.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiScreenFPGA extends GuiScreen
{
    /** The X size of the FPGA window in pixels. */
    protected int xSize = 176;

    /** The Y size of the FPGA window in pixels. */
    protected int ySize = 166;
    
	public GuiScreenFPGA(EntityPlayer player, World world, int x, int y, int z)
	{
	}

	/**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
    	this.mc.renderEngine.bindTexture("/gui/hopper.png");
    	int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    	super.drawScreen(par1, par2, par3);
    }
}

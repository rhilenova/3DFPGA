package fpga3d.client.gui;

import fpga3d.Reference;
import fpga3d.inventory.ContainerFPGA;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiScreenFPGA extends GuiContainer
{
    
	public GuiScreenFPGA()
	{
		super(new ContainerFPGA());
		this.xSize = 223;
		this.ySize = 186;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
	{
    	fontRenderer.drawString("LUT", 72, 10, 4210752);
    	fontRenderer.drawString("000", 50, 24, 4210752);
    	fontRenderer.drawString("001", 50, 34, 4210752);
    	fontRenderer.drawString("010", 50, 44, 4210752);
    	fontRenderer.drawString("011", 50, 54, 4210752);
    	fontRenderer.drawString("100", 50, 64, 4210752);
    	fontRenderer.drawString("101", 50, 74, 4210752);
    	fontRenderer.drawString("110", 50, 84, 4210752);
    	fontRenderer.drawString("111", 50, 94, 4210752);
    	
    	fontRenderer.drawString("00", 100, 24, 4210752);
    	fontRenderer.drawString("00", 100, 34, 4210752);
    	fontRenderer.drawString("00", 100, 44, 4210752);
    	fontRenderer.drawString("00", 100, 54, 4210752);
    	fontRenderer.drawString("00", 100, 64, 4210752);
    	fontRenderer.drawString("00", 100, 74, 4210752);
    	fontRenderer.drawString("00", 100, 84, 4210752);
    	fontRenderer.drawString("00", 100, 94, 4210752);
    	
    	fontRenderer.drawString("D", 149, 117, 4210752);
    	fontRenderer.drawString("Clk", 149, 127, 4210752);
    	fontRenderer.drawString("Q", 169, 117, 4210752);
    	fontRenderer.drawString("Q'", 169, 127, 4210752);
    	
    	fontRenderer.drawString("D", 149, 150, 4210752);
    	fontRenderer.drawString("Clk", 149, 160, 4210752);
    	fontRenderer.drawString("Q", 169, 150, 4210752);
    	fontRenderer.drawString("Q'", 169, 160, 4210752);
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
	{
    	this.mc.renderEngine.bindTexture(Reference.Textures.GUI_FPGA);
    	int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}

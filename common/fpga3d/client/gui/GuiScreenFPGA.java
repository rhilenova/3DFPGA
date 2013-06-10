package fpga3d.client.gui;

import fpga3d.Reference;
import fpga3d.inventory.ContainerFPGA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiScreenFPGA extends GuiContainer
{
    
	public GuiScreenFPGA()
	{
		super(new ContainerFPGA());
		this.xSize = 223;
		this.ySize = 186;
	}
	
	/**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
	public void initGui()
    {
        super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        int[][] button_locs =
        	{{13, 114}, {13, 122}, {13, 130}, {13, 138}, {13, 146}, {13, 154},
        	{36, 45}, {36, 53}, {36, 61},
        	{119, 49}, {119, 57},
        	{139, 118}, {139, 126}, {139, 151}, {139, 159},
        	{180, 118}, {180, 126}, {180, 151}, {180, 159},
        	{204, 26}, {204, 34}, {204, 42}, {204, 50}, {204, 58}, {204, 66}};
        for (int x = 0; x < button_locs.length; ++x)
        {
        	GuiButton temp =new GuiButtonFPGAInput(x, k + button_locs[x][0],
        										   l + button_locs[x][1], 6, 6, null);
        	temp.drawButton = false;
        	buttonList.add(temp);
        }
    }
    
    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	System.out.println("Button " + par1GuiButton.id);
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

package fpga3d.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonFPGAInput extends GuiButton
{
	public GuiButtonFPGAInput(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	/**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        return this.enabled && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
    }
}

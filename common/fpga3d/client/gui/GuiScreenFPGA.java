package fpga3d.client.gui;

import fpga3d.Reference;
import fpga3d.inventory.ContainerFPGA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiScreenFPGA extends GuiContainer
{
    int[] number_vals = {0, 0, 0, 0, 0, 0, 0, 0};
    int num_endpoints;
    int num_numbers;
    
    int last_endpoint;
    
    int[] lut_inputs = {-1, -1, -1};
    int[] ff_inputs = {-1, -1, -1, -1};
    int[] outputs = {-1, -1, -1, -1, -1, -1};
    int[] input_colors = {-2236963, -2392770, -5025604, -9729335, -5134809, -12472776,
    					  0, 0, 0, 0, 0, 0, 0, // Spaces for LUT and FF inputs
    					  -3111783, -12566464, -6643295, -13734263, -8503883, -13748083,
    					  -11587041, -13285861, -6933456, -15133162};
    
    final static int LUT1 = 21;
    final static int LUT2 = 25;
    final static int LUT3 = 28;
    
	public GuiScreenFPGA()
	{
		super(new ContainerFPGA());
		this.xSize = 235;
		this.ySize = 186;
		last_endpoint = -1;
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
        int[][] endpoint_locs =
        	// Inputs
        	{{13, 114}, {13, 122}, {13, 130}, {13, 138}, {13, 146}, {13, 154},
        	// LUT Inputs
        	{36, 45}, {36, 53}, {36, 61},
        	// FF Inputs
        	{143, 118}, {143, 126}, {143, 151}, {143, 159},
        	// LUT Outputs
        	{119, 49}, {119, 57},
        	// FF Outputs
        	{184, 118}, {184, 126}, {184, 151}, {184, 159},
        	// Outputs
        	{216, 26}, {216, 34}, {216, 42}, {216, 50}, {216, 58}, {216, 66}};
        num_endpoints = endpoint_locs.length;
        for (int x = 0; x < endpoint_locs.length; ++x)
        {
        	GuiButton temp = new GuiButtonFPGA(x, k + endpoint_locs[x][0],
        								       l + endpoint_locs[x][1], 6, 6, null);
        	temp.drawButton = false;
        	buttonList.add(temp);
        }
        int[][] number_locs =
        	// LUT
        	{{100, 24}, {106, 24},
        	 {100, 34}, {106, 34},
           	 {100, 44}, {106, 44},
           	 {100, 54}, {106, 54},
           	 {100, 64}, {106, 64},
           	 {100, 74}, {106, 74},
           	 {100, 84}, {106, 84},
           	 {100, 94}, {106, 94}};
        num_numbers = number_locs.length;
        for (int x = 0; x < number_locs.length; ++x)
        {
        	GuiButton temp = new GuiButtonFPGA(x + endpoint_locs.length, k + number_locs[x][0],
				       						   l + number_locs[x][1], 5, 7, null);
        	temp.drawButton = false;
        	buttonList.add(temp);
        }
    }
    
    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	int ID = par1GuiButton.id;
    	if (ID >= 0 && ID < num_endpoints)
    	{
    		if (last_endpoint == -1)
    		{
    			last_endpoint = ID;
    		}
    		else
    		{
    			// Inputs
    			if (last_endpoint < 6)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					last_endpoint = -1;
    					for (int x = 0; x < 3; ++x)
    					{
    						if (lut_inputs[x] == ID) lut_inputs[x] = -1;
    						if (ff_inputs[x] == ID) ff_inputs[x] = -1;
    					}
    					if (ff_inputs[3] == ID) ff_inputs[3] = -1;
    				}
    				// New first click
    				else if (ID <= 5 || ID >= 13)
    				{
    					last_endpoint = ID;
    				}
    				// LUT inputs
    				else if (ID >= 6 && ID <= 8)
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					lut_inputs[ID-6] = last_endpoint;
    					last_endpoint = -1;
    				}
    				// FF inputs
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					ff_inputs[ID-9] = last_endpoint;
    					last_endpoint = -1;
    				}
    			}
    			// LUT Inputs
    			else if (last_endpoint >= 6 && last_endpoint <= 8)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					lut_inputs[ID-6] = -1;
    					last_endpoint = -1;
    				}
    				// New first click
    				else if (ID >= 6)
    				{
    					last_endpoint = ID;
    				}
    				// Connect to input
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					lut_inputs[last_endpoint-6] = ID;
    					last_endpoint = -1;
    				}
    			}
    			// FF Inputs
    			else if (last_endpoint >= 9 && last_endpoint <= 12)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					ff_inputs[ID-9] = -1;
    					last_endpoint = -1;
    				}
    				// New first click
    				else if ((6 <= ID && ID <= 12) || ID >= 15)
    				{
    					last_endpoint = ID;
    				}
    				// Connect
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					ff_inputs[last_endpoint-9] = ID;
    					last_endpoint = -1;
    				}
    			}
    			// LUT Outputs
    			else if (last_endpoint >= 13 && last_endpoint <= 14)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					last_endpoint = -1;
    					for (int x = 0; x < 4; ++x)
    					{
    						if (ff_inputs[x] == ID) ff_inputs[x] = -1;
    					}
    				}
    				// New first click
    				else if ((13 <= ID && ID <= 18) || ID <= 8)
    				{
    					last_endpoint = ID;
    				}
    				// FF Inputs
    				else if (9 <= ID && ID <= 12)
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					ff_inputs[ID-9] = last_endpoint;
    					last_endpoint = -1;
    				}
    				// Outputs
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					outputs[ID-19] = last_endpoint;
    					last_endpoint = -1;
    				}
    			}
    			// FF Outputs
    			else if (last_endpoint >= 15 && last_endpoint <= 18)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					last_endpoint = -1;
    					for (int x = 0; x < 6; ++x)
    					{
    						if (outputs[x] == ID) outputs[x] = -1;
    					}
    				}
    				// New first click
    				else if (ID <= 18)
    				{
    					last_endpoint = ID;
    				}
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					outputs[ID-19] = last_endpoint;
    					last_endpoint = -1;
    				}
    			}
    			// Outputs
    			else if (last_endpoint >= 19)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					outputs[ID-19] = -1;
    					last_endpoint = -1;
    				}
    				// New first click
    				else if (ID >= 19 || ID <= 12)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					outputs[last_endpoint-19] = ID;
    					last_endpoint = -1;
    				}
    			}
    		}
    	}
    	else if (ID >= num_endpoints && ID < num_endpoints + num_numbers)
    	{
        	int number_id = (ID - num_endpoints) / 2;
        	int number_mod = (ID - num_endpoints) % 2;
        	number_vals[number_id] = number_vals[number_id] ^ (0x10 >> (4 * number_mod));
    	}
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
    	
    	for (int idx = 0; idx < 8; ++idx)
    	{
        	fontRenderer.drawString(String.format("%02X", number_vals[idx]), 100, 24 + (10 * idx), 4210752);
    	}
    	
    	fontRenderer.drawString("D", 152, 117, 4210752);
    	fontRenderer.drawString("Clk", 152, 127, 4210752);
    	fontRenderer.drawString("Q", 172, 117, 4210752);
    	fontRenderer.drawString("Q'", 172, 127, 4210752);
    	
    	fontRenderer.drawString("D", 152, 150, 4210752);
    	fontRenderer.drawString("Clk", 152, 160, 4210752);
    	fontRenderer.drawString("Q", 172, 150, 4210752);
    	fontRenderer.drawString("Q'", 172, 160, 4210752);
    	
		if (lut_inputs[0] >= 0)
		{
	    	drawRect(21, 47, 23, 118 + (8 * lut_inputs[0]), input_colors[lut_inputs[0]]);
	    	drawRect(23, 47, 36, 49, input_colors[lut_inputs[0]]);
	    	drawRect(19, 116 + (8 * lut_inputs[0]), 21, 118 + (8 * lut_inputs[0]), input_colors[lut_inputs[0]]);
	    	drawRect(37, 47, 41, 49, input_colors[lut_inputs[0]]);
	    	drawRect(38, 46, 40, 50, input_colors[lut_inputs[0]]);
		}
		
		if (lut_inputs[1] >= 0)
		{
	    	drawRect(25, 55, 27, 118 + (8 * lut_inputs[1]), input_colors[lut_inputs[1]]);
	    	drawRect(25, 55, 36, 57, input_colors[lut_inputs[1]]);
	    	drawRect(19, 116 + (8 * lut_inputs[1]), 25, 118 + (8 * lut_inputs[1]), input_colors[lut_inputs[1]]);
	    	drawRect(37, 55, 41, 57, input_colors[lut_inputs[1]]);
	    	drawRect(38, 54, 40, 58, input_colors[lut_inputs[1]]);
		}
		
		if (lut_inputs[2] >= 0)
		{
	    	drawRect(29, 63, 31, 118 + (8 * lut_inputs[2]), input_colors[lut_inputs[2]]);
	    	drawRect(29, 63, 36, 65, input_colors[lut_inputs[2]]);
	    	drawRect(19, 116 + (8 * lut_inputs[2]), 29, 118 + (8 * lut_inputs[2]), input_colors[lut_inputs[2]]);
	    	drawRect(37, 63, 41, 65, input_colors[lut_inputs[2]]);
	    	drawRect(38, 62, 40, 66, input_colors[lut_inputs[2]]);
		}
		
		if (ff_inputs[0] >= 0)
		{
			drawRect(141, 120, 143, 122, input_colors[ff_inputs[0]]);
			drawRect(144, 120, 148, 122, input_colors[ff_inputs[0]]);
	    	drawRect(145, 119, 147, 123, input_colors[ff_inputs[0]]);
	    	if (ff_inputs[0] <= 5)
	    	{
	    		drawRect(19, 116 + (8 * ff_inputs[0]), 141, 118 + (8 * ff_inputs[0]), input_colors[ff_inputs[0]]);
	    		if (116 + (8 * ff_inputs[0]) > 120)
	    		{
	    	    	drawRect(139, 120, 141, 118 + (8 * ff_inputs[0]), input_colors[ff_inputs[0]]);
	    		}
	    		else
	    		{
	    	    	drawRect(139, 116 + (8 * ff_inputs[0]), 141, 122, input_colors[ff_inputs[0]]);
	    		}
	    	}
		}
		
		if (ff_inputs[1] >= 0)
		{
			drawRect(137, 128, 143, 130, input_colors[ff_inputs[1]]);
			drawRect(144, 128, 148, 130, input_colors[ff_inputs[1]]);
	    	drawRect(145, 127, 147, 131, input_colors[ff_inputs[1]]);
	    	if (ff_inputs[1] <= 5)
	    	{
	    		drawRect(19, 116 + (8 * ff_inputs[1]), 135, 118 + (8 * ff_inputs[1]), input_colors[ff_inputs[1]]);
	    		if (116 + (8 * ff_inputs[1]) > 128)
	    		{
	    	    	drawRect(135, 128, 137, 118 + (8 * ff_inputs[1]), input_colors[ff_inputs[1]]);
	    		}
	    		else
	    		{
	    	    	drawRect(135, 116 + (8 * ff_inputs[1]), 137, 130, input_colors[ff_inputs[1]]);
	    		}
	    	}
		}
		
		if (ff_inputs[2] >= 0)
		{
			drawRect(133, 153, 143, 155, input_colors[ff_inputs[2]]);
			drawRect(144, 153, 148, 155, input_colors[ff_inputs[2]]);
	    	drawRect(145, 152, 147, 156, input_colors[ff_inputs[2]]);
	    	if (ff_inputs[2] <= 5)
	    	{
	    		drawRect(19, 116 + (8 * ff_inputs[2]), 131, 118 + (8 * ff_inputs[2]), input_colors[ff_inputs[2]]);
	    		if (116 + (8 * ff_inputs[2]) > 153)
	    		{
	    	    	drawRect(131, 153, 133, 118 + (8 * ff_inputs[2]), input_colors[ff_inputs[2]]);
	    		}
	    		else
	    		{
	    	    	drawRect(131, 116 + (8 * ff_inputs[2]), 133, 155, input_colors[ff_inputs[2]]);
	    		}
	    	}
		}
		
		if (ff_inputs[3] >= 0)
		{
			drawRect(129, 161, 143, 163, input_colors[ff_inputs[3]]);
			drawRect(144, 161, 148, 163, input_colors[ff_inputs[3]]);
	    	drawRect(145, 160, 147, 164, input_colors[ff_inputs[3]]);
	    	if (ff_inputs[3] <= 5)
	    	{
	    		drawRect(19, 116 + (8 * ff_inputs[3]), 127, 118 + (8 * ff_inputs[3]), input_colors[ff_inputs[3]]);
	    		if (116 + (8 * ff_inputs[3]) > 161)
	    		{
	    	    	drawRect(127, 161, 129, 118 + (8 * ff_inputs[3]), input_colors[ff_inputs[3]]);
	    		}
	    		else
	    		{
	    	    	drawRect(127, 116 + (8 * ff_inputs[3]), 129, 163, input_colors[ff_inputs[3]]);
	    		}
	    	}
		}
		
		if (outputs[0] >= 0)
		{
			drawRect(194, 28, 216, 30, input_colors[outputs[0]]);
		}
		
		if (outputs[1] >= 0)
		{
			drawRect(198, 36, 216, 38, input_colors[outputs[1]]);
		}
		
		if (outputs[2] >= 0)
		{
			drawRect(202, 44, 216, 46, input_colors[outputs[2]]);
		}
		
		if (outputs[3] >= 0)
		{
			drawRect(206, 52, 216, 54, input_colors[outputs[3]]);
		}
		
		if (outputs[4] >= 0)
		{
			drawRect(210, 60, 216, 62, input_colors[outputs[4]]);
		}
		
		if (outputs[5] >= 0)
		{
			drawRect(214, 68, 216, 70, input_colors[outputs[5]]);
		}
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

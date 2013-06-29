package fpga3d.client.gui;

import java.util.Arrays;

import cpw.mods.fml.common.network.PacketDispatcher;

import fpga3d.Reference;
import fpga3d.inventory.ContainerFPGA;
import fpga3d.tileentity.TileEntityFPGA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiScreenFPGA extends GuiContainer
{
    class routing_struct
    {
    	routing_struct(int _l, int _t, int _r)
    	{
    		left = _l;
    		top = _t;
    		right = _r;
    	}
    	public int left;
    	public int top;
    	public int right;
    }

    routing_struct[] routing =
    {
    	// Inputs
		new routing_struct(19, 116, -1),
		new routing_struct(19, 124, -1),
		new routing_struct(19, 132, -1),
		new routing_struct(19, 140, -1),
		new routing_struct(19, 148, -1),
		new routing_struct(19, 156, -1),
		// LUT Inputs
		new routing_struct(21, 47, 36),
		new routing_struct(25, 55, 36),
		new routing_struct(29, 63, 36),
		// FF Inputs
		new routing_struct(139, 120, 143),
		new routing_struct(135, 128, 143),
		new routing_struct(131, 153, 143),
		new routing_struct(127, 161, 143),
		// LUT Outputs
		new routing_struct(125, 51, -1),
		new routing_struct(125, 59, -1),
		// FF Outputs
		new routing_struct(190, 120, -1),
		new routing_struct(190, 128, -1),
		new routing_struct(190, 153, -1),
		new routing_struct(190, 161, -1),
		// Outputs
		new routing_struct(192, 31, 216),
		new routing_struct(196, 39, 216),
		new routing_struct(200, 47, 216),
		new routing_struct(204, 55, 216),
		new routing_struct(208, 63, 216),
		new routing_struct(212, 71, 216)
	};

    int[] array_idx = {6, 7, 8, 9, 10, 11, 12, 19, 20, 21, 22, 23, 24};

    int num_endpoints;
    int num_numbers;
    
    int last_endpoint;

    int[] input_colors = {-2236963, -2392770, -5025604, -9729335, -5134809, -12472776,
    					  0, 0, 0, 0, 0, 0, 0, // Spaces for LUT and FF inputs
    					  -3111783, -12566464, -6643295, -13734263, -8503883, -13748083,
    					  -11587041, -13285861, -6933456, -15133162};
    
    TileEntityFPGA tile;
    
    /**
     * Create the GUI.
     */
	public GuiScreenFPGA(TileEntityFPGA tile)
	{
		super(new ContainerFPGA());
		this.tile = tile;
		this.xSize = 235;
		this.ySize = 186;
		last_endpoint = -1;
	}
	
	/**
     * Adds the buttons (and other controls) to the screen in question.
     * TODO cleanup?
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
        	{216, 29}, {216, 37}, {216, 45}, {216, 53}, {216, 61}, {216, 69}};
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
    	// Handle routing control
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
    					for (int x = 0; x < tile.connections.length; ++x)
    					{
    						if (tile.connections[x] == ID) tile.connections[x] = -1;
    					}
    				}
    				// New first click
    				else if (ID <= 5 || ID >= 13)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = last_endpoint;
    					last_endpoint = -1;
    				}
    			}
    			// LUT Inputs
    			else if (last_endpoint >= 6 && last_endpoint <= 8)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = -1;
    					last_endpoint = -1;
    				}
    				// New first click
    				else if (ID >= 6)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					int idx = Arrays.binarySearch(array_idx, last_endpoint);
    					tile.connections[idx] = ID;
    					last_endpoint = -1;
    				}
    			}
    			// FF Inputs
    			else if (last_endpoint >= 9 && last_endpoint <= 12)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = -1;
    					last_endpoint = -1;
    				}
    				// New first click
    				else if ((6 <= ID && ID <= 12) || ID >= 15)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					int idx = Arrays.binarySearch(array_idx, last_endpoint);
    					tile.connections[idx] = ID;
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
    					for (int x = 0; x < tile.connections.length; ++x)
    					{
    						if (tile.connections[x] == ID) tile.connections[x] = -1;
    					}
    				}
    				// New first click
    				else if ((13 <= ID && ID <= 18) || ID <= 8)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = last_endpoint;
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
    					for (int x = 0; x < tile.connections.length; ++x)
    					{
    						if (tile.connections[x] == ID) tile.connections[x] = -1;
    					}
    				}
    				// New first click
    				else if (ID <= 18)
    				{
    					last_endpoint = ID;
    				}
    				// Connection
    				else
    				{
    					System.out.println("Connected " + last_endpoint + " to " + ID);
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = last_endpoint;
    					last_endpoint = -1;
    				}
    			}
    			// Outputs
    			else if (last_endpoint >= 19)
    			{
    				// Clear connections
    				if (ID == last_endpoint)
    				{
    					int idx = Arrays.binarySearch(array_idx, ID);
    					tile.connections[idx] = -1;
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
    					int idx = Arrays.binarySearch(array_idx, last_endpoint);
    					tile.connections[idx] = ID;
    					last_endpoint = -1;
    				}
    			}
    		}

    		if (last_endpoint == -1)
    		{
	        	PacketDispatcher.sendPacketToServer(tile.getDescriptionPacket());
    		}
    	}
    	// Handle LUT control
    	else if (ID >= num_endpoints && ID < num_endpoints + num_numbers)
    	{
        	int number_id = (ID - num_endpoints) / 2;
        	int number_mod = (ID - num_endpoints) % 2;
        	tile.lut_vals[number_id] = tile.lut_vals[number_id] ^ (0x10 >> (4 * number_mod));
        	PacketDispatcher.sendPacketToServer(tile.getDescriptionPacket());
    	}
    }

	@Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		// Draw LUT static text
    	fontRenderer.drawString("LUT", 72, 10, 4210752);
    	fontRenderer.drawString("000", 50, 24, 4210752);
    	fontRenderer.drawString("001", 50, 34, 4210752);
    	fontRenderer.drawString("010", 50, 44, 4210752);
    	fontRenderer.drawString("011", 50, 54, 4210752);
    	fontRenderer.drawString("100", 50, 64, 4210752);
    	fontRenderer.drawString("101", 50, 74, 4210752);
    	fontRenderer.drawString("110", 50, 84, 4210752);
    	fontRenderer.drawString("111", 50, 94, 4210752);
    	
		// Draw LUT numbers
    	for (int idx = 0; idx < 8; ++idx)
    	{
        	fontRenderer.drawString(String.format("%02X", tile.lut_vals[idx]), 100, 24 + (10 * idx), 4210752);
    	}
    	
    	// Draw FF static text
    	fontRenderer.drawString("D", 152, 117, 4210752);
    	fontRenderer.drawString("Clk", 152, 127, 4210752);
    	fontRenderer.drawString("Q", 172, 117, 4210752);
    	fontRenderer.drawString("Q'", 172, 127, 4210752);
    	
    	fontRenderer.drawString("D", 152, 150, 4210752);
    	fontRenderer.drawString("Clk", 152, 160, 4210752);
    	fontRenderer.drawString("Q", 172, 150, 4210752);
    	fontRenderer.drawString("Q'", 172, 160, 4210752);

    	// Draw routing
    	for (int connection = 0; connection < tile.connections.length; ++connection)
    	{
    		int from_id = tile.connections[connection];
    		int to_id = array_idx[connection];
    		if (from_id >= 0)
    		{
    			// Draw start bar
    			drawRect(routing[from_id].left, routing[from_id].top, routing[to_id].left, routing[from_id].top + 2, input_colors[from_id]);
    			// Draw end bar
    			drawRect(routing[to_id].left, routing[to_id].top, routing[to_id].right, routing[to_id].top + 2, input_colors[from_id]);
    			// Draw connector
    			if (routing[from_id].top > routing[to_id].top)
    			{
    				drawRect(routing[to_id].left, routing[to_id].top, routing[to_id].left + 2, routing[from_id].top + 2, input_colors[from_id]);
    			}
    			else
    			{
    				drawRect(routing[to_id].left, routing[from_id].top, routing[to_id].left + 2, routing[to_id].top + 2, input_colors[from_id]);
    			}
    			// Color endpoint
    			if (to_id <= 18)
    			{
					drawRect(routing[to_id].right + 1, routing[to_id].top, routing[to_id].right + 5, routing[to_id].top + 2, input_colors[from_id]);
					drawRect(routing[to_id].right + 2, routing[to_id].top - 1, routing[to_id].right + 4, routing[to_id].top + 3, input_colors[from_id]);
    			}
    		}
    	}
	}
	
	// Draw background image
	@Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
	{
    	this.mc.renderEngine.bindTexture(Reference.Textures.GUI_FPGA);
    	int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}

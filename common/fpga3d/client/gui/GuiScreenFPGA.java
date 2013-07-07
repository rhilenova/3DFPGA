package fpga3d.client.gui;

import java.util.Arrays;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import cpw.mods.fml.common.network.PacketDispatcher;
import fpga3d.Reference;
import fpga3d.inventory.ContainerFPGA;
import fpga3d.tileentity.TileEntityFPGA;

public class GuiScreenFPGA extends GuiContainer
{
    class routing_struct
    {
        routing_struct(int _l, int _t, int _r)
        {
            this.left = _l;
            this.top = _t;
            this.right = _r;
        }

        public int left;
        public int top;
        public int right;
    }

    routing_struct[] routing = {
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
                                new routing_struct(212, 71, 216)};

    int last_endpoint;

    int[] input_colors = {-2236963, -2392770, -5025604, -9729335, -5134809,
                          -12472776, 0, 0, 0, 0, 0, 0,
                          0, // Spaces for LUT and FF inputs
                          -3111783, -12566464, -6643295, -13734263, -8503883,
                          -13748083, -11587041, -13285861, -6933456, -15133162};

    int[] input_connections = {6, 7, 8, 9, 10, 11, 12};
    int[] lut_input_connections = {0, 1, 2, 3, 4, 5};
    int[] ff_input_connections = {0, 1, 2, 3, 4, 5, 13, 14};
    int[] lut_output_connections = {9, 10, 11, 12, 19, 20, 21, 22, 23, 24};
    int[] ff_output_connections = {19, 20, 21, 22, 23, 24};
    int[] output_connections = {13, 14, 15, 16, 17, 18};

    boolean[] is_connection = new boolean[Reference.Constants.NUM_ENDPOINTS];
    int[] connection_endpoints = {6, 7, 8, 9, 10, 11, 12, 19, 20, 21, 22, 23,
                                  24};
    GuiButton hard_decision;

    int[][] legal_connections = {this.input_connections,
                                 this.input_connections,
                                 this.input_connections,
                                 this.input_connections,
                                 this.input_connections,
                                 this.input_connections,
                                 this.lut_input_connections,
                                 this.lut_input_connections,
                                 this.lut_input_connections,
                                 this.ff_input_connections,
                                 this.ff_input_connections,
                                 this.ff_input_connections,
                                 this.ff_input_connections,
                                 this.lut_output_connections,
                                 this.lut_output_connections,
                                 this.ff_output_connections,
                                 this.ff_output_connections,
                                 this.ff_output_connections,
                                 this.ff_output_connections,
                                 this.output_connections,
                                 this.output_connections,
                                 this.output_connections,
                                 this.output_connections,
                                 this.output_connections,
                                 this.output_connections};

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
        this.last_endpoint = -1;
        Arrays.fill(this.is_connection, false);
        for (int idx : this.connection_endpoints)
        {
            this.is_connection[idx] = true;
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     * TODO cleanup?
     */
    @Override
    @SuppressWarnings("unchecked")
    public void initGui()
    {
        super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        int[][] endpoint_locs =
        // Inputs
        { {13, 114}, {13, 122}, {13, 130}, {13, 138}, {13, 146}, {13, 154},
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
        for (int x = 0; x < endpoint_locs.length; ++x)
        {
            GuiButton temp = new GuiButtonFPGA(x, k + endpoint_locs[x][0],
                                               l + endpoint_locs[x][1], 6, 6,
                                               null);
            temp.drawButton = false;
            this.buttonList.add(temp);
        }
        int[][] number_locs =
        // LUT
        { {100, 24}, {106, 24}, {100, 34}, {106, 34}, {100, 44}, {106, 44},
         {100, 54}, {106, 54}, {100, 64}, {106, 64}, {100, 74}, {106, 74},
         {100, 84}, {106, 84}, {100, 94}, {106, 94}};
        for (int x = 0; x < number_locs.length; ++x)
        {
            GuiButton temp = new GuiButtonFPGA(x + endpoint_locs.length,
                                               k + number_locs[x][0],
                                               l + number_locs[x][1], 5, 7,
                                               null);
            temp.drawButton = false;
            this.buttonList.add(temp);
        }
        hard_decision = new GuiButton(100, k + 121, l + 5, 109, 20,
                                                "Soft Decision");
        this.buttonList.add(hard_decision);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of
     * ActionListener.actionPerformed(ActionEvent e).
     */
    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        int ID = par1GuiButton.id;
        System.out.println("Pushed: " + ID);
        // Handle routing control
        if (ID >= 0 && ID < Reference.Constants.NUM_ENDPOINTS)
        {
            if (this.last_endpoint == -1)
            {
                this.last_endpoint = ID;
            }
            else
            {
                // Clear connections
                if (ID == this.last_endpoint)
                {
                    for (int x = 0; x < this.tile.connections.length; ++x)
                    {
                        if (this.tile.connections[x] == ID)
                        {
                            this.tile.connections[x] = -1;
                        }
                    }
                    this.tile.connections[ID] = -1;
                    this.last_endpoint = -1;
                }
                // Make connection
                else if (Arrays
                        .binarySearch(this.legal_connections[this.last_endpoint],
                                      ID) >= 0)
                {
                    System.out.println("Connected " + this.last_endpoint +
                                       " to " + ID);
                    this.tile.connections[ID] = this.last_endpoint;
                    this.tile.connections[this.last_endpoint] = ID;
                    this.last_endpoint = -1;
                }
                // New first click
                else
                {
                    this.last_endpoint = ID;
                }
            }

            if (this.last_endpoint == -1)
            {
                PacketDispatcher.sendPacketToServer(this.tile
                        .getDescriptionPacket());
            }
        }
        // Handle LUT control
        else if (ID >= Reference.Constants.NUM_ENDPOINTS &&
                 ID < Reference.Constants.NUM_ENDPOINTS +
                      Reference.Constants.LUT_SIZE * 2)
        {
            int number_id = (ID - Reference.Constants.NUM_ENDPOINTS) / 2;
            int number_mod = (ID - Reference.Constants.NUM_ENDPOINTS) % 2;
            this.tile.lut_vals[number_id] = this.tile.lut_vals[number_id] ^
                                            (0x10 >> (4 * number_mod));
            PacketDispatcher.sendPacketToServer(this.tile
                    .getDescriptionPacket());
        }
        // Handle decision button
        else if (ID == 100)
        {
            this.tile.is_hard_decision = !this.tile.is_hard_decision;
            PacketDispatcher.sendPacketToServer(this.tile
                                                .getDescriptionPacket());
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        if (this.tile.is_hard_decision)
        {
            hard_decision.displayString = "Hard Decision";
        }
        else
        {
            hard_decision.displayString = "Soft Decision";
        }
        
        // Draw LUT static text
        this.fontRenderer.drawString("LUT", 72, 10, 4210752);
        this.fontRenderer.drawString("000", 50, 24, 4210752);
        this.fontRenderer.drawString("001", 50, 34, 4210752);
        this.fontRenderer.drawString("010", 50, 44, 4210752);
        this.fontRenderer.drawString("011", 50, 54, 4210752);
        this.fontRenderer.drawString("100", 50, 64, 4210752);
        this.fontRenderer.drawString("101", 50, 74, 4210752);
        this.fontRenderer.drawString("110", 50, 84, 4210752);
        this.fontRenderer.drawString("111", 50, 94, 4210752);

        // Draw LUT numbers
        for (int idx = 0; idx < 8; ++idx)
        {
            this.fontRenderer
                    .drawString(String.format("%02X", this.tile.lut_vals[idx]),
                                100, 24 + (10 * idx), 4210752);
        }

        // Draw FF static text
        this.fontRenderer.drawString("D", 152, 117, 4210752);
        this.fontRenderer.drawString("Clk", 152, 127, 4210752);
        this.fontRenderer.drawString("Q", 172, 117, 4210752);
        this.fontRenderer.drawString("Q'", 172, 127, 4210752);

        this.fontRenderer.drawString("D", 152, 150, 4210752);
        this.fontRenderer.drawString("Clk", 152, 160, 4210752);
        this.fontRenderer.drawString("Q", 172, 150, 4210752);
        this.fontRenderer.drawString("Q'", 172, 160, 4210752);

        // Draw routing
        for (int connection = 0; connection < this.tile.connections.length; ++connection)
        {
            if (this.is_connection[connection])
            {
                int from_id = this.tile.connections[connection];
                int to_id = connection;
                if (from_id >= 0)
                {
                    // Draw start bar
                    drawRect(this.routing[from_id].left,
                             this.routing[from_id].top,
                             this.routing[to_id].left,
                             this.routing[from_id].top + 2,
                             this.input_colors[from_id]);
                    // Draw end bar
                    drawRect(this.routing[to_id].left, this.routing[to_id].top,
                             this.routing[to_id].right,
                             this.routing[to_id].top + 2,
                             this.input_colors[from_id]);
                    // Draw connector
                    if (this.routing[from_id].top > this.routing[to_id].top)
                    {
                        drawRect(this.routing[to_id].left,
                                 this.routing[to_id].top,
                                 this.routing[to_id].left + 2,
                                 this.routing[from_id].top + 2,
                                 this.input_colors[from_id]);
                    }
                    else
                    {
                        drawRect(this.routing[to_id].left,
                                 this.routing[from_id].top,
                                 this.routing[to_id].left + 2,
                                 this.routing[to_id].top + 2,
                                 this.input_colors[from_id]);
                    }
                    // Color endpoint
                    if (to_id <= 18)
                    {
                        drawRect(this.routing[to_id].right + 1,
                                 this.routing[to_id].top,
                                 this.routing[to_id].right + 5,
                                 this.routing[to_id].top + 2,
                                 this.input_colors[from_id]);
                        drawRect(this.routing[to_id].right + 2,
                                 this.routing[to_id].top - 1,
                                 this.routing[to_id].right + 4,
                                 this.routing[to_id].top + 3,
                                 this.input_colors[from_id]);
                    }
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

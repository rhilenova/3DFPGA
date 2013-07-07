package fpga3d.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import fpga3d.Reference;
import fpga3d.tileentity.TileEntityFPGA;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager,
                             Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals(Reference.MOD_CHANNEL))
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
            DataInputStream dis = new DataInputStream(bis);

            try
            {
                int id = dis.readInt();
                parseMessage(dis, id);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void handleFPGAMessage(DataInputStream dis, int x, int y,
                                         int z, int dim)
    {
        int[] connections = new int[Reference.Constants.NUM_ENDPOINTS];
        int[] values = new int[Reference.Constants.NUM_ENDPOINTS];
        int[] ff_vals = new int[2]; // TODO Reference
        int[] lut_vals = new int[Reference.Constants.LUT_SIZE];
        boolean is_hard_decision = false;
        
        try
        {
            for (int conn = 0; conn < connections.length; ++conn)
            {
                connections[conn] = dis.readInt();
            }
            for (int val = 0; val < values.length; ++val)
            {
                values[val] = dis.readInt();
            }
            for (int ff_val = 0; ff_val < ff_vals.length; ++ff_val)
            {
                ff_vals[ff_val] = dis.readInt();
            }
            for (int lut = 0; lut < lut_vals.length; ++lut)
            {
                lut_vals[lut] = dis.readInt();
            }
            is_hard_decision = dis.readBoolean();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        TileEntity tile = null;
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            tile = FMLCommonHandler.instance().getMinecraftServerInstance()
                    .worldServerForDimension(dim).getBlockTileEntity(x, y, z);
        }
        else
        {
            if (FMLClientHandler.instance().getClient().theWorld.getWorldInfo()
                    .getDimension() == dim)
            {
                tile = FMLClientHandler.instance().getClient().theWorld
                        .getBlockTileEntity(x, y, z);
            }
        }

        if (tile == null || !(tile instanceof TileEntityFPGA))
        {
            return;
        }

        TileEntityFPGA tile_fpga = (TileEntityFPGA) tile;
        tile_fpga.setUpdate(connections, values, ff_vals, lut_vals,
                            is_hard_decision);
    }

    public static void parseMessage(DataInputStream dis, int id)
    {
        if (id == Reference.MessageIDs.FPGA)
        {
            try
            {
                int dim = dis.readInt();
                int x = dis.readInt();
                int y = dis.readInt();
                int z = dis.readInt();

                handleFPGAMessage(dis, x, y, z, dim);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

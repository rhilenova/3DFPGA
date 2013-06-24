package fpga3d.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import fpga3d.FPGA3D;
import fpga3d.Reference;

public class PacketHandler implements IPacketHandler
{
	public void parseFPGAMessage(DataInputStream dis)
    {
		try
        {
			int dim = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			int z = dis.readInt();
			
			// TODO remove hardcoded 13
			int[] connections = new int[13];
			for (int conn = 0; conn < 13; ++conn)
			{
				connections[conn] = dis.readInt();
			}
			FPGA3D.proxy.handleTileEntityPacket(x, y, z, dim, connections);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel == Reference.MOD_CHANNEL)
		{
			System.out.println("Got packet: " + FMLCommonHandler.instance().getEffectiveSide());
			ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
			DataInputStream dis = new DataInputStream(bis);

			try
			{
			    int id = dis.readInt();
			    
			    if (id == Reference.MessageIDs.FPGA)
			    {
			        parseFPGAMessage(dis);
			    }
			    else
			    {
			        System.err.println("Unknown message received.");
			    }
			}
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
	}
}

package fpga3d.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import fpga3d.Reference;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFPGA extends TileEntity {
    public int[] connections = new int[13];
    
    public TileEntityFPGA()
    {
        Arrays.fill(connections, -1);
    }
    
    public void setConnections(int[] connections)
    {
    	this.connections = connections;
    }
    
    @Override
	public Packet getDescriptionPacket()
	{
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try
		{
		    dos.writeInt(Reference.MessageIDs.FPGA);
		    dos.writeInt(worldObj.getWorldInfo().getDimension());
		    
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			for (int x = 0; x < connections.length; ++x)
	    	{
	    		dos.writeInt(connections[x]);
	    	}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		byte[] data = bos.toByteArray();

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.MOD_CHANNEL;
		packet.data = data;
		packet.length = data.length;
		packet.isChunkDataPacket = true;

		return packet;
	}
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
    	super.writeToNBT(nbtTagCompound);
    	nbtTagCompound.setIntArray("connections", connections);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
    	super.readFromNBT(nbtTagCompound);
    	connections = nbtTagCompound.getIntArray("connections");
    }
}

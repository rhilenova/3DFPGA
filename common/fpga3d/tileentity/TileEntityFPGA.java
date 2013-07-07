package fpga3d.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import fpga3d.Reference;

public class TileEntityFPGA extends TileEntity
{
    public int[] connections = new int[Reference.Constants.NUM_ENDPOINTS];
    public int[] values = new int[Reference.Constants.NUM_ENDPOINTS];
    public int[] ff_vals = new int[2]; // TODO reference
    public int[] lut_vals = new int[Reference.Constants.LUT_SIZE];
    public boolean is_hard_decision = false;

    public TileEntityFPGA()
    {
        Arrays.fill(this.connections, -1);
        Arrays.fill(this.values, -1);
    }

    public void setUpdate(int[] connections, int[] values, int[] ff_values,
                          int[] lut_vals, boolean hard_decision)
    {
        this.connections = connections;
        this.values = values;
        this.ff_vals = ff_values;
        this.lut_vals = lut_vals;
        this.is_hard_decision = hard_decision;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
            dos.writeInt(Reference.MessageIDs.FPGA);
            dos.writeInt(this.worldObj.getWorldInfo().getDimension());

            dos.writeInt(this.xCoord);
            dos.writeInt(this.yCoord);
            dos.writeInt(this.zCoord);

            for (int connection : this.connections)
            {
                dos.writeInt(connection);
            }
            for (int value : this.values)
            {
                dos.writeInt(value);
            }
            for (int ff_val : this.ff_vals)
            {
                dos.writeInt(ff_val);
            }
            for (int val : this.lut_vals)
            {
                dos.writeInt(val);
            }
            dos.writeBoolean(this.is_hard_decision);
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
        nbtTagCompound.setIntArray("connections", this.connections);
        nbtTagCompound.setIntArray("values", this.values);
        nbtTagCompound.setIntArray("ff_vals", this.ff_vals);
        nbtTagCompound.setIntArray("lut_vals", this.lut_vals);
        nbtTagCompound.setBoolean("is_hard_decision", this.is_hard_decision);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.connections = nbtTagCompound.getIntArray("connections");
        this.values = nbtTagCompound.getIntArray("values");
        this.ff_vals = nbtTagCompound.getIntArray("ff_vals");
        this.lut_vals = nbtTagCompound.getIntArray("lut_vals");
        this.is_hard_decision = nbtTagCompound.getBoolean("is_hard_decision");
    }
}

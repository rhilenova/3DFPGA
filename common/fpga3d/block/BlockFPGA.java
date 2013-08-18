package fpga3d.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fpga3d.FPGA3D;
import fpga3d.Reference;
import fpga3d.tileentity.TileEntityFPGA;

public class BlockFPGA extends Block implements ITileEntityProvider
{
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    private int[] side_transformer = {1, 0, 3, 2, 5, 4};

    public BlockFPGA(int par1)
    {
        super(par1, Material.rock);
        this.setUnlocalizedName(Reference.Strings.FPGA_NAME);
        this.setCreativeTab(FPGA3D.tabsFPGA);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.iconArray = new Icon[6];
        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = iconRegister.registerIcon(Reference.MOD_ID
                    .toLowerCase() + ":" + this.getUnlocalizedName2() + i);
        }
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z,
                                      ForgeDirection side)
    {
        return true;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture.
     * Args: side, metadata
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return this.iconArray[par1];
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z,
                                    EntityPlayer par5EntityPlayer, int par6,
                                    float par7, float par8, float par9)
    {
        if(par5EntityPlayer.isSneaking()) return false;
        
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityFPGA tileentityfpga = (TileEntityFPGA) par1World
                    .getBlockTileEntity(x, y, z);

            if (tileentityfpga != null)
            {
                par5EntityPlayer.openGui(FPGA3D.instance,
                                         Reference.GuiIDs.FPGA, par1World, x,
                                         y, z);
            }

            return true;
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, int x, int y, int z)
    {
        par1World.scheduleBlockUpdate(x, y, z, this.blockID,
                                      this.tickRate(par1World));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z,
     * neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z,
                                      int blockID)
    {
        TileEntityFPGA tile = (TileEntityFPGA) world.getBlockTileEntity(x, y, z);
        updateTileValues(world, tile, false);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityFPGA();
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess blockAccess, int x, int y,
                                      int z, int side)
    {
        return this.isProvidingWeakPower(blockAccess, x, y, z, side);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y,
                                    int z, int side)
    {
        TileEntityFPGA tile = (TileEntityFPGA) blockAccess
                .getBlockTileEntity(x, y, z);
        if (tile.is_hard_decision &&
            tile.values[this.side_transformer[side] + 19] > 7)
        {
            return 15;
        }
        else if (!tile.is_hard_decision &&
                 tile.values[this.side_transformer[side] + 19] > 0)
        {
            return tile.values[this.side_transformer[side] + 19];
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int tickRate(World par1World)
    {
        return 40;
    }

    public void updateTileValues(World world, TileEntityFPGA tile,
                                 boolean is_tick)
    {
        // TODO Cleanup
        tile.values[0] = world
                .getIndirectPowerLevelTo(tile.xCoord, tile.yCoord - 1,
                                         tile.zCoord, this.side_transformer[0]);
        tile.values[1] = world
                .getIndirectPowerLevelTo(tile.xCoord, tile.yCoord + 1,
                                         tile.zCoord, this.side_transformer[1]);
        tile.values[2] = world
                .getIndirectPowerLevelTo(tile.xCoord, tile.yCoord,
                                         tile.zCoord - 1,
                                         this.side_transformer[2]);
        tile.values[3] = world
                .getIndirectPowerLevelTo(tile.xCoord, tile.yCoord,
                                         tile.zCoord + 1,
                                         this.side_transformer[3]);
        tile.values[4] = world
                .getIndirectPowerLevelTo(tile.xCoord - 1, tile.yCoord,
                                         tile.zCoord, this.side_transformer[4]);
        tile.values[5] = world
                .getIndirectPowerLevelTo(tile.xCoord + 1, tile.yCoord,
                                         tile.zCoord, this.side_transformer[5]);

        for (int x = 6; x <= 12; ++x)
        {
            if (tile.connections[x] >= 0)
            {
                tile.values[x] = tile.values[tile.connections[x]];
            }
            else
            {
                tile.values[x] = 0;
            }
        }

        int lut_input = 0;
        if (tile.values[6] > 7)
        {
            lut_input |= 1;
        }
        if (tile.values[7] > 7)
        {
            lut_input |= 2;
        }
        if (tile.values[8] > 7)
        {
            lut_input |= 4;
        }

        tile.values[13] = (tile.lut_vals[lut_input] & 1) == 1 ? 15 : 0;
        tile.values[14] = (tile.lut_vals[lut_input] >> 4 & 1) == 1 ? 15 : 0;

        // Only update on clock (if connected) or on tick.
        boolean update_ff0 = false;
        if (tile.connections[10] >= 0 && tile.is_hard_decision)
        {
            if (tile.last_ff_0 == 0 && tile.values[10] == 15)
            {
                update_ff0 = true;
            }
        }
        else if (tile.connections[10] >= 0 && !tile.is_hard_decision)
        {
            if (tile.last_ff_0 <= 7 && tile.values[10] > 7)
            {
                update_ff0 = true;
            }
        }
        else if (tile.connections[10] < 0 && is_tick)
        {
            update_ff0 = true;
        }

        if (update_ff0)
        {
            tile.ff_vals[0] = tile.values[9];
            tile.values[15] = tile.ff_vals[0];
            tile.values[16] = 15 - tile.ff_vals[0];
        }

        boolean update_ff1 = false;
        if (tile.connections[12] >= 0 && tile.is_hard_decision)
        {
            if (tile.last_ff_1 == 0 && tile.values[12] == 15)
            {
                update_ff1 = true;
            }
        }
        else if (tile.connections[12] >= 0 && !tile.is_hard_decision)
        {
            if (tile.last_ff_1 <= 7 && tile.values[12] > 7)
            {
                update_ff1 = true;
            }
        }
        else if (tile.connections[12] < 0 && is_tick)
        {
            update_ff1 = true;
        }

        if (update_ff1)
        {
            tile.ff_vals[1] = tile.values[11];
            tile.values[17] = tile.ff_vals[1];
            tile.values[18] = 15 - tile.ff_vals[1];
        }

        for (int x = 19; x <= 24; ++x)
        {
            if (tile.connections[x] >= 0)
            {
                tile.values[x] = tile.values[tile.connections[x]];
            }
            else
            {
                tile.values[x] = 0;
            }
        }
        
        tile.last_ff_0 = tile.values[10];
        tile.last_ff_1 = tile.values[12];
    }

    @Override
    public void updateTick(World par1World, int blockX, int blockY, int blockZ,
                           Random par5Random)
    {
        TileEntityFPGA tile = (TileEntityFPGA) par1World
                .getBlockTileEntity(blockX, blockY, blockZ);
        this.updateTileValues(par1World, tile, true);
        par1World.notifyBlocksOfNeighborChange(blockX, blockY, blockZ,
                                               this.blockID);
        par1World.scheduleBlockUpdate(blockX, blockY, blockZ, this.blockID,
                                      this.tickRate(par1World));
    }
}

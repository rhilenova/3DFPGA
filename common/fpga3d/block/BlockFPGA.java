package fpga3d.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fpga3d.FPGA3D;
import fpga3d.Reference;
import fpga3d.tileentity.TileEntityFPGA;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockFPGA extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
    private Icon[] iconArray;
	
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
        iconArray = new Icon[6];
		for (int i = 0; i < this.iconArray.length; ++i)
        {
			iconArray[i] = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + this.getUnlocalizedName2() + i);
        }
    }
	
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return iconArray[par1];
    }
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityFPGA tileentityfpga = (TileEntityFPGA) par1World.getBlockTileEntity(x, y, z);

            if (tileentityfpga != null)
            {
                par5EntityPlayer.openGui(FPGA3D.instance, Reference.GuiIDs.FPGA, par1World, x, y, z);
            }

            return true;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityFPGA();
	}
}

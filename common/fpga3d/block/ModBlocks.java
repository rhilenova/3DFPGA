package fpga3d.block;

import net.minecraft.block.Block;

import fpga3d.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    /* Mod block instances */
    public static Block fpga;

    public static void init()
    {
        fpga = new BlockFPGA(Reference.BlockIDs.FPGA);

        GameRegistry.registerBlock(fpga, Reference.Strings.FPGA_NAME);

        initBlockRecipes();
    }

    private static void initBlockRecipes()
    {
        //GameRegistry.addRecipe(new ItemStack(glassBell), new Object[] { "iii", "i i", "i i", Character.valueOf('i'), Block.glass });
    }
}

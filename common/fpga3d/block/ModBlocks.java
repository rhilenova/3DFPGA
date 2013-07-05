package fpga3d.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import fpga3d.Reference;

public class ModBlocks
{

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
        GameRegistry.addRecipe(new ItemStack(fpga),
                               new Object[] {"rcr", "sts", "ggg",
                                             Character.valueOf('r'),
                                             Item.redstone,
                                             Character.valueOf('c'),
                                             Item.comparator,
                                             Character.valueOf('s'),
                                             Block.stone,
                                             Character.valueOf('g'),
                                             Block.glass,
                                             Character.valueOf('t'),
                                             Block.torchRedstoneActive});
    }
}

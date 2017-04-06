package sayfog.electricool.compat;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import sayfog.electricool.powersystem.AbstractNetworkMemberBlock;

public class WailaCompat implements IWailaDataProvider {
	
	public static final WailaCompat INSTANCE = new WailaCompat();
	
	private static boolean registered;
    private static boolean loaded;
	
	public static void register(){
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendMessage("Waila", "register", "sayfog.electricool.compat.WailaCompat.load");
    }
	
	public static void load(IWailaRegistrar registrar) {
        System.out.println("WailaCompatibility.load");
        if (!registered){
            throw new RuntimeException("Please register this handler using the provided method.");
        }
        if (!loaded) {
            registrar.registerHeadProvider(INSTANCE, AbstractNetworkMemberBlock.class);
            registrar.registerBodyProvider(INSTANCE, AbstractNetworkMemberBlock.class);
            registrar.registerTailProvider(INSTANCE, AbstractNetworkMemberBlock.class);
            loaded = true;
        }
    }
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound tag, World arg3,
			BlockPos arg4) {
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		Block block = accessor.getBlock();
        if (block instanceof WailaInfoProvider) {
            return ((WailaInfoProvider) block).getWailaBody(itemStack, currenttip, accessor, config);
        }
        return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack arg0, List<String> currenttip, IWailaDataAccessor arg2,
			IWailaConfigHandler arg3) {
		return currenttip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor arg0, IWailaConfigHandler arg1) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack arg0, List<String> tail, IWailaDataAccessor arg2,
			IWailaConfigHandler arg3) {
		return tail;
	}

}

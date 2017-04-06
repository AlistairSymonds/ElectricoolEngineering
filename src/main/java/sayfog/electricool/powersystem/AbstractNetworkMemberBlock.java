package sayfog.electricool.powersystem;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import sayfog.electricool.compat.WailaInfoProvider;

public abstract class AbstractNetworkMemberBlock extends Block implements ITileEntityProvider, WailaInfoProvider{
	
	
	
	protected AbstractNetworkMemberBlock(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof AbstractNetworkMemberTileEntity) {
        	AbstractNetworkMemberTileEntity nte = (AbstractNetworkMemberTileEntity) te;
            currenttip.add(TextFormatting.GRAY + "Network: " + nte.getNetwork());
        }
        return currenttip;
    }
}

package sayfog.electricool.blocks.esources;


import java.util.ArrayList;
import java.util.List;

import sayfog.electricool.powersystem.ConnectionKeys;
import sayfog.electricool.powersystem.Current;

public class CurrentTileEntity extends Current {

	@Override
	public List<String> getPossibleConnectionKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(ConnectionKeys.POSITIVE);
		keys.add(ConnectionKeys.NEGATIVE);
		return keys;
	}
	
}

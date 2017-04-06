package sayfog.electricool.powersystem;

import java.util.HashSet;

import sayfog.electricool.ModElectricool;

public class ElectricoolNetworkManager {
	private static HashSet<ElectricoolNetwork> networks = new HashSet<ElectricoolNetwork>();
	
	
	
	protected static void addNetwork(ElectricoolNetwork networkIn){
		HashSet<ElectricoolNetwork> nets = ElectricoolNetworkManager.networks;
		networks.add(networkIn);
		networkIn.calculateValuesMNA();
		ModElectricool.logger.info("Added network: " + networkIn.toString());	
	}
	
	
	
	/**
	 * called each tick to update values
	 */
	/*
	public static void updateNetworks(){
		for(ElectricoolNetwork network : networks){
			network.defineValues();
		}
	}
	*/
}

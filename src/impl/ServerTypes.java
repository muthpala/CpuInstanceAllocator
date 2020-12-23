package impl;

/**
 * ServerTypes enum specifies the standard server types and the number of CPUS for each server type
 * 
 * @author Muthukumar
 *
 */
public enum ServerTypes {
	LARGE(1),
	XLARGE(2),
	X2LARGE(4),
	X4LARGE(8),
	X8LARGE(16),
	X10LARGE(32);
	
	private final int cpus;
	
	ServerTypes(int cpus) {
		this.cpus=cpus;
	}
	
	public int getCpus() {
		return cpus;
	}
	
	/**
	 * getCpusByServerType gets the number of cpus for the given server type
	 * 
	 * @param serverType
	 * @return numberOfCpus
	 */
	public static int getCpusByServerType(String serverType) {
		ServerTypes[] values = ServerTypes.values();
		for(ServerTypes eachType : values) {
			if(eachType.name().equalsIgnoreCase(serverType)) {
				return eachType.getCpus();
			}
		}
		return -1;
	}
	
}


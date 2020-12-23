package impl;
import java.math.BigDecimal;
import java.util.Map;

public class InstanceAllocatorResponse implements Comparable<InstanceAllocatorResponse>{
	
	private String region;
	
	private BigDecimal totalCost;
	
	private Map<String,Integer> servers;

	public InstanceAllocatorResponse(String region, BigDecimal totalCost, Map<String, Integer> servers) {
		super();
		this.region = region;
		this.totalCost = totalCost;
		this.servers = servers;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public Map<String, Integer> getServers() {
		return servers;
	}

	public void setServers(Map<String, Integer> servers) {
		this.servers = servers;
	}

	@Override
	public int compareTo(InstanceAllocatorResponse other) {
		return this.totalCost.compareTo(other.getTotalCost());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((servers == null) ? 0 : servers.hashCode());
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstanceAllocatorResponse other = (InstanceAllocatorResponse) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (servers == null) {
			if (other.servers != null)
				return false;
		} else if (!servers.equals(other.servers))
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InstanceAllocatorResponse [region=" + region + ", totalCost=" + totalCost + ", servers=" + servers
				+ "]";
	}
}

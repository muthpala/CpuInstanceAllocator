package impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * InstanceAllocatorModule allocates cost optimized instances 
 * 
 * @author Muthukumar
 *
 */
public class InstanceAllocatorModule {

	/**
	 * getCosts gets the maximum servers with optimized costs in each given region 
	 * 
	 * @param hours
	 * @param cpus
	 * @param price
	 * @param inputMap
	 * @return List<InstanceAllocatorRespoinse>
	 */
	public List<InstanceAllocatorResponse> getCosts(int hours, int cpus, double price, Map<String,Map<String,BigDecimal>> inputMap) {
		if(inputMap.isEmpty() || (hours<0 && cpus<0 && price<0)) {
			return new ArrayList<>();
		}
		List<InstanceAllocatorResponse> responseList = new ArrayList<>();
		for(Entry<String,Map<String,BigDecimal>> entry: inputMap.entrySet()) {
			Map<String,Integer> resultServerMap = new HashMap<>();
			BigDecimal totalCost = new BigDecimal(0);
			// For each server type dividing the cost per hour by number of cpus for the respective server type to obtain the cost per CPU in each server type
			Map<String,BigDecimal> dataCenterMap = entry.getValue().entrySet().stream().map(data -> {
				int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
				if(cpusFromEnum != -1) {
					BigDecimal costPerCpu = data.getValue().divide(BigDecimal.valueOf(cpusFromEnum));
					data.setValue(costPerCpu);
				}
				return data;
			}).sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
			int dataCenterMapSize = dataCenterMap.size();
			int numberOfCpus = cpus;
			int incrementor=0;
			BigDecimal pricePerHour = BigDecimal.ZERO;
			// If price and hours are given, the problem will be calculated with price logic
			if(price>0 && hours>0) {
				// calculate the price per hour by dividing the total price with the given hours
				pricePerHour = BigDecimal.valueOf(price/hours);
				BigDecimal serverTypeValue = entry.getValue().get((String)dataCenterMap.keySet().toArray()[incrementor]);
				// Divide the price per hour by the least cost server type to obtain the possible number of cpus that can be bought within the given price.
				int possibleNumberOfCpus = pricePerHour.divide(serverTypeValue,2,RoundingMode.HALF_UP).intValue();
				numberOfCpus = possibleNumberOfCpus;
			}
			while(numberOfCpus > 0 && (incrementor < dataCenterMapSize)) {
				String serverType = (String)dataCenterMap.keySet().toArray()[incrementor];
				int cpusFromEnum = ServerTypes.getCpusByServerType(serverType);
				if((cpusFromEnum != -1) && (numberOfCpus - cpusFromEnum) >=0) {
					BigDecimal addedValue = totalCost.add(entry.getValue().get(serverType).multiply(BigDecimal.valueOf(cpusFromEnum)));
					if(price>0 && hours>0 && addedValue.compareTo(pricePerHour) < 0) {
						numberOfCpus = numberOfCpus - cpusFromEnum;
						totalCost = addedValue;
						addValuesToMap(resultServerMap, numberOfCpus, incrementor, serverType);
					}else if(price<0 && cpus>0){
						numberOfCpus = numberOfCpus - cpusFromEnum;
						totalCost = totalCost.add(entry.getValue().get(serverType).multiply(BigDecimal.valueOf(cpusFromEnum)));
						addValuesToMap(resultServerMap, numberOfCpus, incrementor, serverType);
					}else {
						incrementor++;
					}
				}else {
					incrementor++;
				}
			}
			totalCost = calculateCostForHours(hours, totalCost);
			responseList.add(new InstanceAllocatorResponse(entry.getKey(), totalCost.setScale(2,RoundingMode.HALF_UP), resultServerMap));
		}
		Collections.sort(responseList);
		return responseList;
	}

	/**
	 * calculateCostForHours calculates the cost if hours is specified as an input
	 * 
	 * @param hours
	 * @param totalCost
	 * @return totalCost
	 */
	private BigDecimal calculateCostForHours(int hours, BigDecimal totalCost) {
		if(hours>0) {
			totalCost = totalCost.multiply(BigDecimal.valueOf(hours));
		}
		return totalCost;
	}

	/**
	 * addValuesToMap adds the server types which are eligible, to the result map 
	 * 
	 * @param resultServerMap
	 * @param numberOfCpus
	 * @param incrementor
	 * @param serverType
	 */
	private void addValuesToMap(Map<String, Integer> resultServerMap, int numberOfCpus, int incrementor, String serverType) {
		if(incrementor >=0 && numberOfCpus >=0 && resultServerMap.containsKey(serverType)) {
			resultServerMap.put(serverType, resultServerMap.get(serverType)+1);
		}else {
			resultServerMap.put(serverType, 1);
		}
	}

}

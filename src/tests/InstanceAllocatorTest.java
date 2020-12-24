package tests;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import impl.InstanceAllocatorModule;
import impl.InstanceAllocatorResponse;
import impl.ServerTypes;

class InstanceAllocatorTest {

		Map<String,Map<String,BigDecimal>> inputMap = new HashMap<>();
		
		@BeforeEach
		void formInput() {
			inputMap = new HashMap<>();
			Map<String,BigDecimal> usEastCosts = new HashMap<>();
			usEastCosts.put("large", BigDecimal.valueOf(0.12));
			usEastCosts.put("xlarge", BigDecimal.valueOf(0.23));
			usEastCosts.put("x2large", BigDecimal.valueOf(0.45));
			usEastCosts.put("x4large", BigDecimal.valueOf(0.774));
			usEastCosts.put("x8large", BigDecimal.valueOf(1.4));
			usEastCosts.put("x10large", BigDecimal.valueOf(2.82));
			inputMap.put("us-east", usEastCosts);
			Map<String,BigDecimal> usWestCosts = new HashMap<>();
			usWestCosts.put("large", BigDecimal.valueOf(0.14));
			usWestCosts.put("x2large", BigDecimal.valueOf(0.413));
			usWestCosts.put("x4large", BigDecimal.valueOf(0.89));
			usWestCosts.put("x8large", BigDecimal.valueOf(1.3));
			usWestCosts.put("x10large", BigDecimal.valueOf(2.97));
			inputMap.put("us-west", usWestCosts);
			Map<String,BigDecimal> asiaCosts = new HashMap<>();
			asiaCosts.put("large", BigDecimal.valueOf(0.11));
			asiaCosts.put("xlarge", BigDecimal.valueOf(0.20));
			asiaCosts.put("x4large", BigDecimal.valueOf(0.67));
			asiaCosts.put("x8large", BigDecimal.valueOf(1.18));
			inputMap.put("asia", asiaCosts);
		}

		@Test
		void testWithPriceHoursAndCpusInput() {
			int hours = 24;
			int cpus = 115;
			double price = 95;
			
			InstanceAllocatorModule module1 = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module1.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
				Assert.assertTrue(result.getTotalCost().compareTo(BigDecimal.valueOf(price)) <= 0);
			}
		}
		
		@Test
		void testWithPriceAndHoursInput() {
			int hours = 24;
			int cpus = -1;
			double price = 95;
			InstanceAllocatorModule module1 = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module1.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				Assert.assertTrue(result.getTotalCost().compareTo(BigDecimal.valueOf(price)) <= 0);
			}
		}
		
		@Test
		void testCpusWithHoursWithoutPrice() {
			int hours = 24;
			int cpus = 115;
			double price = -1;
			
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
			}
		}
		
		@Test
		void testCpusWithoutHoursAndPrice() {
			int hours = -1;
			int cpus = 115;
			double price = -1;
			
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
			}
		}
		
		@Test
		void testWithPriceHoursAndCpusInput2() {
			int hours = 7;
			int cpus = 214;
			double price = 95;
			
			InstanceAllocatorModule module1 = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module1.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
				Assert.assertTrue(result.getTotalCost().compareTo(BigDecimal.valueOf(price)) <= 0);
			}
		}
		
		@Test
		void testWithPriceAndHoursInput2() {
			int hours = 8;
			int cpus = -1;
			double price = 29;
			InstanceAllocatorModule module1 = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module1.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				Assert.assertTrue(result.getTotalCost().compareTo(BigDecimal.valueOf(price)) <= 0);
			}
		}
		
		@Test
		void testCpusWithHoursWithoutPrice2() {
			int hours = 8;
			int cpus = 148;
			double price = -1;
			
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
			}
			
		}
		
		@Test
		void testCpusWithoutHoursAndPrice2() {
			int hours = -1;
			int cpus = 25;
			double price = -1;
			
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			for(InstanceAllocatorResponse result: resultList) {
				result.getServers().entrySet().stream().forEach(data -> {
					int cpusFromEnum = ServerTypes.getCpusByServerType(data.getKey());
					int totalCpus = cpusFromEnum*data.getValue();
					Assert.assertTrue(totalCpus <= cpus);
				});
			}
		}
		
		@Test
		void testfailureCase1() {
			// all the inputs are invalid
			int hours = -1;
			int cpus = -1;
			double price = -1;
			
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			Assert.assertEquals(Collections.EMPTY_LIST, resultList);
		}
		
		@Test
		void testfailureCase2() {
			int hours = 20;
			int cpus = 20;
			double price = 100;
			// the region wise price map is empty
			inputMap = new HashMap<>();
			InstanceAllocatorModule module = new InstanceAllocatorModule();
			List<InstanceAllocatorResponse> resultList = module.getCosts(hours, cpus, price, inputMap);
			System.out.println(resultList);
			Assert.assertEquals(Collections.EMPTY_LIST, resultList);
		}

}

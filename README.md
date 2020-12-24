# CpuInstanceAllocator

## Overview
The following repo contains java code for Optimized CPU instance allocation for a cloud computing service company

## Source Code Algorithm

1. Get the inputs - hours,cpus,price and the region based cost map
2. If the inputs are invalid an empty list will be returned. (hours<0,cpus<0,price<0 or costmap is empty)
3. For each region in the region based cost map
     * Divide the cost per hour by given cpus to obtain the cost per CPU in each server type (large,xlarge,etc.)
     * Then sort the above obtained result based on the cost in ascending order
     * If price and hours are given as input, price per hour is calculated(price/hours), then divide the pricePerhour by the least cost server type's cost
       ie. the first element in the above sorted map will be the least cost as it is sorted in ascending order.
     * Now the divided value(PricePerHour/leastServerTypeCost) will be the possible number of cpus that can be bought for the given price.
     * Keep reducing the possible number of cpus by each server types cpus in the sorted map until they reach a point where the total cost calculated exceeds the given price.
     * If the price is not given, then the same logic should be carried out until the number of cpus become zero.
     * In both the cases the totalCost, servers and region will be populated. The result list will be sorted by the total cost, hence we will get the best region to purchase the servers
     * The total cost calculated in the above steps will be totalCost per hour, When hours is given as an input, the totalCost should be multiplied with the given hours to get the totalCost for the given hours


## Sample Input

hours = 24;
cpus = 115;
price = 95;
inputMap = 
{ 
  "us-east": {
     "large":0.12,
     "xlarge":0.23,
     "x2large":0.45,
     "x4large":0.774,
     "x8large":1.4,
     "x10large":2.82
   },
   "us-west": {
     "large":0.14,
     "x2large":0.413,
     "x4large":0.89,
     "x8large":1.3,
     "x10large":2.97
   },
   "us-east": {
     "large":0.11,
     "xlarge":0.20,
     "x4large":0.67,
     "x8large":1.18
   }
}

## Sample Output

[
InstanceAllocatorResponse [region=us-west, totalCost=93.60, servers={x8large=3}],
InstanceAllocatorResponse [region=us-east, totalCost=94.18, servers={x8large=2, large=1, xlarge=1, x4large=1}],
InstanceAllocatorResponse [region=asia, totalCost=94.56, servers={x8large=3, xlarge=2}]
]

As you could see the totalCost is not exceeding more than the given price.

Hence the result is a list of InstanceAllocatorResponse sorted by totalcost.


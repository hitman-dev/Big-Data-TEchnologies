# import findspark

from pyspark import SparkContext, SparkConf

master = 'local'
appName = 'MultipleRDDOperations'

config = SparkConf().setAppName(appName).setMaster(master)
sc = SparkContext(conf=config)

if sc:
    print(sc.appName)
else:
    print('Could not initialise pyspark session')

print()
print('=================================')
print('Declare an array and parallelize it')
print('=================================')
data1 = [1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 6]
data2 = [1, 2, 3, 8, 9, 10]
parallelData1 = sc.parallelize(data1)
parallelData2 = sc.parallelize(data2)

parallelPairRDD1 = parallelData1.map(lambda x: (x, 1))
parallelPairRDD2 = parallelData2.map(lambda x: (x, 2))

print('=================================')
print('Count by Value')
print('=================================')
countByValue = parallelData1.countByValue()
print(f'Print : {countByValue}')
print('=================================')

print('=================================')
print('Group By Key')
print('=================================')
groupedByKey = parallelPairRDD1.groupByKey()
groupedByKey.foreach(print)
print('=================================')

print('=================================')
print('Reduce By Key')
print('=================================')
reduceByKey = parallelPairRDD1.reduceByKey(lambda x, y: x + y)
reduceByKey.foreach(print)
print('=================================')

print('=================================')
print('Aggregate By Key')
print('=================================')
aggregateByKey = parallelPairRDD1.aggregateByKey(0, lambda x, y: x + y, lambda x, y: x + y)
aggregateByKey.foreach(print)
print('=================================')

print('=================================')
print('Sort By Key')
print('=================================')
sortByKey = parallelPairRDD1.sortByKey()
sortByKey.foreach(print)
print('=================================')


print('=================================')
print('Count By Key')
print('=================================')
countByKey = parallelPairRDD1.countByKey()
print(countByKey)
print('=================================')

print('=================================')
print('CoGroup Operation')
print('=================================')
coGroup = parallelData1.cogroup(parallelData2)
print(coGroup)
print('=================================')


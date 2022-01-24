from pyspark import SparkContext, SparkConf, SQLContext
from pyspark.sql import SparkSession, DataFrame
from pyspark.sql.functions import desc, sum, avg
from pyspark.sql.functions import col

master = 'local'
appName = 'PySpark_Data format Operations'

config = SparkConf().setAppName(appName).setMaster(master)
sc = SparkContext(conf=config)
# You will need to create the sqlContext for SparkSQL
sqlContext = SQLContext(sc)
# You will need to create the SparkSession for streaming
ss = SparkSession(sc)

if ss:
    print(sc.appName)
else:
    print('Could not initialise pyspark session')

print('Load Data into dataframe')
print('=================================')
df = ss.read.csv('file:////home/hitman/Downloads/BDT_Labexam/fci_Stock_Position_commodity_02_Rice'
                 '-Raw_Tamil_Nadu-2021.csv', header=True)

df.show(n=20)
df.printSchema()

print('=================================')
print('Q1-1 Find all the districts participating in the data collection')
print('=================================')

df.select('DistrictName').distinct().show()

print('=================================')
print('Q1-2 Find the district with max Stock and CommodityStock')
print('=================================')

df.groupBy("DistrictName").agg(sum('Stock')).sort(desc('sum(Stock)')).show(20)

print('=================================')
print('Q1-3 Find the district with max Total Stock, where Total Stock = Stock + CommodityStock')
print('=================================')

df_totalStock = df.withColumn("Total_Stock", col("Stock") + col("CommodityStock"))

df_totalStock.groupBy("DistrictName").agg(sum('Total_Stock')).sort(desc('sum(Total_Stock)')).show(1)

print('=================================')
print('Q1-4 Find the average of all the Stock for “Chennai”')
print('=================================')

df.groupBy("DistrictName").agg(avg('Stock')).filter((df.DistrictName == "CHENNAI")).show()

print('=================================')
print('Q1-5. Find the district with min of avg of commodity stock')
print('=================================')

df.groupBy("DistrictName").agg(avg('CommodityStock')).sort('avg(CommodityStock)').show(1)


from pyspark import SparkContext, SparkConf, SQLContext, StorageLevel
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, StringType, IntegerType, DoubleType

master = 'local'
appName = 'PySpark_Streaming Operations'
config = SparkConf().setAppName(appName).setMaster(master)
sc = SparkContext(conf=config)
sc.setSystemProperty("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
# You will need to create the sqlContext for SparkSQL
sqlContext = SQLContext(sc)
# You will need to create the SparkSession for streaming
ss = SparkSession(sc)
print(sc.getConf().get("spark.serializer"))
print('Done')
if ss:
    print(sc.appName)
else:
    print('Could not initialise pyspark session')

print('=================================')
print('Read train data over a file location')
print('=================================')
Schema = StructType([StructField('Data', StringType(), True),
                          StructField('Code', StringType(), True),
                          StructField('CommodityId', IntegerType(), True),
                          StructField('CommodityName', StringType(), True),
                          StructField('DistrictName', StringType(), True),
                          StructField('DistrictCode', StringType(), True),
                          StructField('Stock', DoubleType(), True),
                          StructField('CommodityStock', DoubleType(), True)])
# Please modify the param to CSV with some existing directory

baseDir1 = 'hdfs://localhost:9000/tmp/inputDir'

df = ss.read.option("header", False) \
    .option("inferSchema", True) \
    .csv('file:////home/hitman/Downloads/BDT_Labexam/fci_Stock_Position_commodity_02_Rice-Raw_Tamil_Nadu-2021.csv')
df.write.option("header", False).mode('overwrite').parquet(baseDir1)

stockData = ss.readStream.schema(Schema) \
    .option('header', False) \
    .csv('/tmp/inputDir')

stockData.printSchema()
query = stockData.writeStream \
    .outputMode('append').format('console').start()
query.awaitTermination()
print('=================================')
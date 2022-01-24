from pyspark.sql import SparkSession
from pyspark.sql.functions import explode, split, col

print('Create the spark session')
spark = SparkSession \
    .builder \
    .master('local') \
    .appName("DBDA_Socket_Example") \
    .getOrCreate()

# Create DataFrame representing the stream of input lines from connection to localhost:9999
# Run this command before starting your program ==> $ nc -lk 9999
lines = spark \
    .readStream \
    .format("socket") \
    .option("host", "localhost") \
    .option("port", 9999) \
    .load()

print('Check the schema for the lines dataframe')
lines.printSchema()

words = lines.select(explode(split(lines.value, " ")).alias('word'))
print('Check the schema for the words dataframe')
words.printSchema()

wc = words.groupBy('word').count()

print('Check the schema for the wc dataframe')
wc.printSchema()


# Start running the query that prints the running counts to the console
query = wc \
    .writeStream \
    .outputMode("complete") \
    .format("console") \
    .start()

# The application will wait for query termination.
# if not present, your spark streaming application will close, and it will also close your streaming query
query.awaitTermination()

import os

import findspark
from pyspark import SparkContext, SparkConf, SQLContext

# os.environ['SPARK_HOME'] = '/home/abhay/MyHome/InstallHome/Apache/spark'
#
# findspark.init()

master = 'local'
appName = 'PySpark_Dataframe DB Operations'

config = SparkConf().setAppName(appName).setMaster(master)
sc = SparkContext(conf=config)
# You will need to create the sqlContext
sqlContext = SQLContext(sc)
#sqlContext = SQLContext(sc)

if sc:
    print(sc.appName)
else:
    print('Could not initialise pyspark session')

jdbcURL = 'jdbc:mysql://localhost/HumanResource'
user = 'hitman'
password = 'password'

# Read the complete table
dfFromDb = sqlContext.read.format('jdbc') \
    .option('url', jdbcURL) \
    .option('user', user) \
    .option('password', password) \
    .option('dbtable', 'countries') \
    .load()
dfFromDb.printSchema()
dfFromDb.show()

# Same as line no 42
# dfFromDb.filter('region_id == 1')

# Run queries on DF
dfFromDb.createGlobalTempView('countries')
countryWithRegion = sqlContext.sql('select * from global_temp.countries')
countryWithRegion.show()

# Read selective from table
# dfFromDb = sqlContext.read.format('jdbc') \
#     .option('url', jdbcURL) \
#     .option('user', user) \
#     .option('password', password) \
#     .option('query', 'select * from employees where employee_id > 200') \
#     .load()
# dfFromDb.printSchema()
# dfFromDb.show()
#
# # Write back to DBs
# countryWithRegion.write.jdbc(jdbcURL, 'countryWithRegion', 'overwrite',
#                              properties={"user": user, "password": password})
# print("Write successful")

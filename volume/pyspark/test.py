from datetime import datetime
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, date_format, to_date, year
from pyspark.sql.types import (DateType, IntegerType, FloatType, StructField,
                               StructType, TimestampType, StringType)


spark = SparkSession.builder.appName('Read Transactions').getOrCreate()

def func(row):
    # print(row.id)
    row.id = row.id*2

try:
    csv_schema = StructType([StructField('id', IntegerType()), StructField('word', StringType())])
    df_load = spark.read.csv('hdfs://127.0.0.1:9000/data/input/test_info.csv', schema=csv_schema)
    # df_load = spark.read.csv('hdfs://127.0.0.1:9000/data/output/part-r-00000')
    df_load.foreach(func)
    df_load.show()
except Exception as e:
    print(e)


'''
MapR Output: team_name, player_name, birth_date, position, current_price, cp_date, highest_price, hp_date
Hive Output: player_name, birth_date, overall_rating, potential, potential_change, overall_change_rate
'''

# spark.createDataFrame()

# csv_schema = StructType([StructField('fecha', StringType()),
#                          StructField('amount', FloatType())
#                          ])

# dataframe = spark.read.csv("datasales.dat",
#                            schema=csv_schema,
#                            header=True)

# dataframe = dataframe.withColumn('fecha',to_date(col('fecha'), 'MM/dd/yyyy'))
# dataframe.show()


# # Group By and Select the data already aggregated
# dataframe = dataframe.withColumn('year',year('fecha'))
# dataframe.show()

# sum_df = dataframe.groupBy("year").sum()

# dataframe = \
#     sum_df.select(
#         col('year'),
#         col('sum(amount)').alias('amountperyear'))

# dataframe.printSchema()
# dataframe.show()

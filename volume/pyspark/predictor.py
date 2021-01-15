from pyspark.sql import SparkSession
from pyspark.sql.functions import col, date_format, to_date, year
from pyspark.sql.types import (DateType, IntegerType, FloatType, StructField,
                               StructType, TimestampType, StringType)


spark = SparkSession.builder.appName('Read Transactions').getOrCreate()

'''
MapR Output: team_name, player_name, birth_date, current_price, price_change
Hive Output: player_name, birth_date, overall_rating, potential, potential_change, overall_change_rate
'''
market_schema = StructType([StructField('team_name', StringType()), 
                            StructField('player_name', StringType()),
                            StructField('birth_date', DateType()),
                            StructField('current_price', IntegerType()),
                            StructField('price_change', FloatType())])

player_schema = StructType([StructField('name', StringType()),
                            StructField('bdate', DateType()),
                            StructField('overall_rating', IntegerType()),
                            StructField('potential', IntegerType()),
                            StructField('potential_change', FloatType()),
                            StructField('overall_change', FloatType())])

try:
    # creates initial data frames
    market = spark.read.csv('hdfs://127.0.0.1:9000/data/output/mapr/part-r-00000', schema=market_schema, ignoreTrailingWhiteSpace=True)
    player = spark.read.csv('hdfs://127.0.0.1:9000/data/output/hive/000000_0', schema=player_schema)

    # joins both dataframes and selects necessary info
    # current_price is left temporarily, for development purposes
    join_condition = (market.player_name==player.name) & (market.birth_date==player.bdate)
    predictions = market.join(player,join_condition,'inner').selectExpr(
                        "team_name", "player_name", "current_price", "(((overall_change+price_change+2)/2)+potential_change)*current_price AS predicted_value")
    
    predictions.show(100)
    predictions.write.mode("overwrite").json('hdfs://127.0.0.1:9000/data/output/pyspark')

except Exception as e:
    print(e)
from pyspark.sql import SparkSession
from pyspark.sql.functions import col, date_format, to_date, year, collect_list, struct, lit
from pyspark.sql.types import (DateType, IntegerType, FloatType, StructField,
                               StructType, TimestampType, StringType)


spark = SparkSession.builder.appName('Read Transactions').getOrCreate()

hdfsRoute = 'hdfs://127.0.0.1:9000'
outputPath = hdfsRoute + '/data/output/pyspark'
marketFilePath = hdfsRoute + '/data/output/mapr/part-r-00000'
playerFilePath = hdfsRoute + '/data/output/hive/000000_0'

'''
MapR Output: country, team_name, player_name, birth_date, current_price, price_change
Hive Output: player_name, birth_date, overall_rating, potential, potential_change, overall_change_rate
'''
market_schema = StructType([StructField('team_name', StringType()), 
                            StructField('player_name', StringType()),
                            StructField('birth_date', DateType()),
                            StructField('current_price', IntegerType()),
                            StructField('price_change', FloatType()),
                            StructField('country', StringType())])

player_schema = StructType([StructField('name', StringType()),
                            StructField('bdate', DateType()),
                            StructField('overall_rating', IntegerType()),
                            StructField('potential', IntegerType()),
                            StructField('potential_change', FloatType()),
                            StructField('overall_change', FloatType())])
try:
    # creates initial data frames
    market = spark.read.csv(marketFilePath, schema=market_schema, ignoreTrailingWhiteSpace=True)
    player = spark.read.csv(playerFilePath, schema=player_schema)

    # joins both dataframes and selects necessary info
    join_condition = (market.player_name==player.name) & (market.birth_date==player.bdate)

    predictionExpression = "(((overall_change+price_change+2)/2)+potential_change)*current_price"
    predictions = market.join(player,join_condition,'inner').selectExpr(
                    "country","team_name", "player_name", predictionExpression + " AS predicted_value")

    playersByGroup = predictions.withColumn('player',struct(
                                                                col("player_name").alias("name"),
                                                                col("predicted_value").alias("value"))
                                                                    ).drop("predicted_value","player_name"
                                                                        ).groupBy("country","team_name").agg(collect_list("player").alias("Players"))

    clubsByCountry = playersByGroup.withColumn("club", struct(
                                                                col("team_name").alias("name"),
                                                                col("Players").alias("children"))
                                                                ).drop("team_name","Players"
                                                                    ).groupBy("country").agg(collect_list("club").alias("children"))

    clubsByCountry.write.mode("overwrite").json(outputPath)

except Exception as e:
    print(e)
## Hive Guideline

## Import SQLite Data:

sqlite3 -header -csv database.sqlite "SELECT * FROM Player" > Player.csv
sqlite3 -header -csv database.sqlite "SELECT * FROM Player_Attributes" > Player_Attributes.csv

## Hive Querys

- Hay que tomar en cuenta que **birthday es un timestamp**

1. Copy data file to hfs
    hadoop fs -copyFromLocal Player.csv /data/input
    hadoop fs -copyFromLocal Player_Attributes.csv /data/input

2. Create Schema
    create schema SoccerDB;
    use SoccerDB;

3. Create Tables
    hive –f CreateTables.sql

4. Insert into table
    load data inpath '/data/input/PlayerStats.csv' into table player_stats;

5. Querys

### Show headers
    set hive.cli.print.header=true
### Test
    select * from player_stats limit 2;
### Filter
**Hay que definir el query que se va a hacer**


6. Save output to file in hdfs

- You can add LOCAL DIRECTORY 

```
INSERT OVERWRITE DIRECTORY '/data/output/hive'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
select * from player_stats2 limit 2;
```

6. Check output

` hadoop fs -cat /data/output/hive/000000_0 `





## Hive Guideline
## TODO
[ ] Hay que agregar el club ya que se debe tener al final el club del 2016

## Import SQLite Data:

sqlite3 -header -csv database.sqlite "SELECT * FROM Player" > Player.csv
sqlite3 -header -csv database.sqlite "SELECT * FROM Match" > Match.csv
sqlite3 -header -csv database.sqlite "SELECT * FROM Player_Attributes" > Player_Attributes.csv
sqlite3 -header -csv database.sqlite "SELECT * FROM Team" > Team.csv


id,country_id,league_id,season,stage,date,match_api_id,home_team_api_id,away_team_api_id,home_team_goal,away_team_goal,home_player_X1,home_player_X2,home_player_X3,home_player_X4,home_player_X5,home_player_X6,home_player_X7,home_player_X8,home_player_X9,home_player_X10,home_player_X11,away_player_X1,away_player_X2,away_player_X3,away_player_X4,away_player_X5,away_player_X6,away_player_X7,away_player_X8,away_player_X9,away_player_X10,away_player_X11,home_player_Y1,home_player_Y2,home_player_Y3,home_player_Y4,home_player_Y5,home_player_Y6,home_player_Y7,home_player_Y8,home_player_Y9,home_player_Y10,home_player_Y11,away_player_Y1,away_player_Y2,away_player_Y3,away_player_Y4,away_player_Y5,away_player_Y6,away_player_Y7,away_player_Y8,away_player_Y9,away_player_Y10,away_player_Y11,home_player_1,home_player_2,home_player_3,home_player_4,home_player_5,home_player_6,home_player_7,home_player_8,home_player_9,home_player_10,home_player_11,away_player_1,away_player_2,away_player_3,away_player_4,away_player_5,away_player_6,away_player_7,away_player_8,away_player_9,away_player_10,away_player_11,goal,shoton,shotoff,foulcommit,card,cross,corner,possession,B365H,B365D,B365A,BWH,BWD,BWA,IWH,IWD,IWA,LBH,LBD,LBA,PSH,PSD,PSA,WHH,WHD,WHA,SJH,SJD,SJA,VCH,VCD,VCA,GBH,GBD,GBA,BSH,BSD,BSA

## Hive Querys

- Hay que tomar en cuenta que **birthday es un timestamp**

1. Copy data file to hfs
    hadoop fs -copyFromLocal Player.csv /data/input
    hadoop fs -copyFromLocal Match.csv /data/input
    hadoop fs -copyFromLocal Player_Attributes.csv /data/input
    hadoop fs -copyFromLocal Team.csv /data/input

2. Create Schema
    create schema SoccerDB;
    use SoccerDB;

3. Create Tables

    ```
    CREATE TABLE IF NOT EXISTS player_stats
        (   
            player_api_id int, 
            player_name string, 
            birthday timestamp, 
            overall_rating int,
            rating_date timestamp 
            potential int,
        )
    COMMENT 'Overall rating and potential of players with name and birthday'
    ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
    STORED AS TEXTFILE
    tblproperties("skip.header.line.count"="1");
    ```

3. Insert into table
load data inpath '/data/input/PlayerStats.csv' into table player_stats;

4. Querys

### Show headers
    set hive.cli.print.header=true
### Test
    select * from player_stats limit 2;
### Filter
**Hay que definir el query que se va a hacer**


5. Save output to file in hdfs

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





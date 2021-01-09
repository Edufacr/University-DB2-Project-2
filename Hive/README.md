## Import SQLite Data

[ ] Hay que preguntarle al profe cual de las dos opciones

### Opciones:
    1. Se puede hacer por medio de sqoop:
        https://docs.cloudera.com/HDPDocuments/HDP3/HDP-3.1.5/migrating-data/content/hive_import_rdbms_data_into_hive.html
        https://stackoverflow.com/questions/30365368/how-to-import-sqlite-database-into-hadoop-hdfs
        
    2. Se puede transformar a CSV el sqlite.
        https://www.sqlitetutorial.net/sqlite-tutorial/sqlite-export-csv/

        Comando:
        sqlite3 -header -csv database.sqlite "SELECT p.player_api_id, player_name, birthday, overall_rating, date, potential FROM Player p INNER JOIN Player_Attributes pa ON p.player_api_id = pa.player_api_id; " > PlayerStats.csv

## Query SQLite
```
SELECT p.player_api_id, player_name, birthday, overall_rating, date, potential
    FROM Player p INNER JOIN Player_Attributes pa
        ON p.player_api_id = pa.player_api_id; 
```
## Hive Querys

- Hay que tomar en cuenta que **birthday es un timestamp**
- Hay que ver donde guarda el output de los querys o si hay que meterlo en una tabla

1. Copy data file to hfs
    `hadoop fs -copyFromLocal PlayerStats.csv /data/input`

2. Create Schema
    `create schema SoccerDB;`
    `use SoccerDB;`

3. Create Table

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





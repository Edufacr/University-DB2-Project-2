## Import SQLite Data

[ ] Hay que preguntarle al profe cual de las dos opciones

### Opciones:
    1. Se puede hacer por medio de sqoop:
        https://docs.cloudera.com/HDPDocuments/HDP3/HDP-3.1.5/migrating-data/content/hive_import_rdbms_data_into_hive.html
        https://stackoverflow.com/questions/30365368/how-to-import-sqlite-database-into-hadoop-hdfs
        
    2. Se puede transformar a CSV el sqlite.
        https://www.sqlitetutorial.net/sqlite-tutorial/sqlite-export-csv/

        Comando:
        sqlite3 -header -csv database.sqlite "SELECT p.player_api_id, player_name, birthday, overall_rating, potential FROM Player p INNER JOIN Player_Attributes pa ON p.player_api_id = pa.player_api_id;" > PlayerStats.csv

## Query SQL
'''
SELECT p.player_api_id, player_name, birthday, overall_rating, potential
    FROM Player p INNER JOIN Player_Attributes pa
        ON p.player_api_id = pa.player_api_id;
'''
## Hive Querys

- Hay que tomar en cuenta que **birthday es un timestamp**
- Hay que ver como cargarlo como timestamp de una vez
- Hay que ver donde guarda el output de los querys o si hay que meterlo en una tabla

*Create Schema*
create schema SoccerDB;

*Create Table*
CREATE TABLE IF NOT EXISTS player_stats 
    (   
        player_api_id int, 
        player_name string, 
        birthday timestamp, 
        overall_rating int, 
        potential int
    )
COMMENT 'Overall rating and potential of players with name and birthday'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

*Insert into table*

load data inpath '/data/input/PlayerStats.dat' into table player_stats;
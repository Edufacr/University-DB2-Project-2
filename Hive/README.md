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

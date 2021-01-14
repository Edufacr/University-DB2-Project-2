## Hive Guideline

## SQLite Data to CSV:

sqlite3 -header -csv database.sqlite "SELECT * FROM Player" > Player.csv
sqlite3 -header -csv database.sqlite "SELECT * FROM Player_Attributes" > Player_Attributes.csv

## Hive Querys

- Hay que tomar en cuenta que **birthday es un timestamp**

1. Copy data file to hfs
    hadoop fs -copyFromLocal player.csv /data/input
    hadoop fs -copyFromLocal player_attributes.csv /data/input

2. Create Tables
    hive –f createTables.sql

3. Insert into table (ESTO ES DENTRO DE HIVE)
    load data inpath '/data/input/player.csv' into table raw_player;
    load data inpath '/data/input/player_attributes.csv' into table raw_player_attributes;

4. Querys
    
    hive –f reduce.sql

6. Check output

    hadoop fs -cat /data/output/hive/000000_0

## Notas

*Show headers*
    Para mostrar los headers a la hora de hacer un select en hive:
    set hive.cli.print.header=true





-- **********************
-- Tables to load data from csv
-- **********************

CREATE TABLE IF NOT EXISTS raw_Player
    (   
    id int,
    player_api_id int,
    player_name int,
    player_fifa_api_id int,
    birthday timestamp, 
    height float,
    weight float
    )
COMMENT 'Player info'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
tblproperties("skip.header.line.count"="1");

CREATE TABLE IF NOT EXISTS raw_Player_Attributes
    (   
    id int,
    player_fifa_api_id int,
    player_api_id int,
    seasonDate timestamp,
    overall_rating int,
    potential int,
    preferred_foot int,attacking_work_rate int,defensive_work_rate int,crossing int,finishing int,heading_accuracy int,short_passing int,volleys int,dribbling int,curve int,free_kick_accuracy int,long_passing int,ball_control int,acceleration int,sprint_speed int,agility int,reactions int,balance int,shot_power int,jumping int,stamina int,strength int,long_shots int,aggression int,interceptions int,positioning int,vision int,penalties int,marking int,standing_tackle int,sliding_tackle int,gk_diving int,gk_handling int,gk_kicking int,gk_positioning int,gk_reflexes int
    )
COMMENT 'Player attributes'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
tblproperties("skip.header.line.count"="1");


-- **********************
-- Output table
-- **********************
CREATE TABLE IF NOT EXISTS player_stats
    (   
        player_name string, 
        birthday date, 
        overall_rating int,
        potential int,
        potential_change float,
        overall_change_rate float
    )
COMMENT 'Overall ratings and potentials of players with name, birthday and calculated values'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
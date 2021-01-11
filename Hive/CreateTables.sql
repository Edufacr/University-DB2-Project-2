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

CREATE TABLE IF NOT EXISTS raw_Match
    (   
    id int,
    country_id int,
    league_id int,
    season string,
    stage int,
    date timestamp,
    match_api_id int,
    home_team_api_id int,
    away_team_api_id int,
    home_team_goal int,
    away_team_goal int,
    home_player_X1 int,home_player_X2 int,home_player_X3 int,home_player_X4 int,home_player_X5 int,home_player_X6 int,home_player_X7 int,home_player_X8 int,home_player_X9 int,home_player_X10 int,home_player_X11 int,away_player_X1 int,away_player_X2 int,away_player_X3 int,away_player_X4 int,away_player_X5 int,away_player_X6 int,away_player_X7 int,away_player_X8 int,away_player_X9 int,away_player_X10 int,away_player_X11 int,home_player_Y1 int,home_player_Y2 int,home_player_Y3 int,home_player_Y4 int,home_player_Y5 int,home_player_Y6 int,home_player_Y7 int,home_player_Y8 int,home_player_Y9 int,home_player_Y10 int,home_player_Y11 int,away_player_Y1 int,away_player_Y2 int,away_player_Y3 int,away_player_Y4 int,away_player_Y5 int,away_player_Y6 int,away_player_Y7 int,away_player_Y8 int,away_player_Y9 int,away_player_Y10 int,away_player_Y11 int,
    home_player_1 int,home_player_2 int,home_player_3 int,home_player_4 int,home_player_5 int,home_player_6 int,home_player_7 int,home_player_8 int,home_player_9 int,home_player_10 int,home_player_11 int,
    away_player_1 int,away_player_2 int,away_player_3 int,away_player_4 int,away_player_5 int,away_player_6 int,away_player_7 int,away_player_8 int,away_player_9 int,away_player_10 int,away_player_11 int,
    goal string, shoton string, shotoff string, foulcommit string,card string,cross string,corner string,possession string,
    B365H int,B365D int,B365A int,BWH int,BWD int,BWA int,IWH int,IWD int,IWA int,LBH int,LBD int,LBA int,PSH int,PSD int,PSA int,WHH int,WHD int,WHA int,SJH int,SJD int,SJA int,VCH int,VCD int,VCA int,GBH int,GBD int,GBA int,BSH int,BSD int,BSA
    )
COMMENT 'Match info'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
tblproperties("skip.header.line.count"="1");

CREATE TABLE IF NOT EXISTS raw_Player_Attributes
    (   
    id int,
    player_fifa_api_id int,
    player_api_id int,
    date timestamp,
    overall_rating int,
    potential int,
    preferred_foot int,attacking_work_rate int,defensive_work_rate int,crossing int,finishing int,heading_accuracy int,short_passing int,volleys int,dribbling int,curve int,free_kick_accuracy int,long_passing int,ball_control int,acceleration int,sprint_speed int,agility int,reactions int,balance int,shot_power int,jumping int,stamina int,strength int,long_shots int,aggression int,interceptions int,positioning int,vision int,penalties int,marking int,standing_tackle int,sliding_tackle int,gk_diving int,gk_handling int,gk_kicking int,gk_positioning int,gk_reflexes
    )
COMMENT 'Player attributes'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
tblproperties("skip.header.line.count"="1");

CREATE TABLE IF NOT EXISTS raw_Team
    (   
    id int,
    team_api_id int,
    team_fifa_api_id int,
    team_long_name string,
    team_short_name string
    )
COMMENT 'Teams info'
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
STORED AS TEXTFILE
tblproperties("skip.header.line.count"="1");

-- **********************
-- Temp tables
-- **********************
-- **********************
-- Output table
-- **********************
CREATE TABLE IF NOT EXISTS Player_stats
    (   
        player_api_id int, 
        player_name string, 
        birthday timestamp, 
        overall_rating int,
        rating_date timestamp,
        potential int,
        team_api_id int
    )
COMMENT 'Overall ratings and potentials of players with name, birthday and club'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
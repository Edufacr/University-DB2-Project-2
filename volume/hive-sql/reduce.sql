-- **********************
-- Temp tables
-- **********************
CREATE TEMPORARY TABLE tmp_playerStats(
        player_api_id INT,
        player_name STRING, 
        birthday DATE,
        seasonDate TIMESTAMP,
        overall_rating INT,
        potential INT,
        potential_change FLOAT,
        season_number_desc INT
);
-- **********************
-- Querys
-- **********************
INSERT INTO tmp_playerStats
    SELECT 
        p.player_api_id, 
        player_name, 
        To_Date(birthday),
        seasonDate,
        overall_rating,
        potential,
        ((potential - overall_rating) / overall_rating) AS potential_change, 
        RANK() OVER ( PARTITION BY p.player_api_id ORDER BY seasonDate DESC) AS season_number_desc

    FROM raw_Player p INNER JOIN raw_Player_Attributes pa
        ON p.player_api_id = pa.player_api_id;
        
INSERT INTO player_stats
    SELECT 
        player_name,
        birthday,
        overall_rating,
        potential,
        potential_change, 
        overall_change_rate
    FROM 
    (
        SELECT 
            tmp1.player_name,
            tmp1.birthday,
            tmp1.overall_rating,
            tmp1.potential,
            tmp1.potential_change, 
            (tmp1.overall_rating / tmp2.overall_rating) + 1 AS overall_change_rate

        FROM tmp_playerStats tmp1 INNER JOIN tmp_playerStats tmp2
            ON 
                tmp1.player_api_id = tmp2.player_api_id 
                AND 
                (tmp1.season_number_desc + 1) = tmp2.season_number_desc -- Last season
                --(tmp1.season_number_desc + 2) = tmp2.season_number_desc -- last year 
        WHERE 
            tmp1.season_number_desc = 1
            AND
            tmp1.seasonDate > '2015-01-01 00:00:00'
    ) tmp
    WHERE overall_change_rate IS NOT NULL;

-- **********************
-- Write to file
-- **********************
-- - You can add LOCAL DIRECTORY 

INSERT OVERWRITE DIRECTORY '/data/output/hive'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
select player_name, birthday, overall_rating, potential, potential_change, overall_change_rate from player_stats;
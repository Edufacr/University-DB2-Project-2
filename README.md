## Repository for course: database II
This readme helps you to perform the intended labs in classroom regarding topics such as mapreduce with hadoop, hive, spark and kafka.

### docker related  
Build the image, create an internal network and run the image using a local volumen
path to share files and jars from the host computer
```
docker build . -t hadoop-spark

docker network create --driver bridge --subnet 10.0.0.0/28 littlenet

docker run -it -p 9000:9000 -p 9092:9092 -p 22:22 -v /Users/gabriel/Documents/TEC/Bases\ de\ datos\ II/University-DB2-Project-2/volume:/home/hadoopuser/vol --name hadoopserver --net littlenet --ip 10.0.0.2 hadoop-spark

docker run -it -p 9000:9000 -p 9092:9092 -p 22:22 -v C:/Users/eduma/Documents/CodingProjects/DB-2/University-DB2-Project-2/volume:/home/hadoopuser/vol --name hadoopserver --net littlenet --ip 10.0.0.2 hadoop-spark
```

This is an example of how to manually copy files from the host to the container 
```
docker cp mapr.jar hadoopserver:/home/hadoopuser
docker cp mercado.csv  hadoopserver:/home/hadoopuser
```

### ssh related
The image includes a default user setup, the user "hadoopuser" must grant passwordless access by ssh, this is required for the hadoop server

```
su - hadoopuser
cd /home/hadoopuser
ssh-keygen -t rsa -P '' -f /home/hadoopuser/.ssh/id_rsa
ssh-copy-id hadoopuser@localhost
exit
```

### hadoop related
These are the commands to start/stop the hadoop single node cluster 
```
start-all.sh
stop-all.sh
```

These are example of instructions to prepare hdfs folders and run a map reduce example
```
hadoop fs -mkdir /data
hadoop fs -mkdir /data/input
hadoop fs -copyFromLocal vol/data/mercado.csv /data/input
hadoop jar vol/mapr/mapr.jar main.program /data/input/mercado.csv /data/output/mapr
```

hadoop fs -cat /data/output/part-r-00000
hadoop fs -rm -r /data/output/mapr

../../../opt/hadoop/hadoop-3.3.0/logs
cat ../../../opt/hadoop/hadoop-3.3.0/logs/userlogs/application_1607106532893_000x/container_1607106532893_000x_01_000002/stdout
cat ../../opt/hadoop/hadoop-3.3.0/logs/userlogs/application_1610574396214_0006/container_1610574396214_0006_01_000002/stdout

# Finding hdfs url
cat ../../opt/hadoop/hadoop-3.3.0/etc/hadoop/core-site.xml

### hive related
To setup the hive environment just run the `hive-setup.sh` script located in hadoopuser home folder

Then access the hive console with `hive`

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

### pyspark related
To run the examples use the spark-submit command, for example:

spark-submit vol/pyspark/predictor.py


### Kakfa related
To start the kafkta server just the script `start-kafka.sh` located in the hadoopuser home folder.

To test your Kafka environment follow the [kafka quickstart guide](https://kafka.apache.org/quickstart) 

### Visualization 
Run:
    node vol/visulizer/app.js
Access:
    127.0.0.1:9092

//Le hice un apt-get intall npm
//npm install --save express
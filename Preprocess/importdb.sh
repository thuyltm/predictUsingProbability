#mongo -host 192.168.1.102 bus --eval "db.bus.drop()"
#mongo -host 192.168.1.102 bus --eval "db.bus.dropIndexes()"
cd "/home/thuy1/json/"
for folder in /home/thuy1/json/*
  do 
    cd "$folder"
    #for f in *.json; do
    for f in *.*; do
	    # do some stuff here with "$f"
	    # remember to quote it or spaces may misbehave
	   #echo Entering into $f and installing packages 123	
    	   mongoimport --host 192.168.1.102 --port 27017 --db bus --collection bus <$f --jsonArray
    done
  done;
#for f in *.json; do
    # do some stuff here with "$f"
    # remember to quote it or spaces may misbehave
#    mongoimport --db bus --collection bus <$f --jsonArray
#done
#mongodump --host 192.168.1.101 --port 27017 --collection bus --db bus --out /home/thuy/backup/
#mongorestore /home/thuy/backup/2016-09-18/bus6/
#mongo -host 192.168.1.102 bus --eval "db.bus.ensureIndex({'deviceId':1})"
#mongo -host 192.168.1.102 bus --eval "db.bus.ensureIndex({'date':1})"
#mongo -host 192.168.1.102 bus --eval "db.bus.ensureIndex({'deviceId':1,'date':1})"

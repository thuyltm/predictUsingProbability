#mongo bus --eval "db.dropDatabase()"
#cd "/home/thuy/Documents/data/2016-09-19-json/"
for f in /home/thuy/Documents/data/2016-09-19-json/*
  do 
    # cd "$folder"
    # for f in *.json; do
	    # do some stuff here with "$f"
	    # remember to quote it or spaces may misbehave
	    mongoimport --host 192.168.1.104 --port 27017 --db bus --collection bus <$f --jsonArray
    # done
  done;
#for f in *.json; do
    # do some stuff here with "$f"
    # remember to quote it or spaces may misbehave
#    mongoimport --db bus --collection bus <$f --jsonArray
#done
#mongodump --host 192.168.1.101 --port 27017 --collection bus --db bus --out /home/thuy/backup/
#mongorestore /home/thuy/backup/2016-09-18/bus6/

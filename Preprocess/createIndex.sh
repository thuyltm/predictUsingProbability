mongo -host 192.168.1.104 bus --eval "db.bus.ensureIndex({'deviceId':1})"
mongo -host 192.168.1.104 bus --eval "db.bus.ensureIndex({'deviceId':1,'date':1})"

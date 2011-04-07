#!/bin/bash
echo "db.runCommand( { addshard : 'rs1/`hostname`:27017', allowLocal : true } );" | mongo `hostname`:37017/admin
echo "db.runCommand( { addshard : 'rs2/`hostname`:27317', allowLocal : true } );" | mongo `hostname`:37017/admin
echo "db.runCommand( { enablesharding : 'logs' } );" | mongo `hostname`:37017/admin
echo "db.runCommand( { shardcollection : 'logs.entries', key : { _id : 1 } } );" | mongo `hostname`:37017/admin

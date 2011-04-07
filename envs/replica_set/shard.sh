#!/bin/bash

shutdown_nodes()
{
    kill $PID1
    kill $PID2
}
trap 'shutdown_nodes' INT

mkdir -p logs
mkdir -p data/config

mongod --port 37117 --dbpath ./data/config --logpath ./logs/config.log &
PID1=$!
sleep 2
mongos --port 37017 --configdb `hostname`:37117 --logpath ./logs/mongos.log &
PID2=$!
echo "^C will terminate all mongos and config db"
wait

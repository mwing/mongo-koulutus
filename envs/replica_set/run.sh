#!/bin/bash

shutdown_nodes()
{
    kill $PID1
    kill $PID2
    kill $PID3
}
trap 'shutdown_nodes' INT

mkdir -p logs
mkdir -p data/node1
mkdir -p data/node2
mkdir -p data/node3

mongod --replSet example --port 27017 --dbpath ./data/node1 --logpath ./logs/node1.log &
PID1=$!
mongod --replSet example --port 37017 --dbpath ./data/node2 --logpath ./logs/node2.log &
PID2=$!
mongod --replSet example --port 47017 --dbpath ./data/node3 --logpath ./logs/node3.log &
PID3=$!
echo "^C will terminate all nodes"
wait

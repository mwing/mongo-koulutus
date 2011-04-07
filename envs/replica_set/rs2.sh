#!/bin/bash

shutdown_nodes()
{
    kill $PID1
    kill $PID2
    kill $PID3
}
trap 'shutdown_nodes' INT

mkdir -p logs
mkdir -p data/rs2/node1
mkdir -p data/rs2/node2
mkdir -p data/rs2/node3

mongod --replSet rs2 --port 27317 --dbpath ./data/rs2/node1 --logpath ./logs/rs2_node1.log &
PID1=$!
mongod --replSet rs2 --port 27417 --dbpath ./data/rs2/node2 --logpath ./logs/rs2_node2.log &
PID2=$!
mongod --replSet rs2 --port 27517 --dbpath ./data/rs2/node3 --logpath ./logs/rs2_node3.log &
PID3=$!
echo "^C will terminate all nodes"
wait

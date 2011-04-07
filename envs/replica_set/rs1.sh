#!/bin/bash

shutdown_nodes()
{
    kill $PID1
    kill $PID2
    kill $PID3
}
trap 'shutdown_nodes' INT

mkdir -p logs
mkdir -p data/rs1/node1
mkdir -p data/rs1/node2
mkdir -p data/rs1/node3

mongod --replSet rs1 --port 27017 --dbpath ./data/rs1/node1 --logpath ./logs/rs1_node1.log &
PID1=$!
mongod --replSet rs1 --port 27117 --dbpath ./data/rs1/node2 --logpath ./logs/rs1_node2.log &
PID2=$!
mongod --replSet rs1 --port 27217 --dbpath ./data/rs1/node3 --logpath ./logs/rs1_node3.log &
PID3=$!
echo "^C will terminate all nodes"
wait

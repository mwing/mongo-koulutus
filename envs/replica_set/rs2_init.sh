#!/bin/bash
echo "rs.initiate()" | mongo --port 27317
echo "rs.add(\"`hostname`:27417\")" | mongo --port 27317
echo "rs.add(\"`hostname`:27517\")" | mongo --port 27317 

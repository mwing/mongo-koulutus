#!/bin/bash
echo "rs.initiate()" | mongo
echo "rs.add(\"`hostname`:27117\")" | mongo
echo "rs.add(\"`hostname`:27217\")" | mongo

#!/bin/bash
echo "rs.initiate()" | mongo
echo "rs.add(\"`hostname`:37017\")" | mongo
echo "rs.add(\"`hostname`:47017\")" | mongo

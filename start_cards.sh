#!/bin/bash

if [ "$1" != "" ]; then
	ECHO "java -jar bin/cards.jar $1"
	java -jar bin/cards.jar $1
else
	ECHO "java -jar bin/cards.jar"
	java -jar bin/cards.jar
fi

#!/usr/bin/env bash

cd /bungee
if [ ! -f config/template.yml ]; then
	echo "end" | java -jar BungeeCord.jar > /dev/null
	mkdir config
	cp config.yml config/template.yml
fi
cp config/template.yml config.yml
java -jar bungeecord-proxy-dependencies.jar
java -jar BungeeCord.jar

# bungee-proxy

## Running Container
```docker
docker run -d --name bungeecord-proxy \
-v /path/to/config:/bungee/config \
-v /var/run/docker.sock:/var/run/docker.sock:ro \
-p 25565:25577 syuchan1005/bungeecord-proxy:latest
```

## Container Env

|ENV Name|Type|Parameter|Default|
|:-------|:---|---------|:------|
|BC_LISTENER_NAME|String|optional|listeners[0]|
|BC_SERVER_NAME|String|required||
|BC_SERVER_HOST|String|required|| 
|BC_SERVER_MOTD|String|optional|<span style="background:#82540F;"><span style="color:#3ffe3f">Just anothor BungeeCord - Forced Host</span> <span style="color:#3ffefe">by bungee-proxy</span></span>|
|BC_SERVER_RESTRICTED|boolean|optional|false|

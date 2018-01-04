# bungee-proxy

## Running Container
```docker
docker run -d --name bungeecord-proxy \
-v /path/to/config:/bungee/config \
-v /var/run/docker.sock:/var/run/docker.sock:ro \
-p 25565:25577 syuchan1005/bungeecord-proxy:latest
```

## Container Env

if you use `BC_LISTENER_NAME`, put to `name` key in `config.yml -> listeners`

|ENV Name|Type|Flag|Default|
|:-------|:---|:---|:------|
|BC_LISTENER_NAME|String|optional|listeners[0]|
|BC_SERVER_NAME|String|required||
|BC_SERVER_HOST|String|required|| 
|BC_SERVER_MOTD|String|optional|Just another BungeeCord - Forced Host by bungee-proxy|
|BC_SERVER_RESTRICTED|boolean|optional|false|
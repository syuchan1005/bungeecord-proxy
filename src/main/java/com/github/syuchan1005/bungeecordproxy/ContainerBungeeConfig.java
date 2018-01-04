package com.github.syuchan1005.bungeecordproxy;

import com.github.dockerjava.api.model.Container;
import java.util.HashMap;
import java.util.Map;

public class ContainerBungeeConfig {
	private String listenerName;
	private String serverHost;
	private String serverName;
	private String serverMotd;
	private boolean restricted;

	public ContainerBungeeConfig(String[] envArray) {
		if (envArray == null) throw new IllegalArgumentException();
		HashMap<String, String> map = new HashMap<>();
		for (String env : envArray) {
			String[] split = env.split("=");
			map.put(split[0], split.length == 2 ? split[1] : null);
		}
		listenerName = map.get("BC_LISTENER_NAME");
		serverHost = map.get("BC_SERVER_HOST");
		serverName = map.get("BC_SERVER_NAME");
		serverMotd = map.getOrDefault("BC_SERVER_MOTD",
				"&aJust anothor BungeeCord - Forced Host &bby bungee-proxy");
		restricted = map.getOrDefault("BC_SERVER_RESTRICTED", "false").equals("true");
	}

	public String getListenerName() {
		return listenerName;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerHost() {
		return serverHost;
	}

	public Map<String, Object> toServersMap(String address) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("motd", serverMotd);
		hashMap.put("address", address + ":25565");
		hashMap.put("restricted", restricted);
		return hashMap;
	}
}

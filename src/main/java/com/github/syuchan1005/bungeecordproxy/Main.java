package com.github.syuchan1005.bungeecordproxy;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class Main {
	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();
		File configFile = new File("./config.yml");
		if (!configFile.exists()) return;
		Map<String, Object> load = yaml.load(new FileInputStream(configFile));
		DockerClient client = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();
		for (Container container : client.listContainersCmd().exec()) {
			InspectContainerResponse response = client.inspectContainerCmd(container.getId()).exec();
			ContainerBungeeConfig config = new ContainerBungeeConfig(response.getConfig().getEnv());
			if (config.getServerName() == null) continue;
			Map<String, Map<String, Object>> servers = (Map<String, Map<String, Object>>) load.get("servers");
			if (servers != null) {
				servers.put(config.getServerName(), config.toServersMap(response.getNetworkSettings().getIpAddress()));
			} else {
				servers = new LinkedHashMap<>();
				servers.put(config.getServerName(), config.toServersMap(response.getNetworkSettings().getIpAddress()));
				load.put("servers", servers);
			}
			List<Map<String, Object>> listeners = (List<Map<String, Object>>) load.get("listeners");
			if (listeners.size() == 0) return;
			Map<String, Object> listener = null;
			if (config.getListenerName() != null) {
				listener = listeners.stream()
						.filter(l -> l.get("name").equals(config.getListenerName()))
						.findFirst().get();
			}
			if (listener == null) listener = listeners.get(0);
			Map<String, String> forced_hosts = (Map<String, String>) listener.get("forced_hosts");
			if (forced_hosts != null) {
				forced_hosts.put(config.getServerHost(), config.getServerName());
			} else {
				forced_hosts = new LinkedHashMap<>();
				forced_hosts.put(config.getServerHost(), config.getServerName());
				listener.put("forced_hosts", forced_hosts);
			}
		}
		Files.write(yaml.dumpAsMap(load), configFile, StandardCharsets.UTF_8);
	}
}

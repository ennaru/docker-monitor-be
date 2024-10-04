package com.ennaru.monitor.docker.vo;

import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;

@AllArgsConstructor
public class DockerCompose {

    final String version;
    final LinkedHashMap<String, DockerComposeServices> services;

}

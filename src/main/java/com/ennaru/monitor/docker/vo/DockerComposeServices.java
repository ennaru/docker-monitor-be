package com.ennaru.monitor.docker.vo;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DockerComposeServices {

    String image;
    String container_name;
    List<String> ports;
    List<String> depends_on;
    List<String> networks;
    List<String> environment;
    String restart;
    List<String> volumes;

}

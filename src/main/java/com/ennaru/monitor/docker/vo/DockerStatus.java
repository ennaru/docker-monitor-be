package com.ennaru.monitor.docker.vo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DockerStatus {
    String BlockIO;
    String CPUPerc;
    String Container;
    String ID;
    String MemPerc;
    String MemUsage;
    String Name;
    String NetIO;
    String PIDs;
}
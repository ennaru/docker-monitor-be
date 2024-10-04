package com.ennaru.monitor.docker.vo;

import lombok.Data;

@Data
public class DockerProcess {

    String Command;
    String CreatedAt;
    String ID;
    String Image;
    String Labels;
    String LocalVolumes;
    String Mounts;
    String Names;
    String Networks;
    String Ports;
    String RunningFor;
    String Size;
    String State;
    String Stauts;

}

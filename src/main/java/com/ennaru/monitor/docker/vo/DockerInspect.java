package com.ennaru.monitor.docker.vo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DockerInspect {
    private String Id;
    private String Created;
    private String Path;
    private List<String> Args;
    private State State;
    private String Image;
    private String ResolvConfPath;
    private String HostnamePath;
    private String HostsPath;
    private String LogPath;
    private String Name;
    private String RestartCount;
    private String Driver;
    private String Platform;
    private String MountLabel;
    private String ProcessLabel;
    private String AppArmorProfile;
    private String ExecIDs;
    private LinkedHashMap<String, Object> HostConfig;
    private LinkedHashMap<String, Object> GraphDriver;
    private List<Mounts> Mounts;
    private LinkedHashMap<String, Object> Config;
    private LinkedHashMap<String, Object> NetworkSettings;

    @Data
    public static class State {
        private String Status;
        private String Running;
        private String Paused;
        private String Restarting;
        private String OOMKilled;
        private String Dead;
        private String Pid;
        private String ExitCode;
        private String Error;
        private String StartedAt;
        private String FinishedAt;
    }

    @Data
    public static class Mounts {
        private String Type;
        private String Source;
        private String Destination;
        private String Mode;
        private boolean RW;
        private String Propagation;
    }

}

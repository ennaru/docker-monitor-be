package com.ennaru.monitor.docker.controller;

import com.ennaru.monitor.docker.annotation.ContainerId;
import com.ennaru.monitor.docker.service.MonitorService;
import com.ennaru.monitor.docker.vo.DockerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class MonitorController {

    private final MonitorService monitorService;
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/status/simple")
    public DockerResponse statusSimple() {
        return monitorService.statusSimple();
    }

    @GetMapping("/status/all")
    public DockerResponse statusAll() {
        return monitorService.statusAll();
    }

    @ContainerId
    @GetMapping("/status/{containerId}")
    public DockerResponse statusByContainerId(
            @PathVariable String containerId) {
        return monitorService.status(containerId);
    }

    @ContainerId
    @GetMapping("/monitor/{containerId}")
    public DockerResponse monitorByContainerId(
            @PathVariable String containerId) {
        return monitorService.monitor(containerId);
    }

    @ContainerId
    @GetMapping("/restart/{containerId}")
    public DockerResponse restartByContainerId(
            @PathVariable String containerId) {
        return DockerResponse.success();
    }

    @ContainerId
    @GetMapping("/logs/save/{containerId}")
    public DockerResponse saveLogByContainerId(
            @PathVariable String containerId) {
        return DockerResponse.success();
    }

    @ContainerId
    @GetMapping("/logs/get/{containerId}")
    private DockerResponse getLogByContainerId(
            @PathVariable String containerId) {
        return DockerResponse.success();
    }

}

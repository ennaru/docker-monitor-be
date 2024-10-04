package com.ennaru.monitor.docker.controller;

import com.ennaru.monitor.docker.service.ComposeService;
import com.ennaru.monitor.docker.vo.DockerCompose;
import com.ennaru.monitor.docker.vo.DockerComposeServices;
import com.ennaru.monitor.docker.vo.DockerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComposeController {

    private final ComposeService composeService;
    public ComposeController(ComposeService composeService) {
        this.composeService = composeService;
    }

//    @PostMapping("/compose/create")
//    public String create(@RequestBody DockerComposeServices dockerComposeServices) {
//        return composeService.createYamlFile(dockerComposeServices);
//    }

    @PostMapping("/compose/create/v2")
    public DockerResponse createV2(@RequestBody DockerCompose dockerCompose) {
        return composeService.createComposeFile(dockerCompose);
    }

//    @PostMapping("/compose/deploy")
//    public DockerResponse deploy() {
//        return composeService.deploy();
//    }

}

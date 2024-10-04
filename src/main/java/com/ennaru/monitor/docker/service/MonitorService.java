package com.ennaru.monitor.docker.service;

import com.ennaru.monitor.commons.template.CommandTemplate;
import com.ennaru.monitor.docker.vo.DockerInspect;
import com.ennaru.monitor.docker.vo.DockerProcess;
import com.ennaru.monitor.docker.vo.DockerResponse;
import com.ennaru.monitor.docker.vo.DockerStatus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService {

    private final CommandTemplate commandTemplate;
    public MonitorService(CommandTemplate commandTemplate) {
        this.commandTemplate = commandTemplate;
    }

    public DockerResponse statusSimple() {
        String[] command = commandTemplate.executeCommand("docker ps --format '{\"id\": \"{{.ID}}\", \"state\": \"{{.State}}\" }'");
        Gson gson = new Gson();
        return DockerResponse.success(command);
    }

    public DockerResponse statusAll() {
        String[] results = commandTemplate.executeCommand("docker ps --no-trunc --format json");
        Gson gson = new Gson();

        // docker ps는 list형으로 받아옴
        if(ObjectUtils.isEmpty(results)) {
            return DockerResponse.success("[]");
        } else {
            List<DockerProcess> list = new ArrayList<>();
            for(String result : results) {
                list.add(gson.fromJson(result, DockerProcess.class));
            }
            return DockerResponse.success(list);
        }
    }

    public DockerResponse status(String containerId) {
        String command = commandTemplate.executeCommand("docker inspect --format json " + containerId)[0];

        // command[0]가 'empty' 일 때 container_id가 없음
        if(ObjectUtils.isEmpty(command)) {
            throw new IllegalArgumentException("서버에서 container_id를 찾을 수 없습니다.");
        }

        // [{}] 형식으로 반환
        Gson gson = new Gson();
        JsonArray array = gson.fromJson(command, JsonArray.class);

        // 1번째 요소 추출
        return DockerResponse.success(gson.fromJson(array.get(0), DockerInspect.class));
    }

    public DockerResponse monitor(String containerId) {
        String command = commandTemplate.executeCommand("docker stats --no-stream --format json")[0];

        // command[0]가 'empty' 일 때 container_id가 없음
        if(ObjectUtils.isEmpty(command)) {
            throw new IllegalArgumentException("서버에서 container_id를 찾을 수 없습니다.");
        }

        // json parse
        Gson gson = new Gson();
        DockerStatus process = gson.fromJson(command, DockerStatus.class);
        return DockerResponse.success(process);
    }

}

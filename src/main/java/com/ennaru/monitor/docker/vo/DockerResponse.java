package com.ennaru.monitor.docker.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class DockerResponse {

    private String result;
    private String message;
    private Object data;

    public static DockerResponse success() {
        return success(null);
    }

    public static DockerResponse success(Object data) {
        return new DockerResponse("0000", "성공", data);
    }

}

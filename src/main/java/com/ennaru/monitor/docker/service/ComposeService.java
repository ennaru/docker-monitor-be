package com.ennaru.monitor.docker.service;

import com.ennaru.monitor.commons.CommonUtility;
import com.ennaru.monitor.docker.vo.DockerCompose;
import com.ennaru.monitor.docker.vo.DockerResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ComposeService {

    public DockerResponse createComposeFile(DockerCompose dockerCompose) {

        try {
            // docker-compose 파일 읽기
            File file = new File("docker-compose.yml");

            // 기존 파일 검출 시 백업파일로 변경
            if(file.exists()) {
                if(file.renameTo(new File("docker-compose.yml_" + CommonUtility.dateFormat()))) {
                    throw new IOException("파일명을 변경할 수 없습니다.");
                }
                file = new File("docker-compose.yml");
            }

            // [docker-compose.yml] 파일 신규 생성
            if(!file.createNewFile()) {
                throw new IOException("파일을 생성할 수 없습니다.");
            }

            // 파일 작성
            if(file.canWrite()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(createYamlFile(dockerCompose));
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not create docker compose file", e);
        }

        return DockerResponse.success();
    }


    /**
     * DockerCompose 오브젝트를 compose.yml 문자열로 변환시키기
     * @param obj DockerCompose
     * @return docker-compose.yml에 넣을 텍스트
     */
    public String createYamlFile(Object obj) {
        return this.createYamlFile(obj, 0);
    }

    private String createYamlFile(Object obj, int depth) {
        StringBuilder sb = new StringBuilder();

        try {
            // reflection API를 통한 타입 스캔
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                // compose YAML 파일 생성 시 key는 존재하나 value가 없는 경우 생략
                if (isEmptyForYaml(field.get(obj))) {
                    continue;
                }

                // yaml 파일의 개행 탭은 ' ' 2번
                sb.append("  ".repeat(Math.max(0, depth)));

                if (field.getType() == String.class) {
                    /* String 타입 출력
                       key: value
                     */
                    sb.append(field.getName()).append(": ").append(valueForYaml(field.get(obj))).append("\n");
                } else if (field.getType() == List.class) {
                    /* List<String> 출력
                       key:
                        - value
                        - value2
                     */
                    List<?> list = (List<?>) field.get(obj);
                    sb.append(field.getName()).append(": ").append("\n");
                    list.forEach((el) -> {
                        if (el instanceof String) {
                            sb.append("  ".repeat(Math.max(0, depth)));
                            sb.append(" - ").append(valueForYaml(el)).append("\n");
                        }
                    });
                } else if(field.getType() == LinkedHashMap.class) {
                    /* LinkedHashMap<String, ?> 출력
                       key:
                         value
                     */
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) field.get(obj);
                    sb.append(field.getName()).append(": ").append("\n");
                    map.forEach((k, v) -> {
                        sb.append("  ".repeat(Math.max(0, depth + 1)));
                        sb.append(k).append(": ").append("\n");
                        sb.append(createYamlFile(v, depth + 2));
                    });
                }

            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    /**
     * Yaml 파일에 넣을 value가 empty인지 체크
     * @param obj 값을 확인할 오브젝트
     * <br/> String 입력 시 -> isEmpty
     * <br/>List -> get(0) -> isEmpty
     */
    private boolean isEmptyForYaml(Object obj) {

        if(obj == null) {
            return true;
        } else if (obj instanceof String) {
            return ((String) obj).isEmpty();
        } else if (obj instanceof List<?> obj2) {
            return obj2.size() == 1 && ObjectUtils.isEmpty(obj2.get(0));
        }
        return false;
    }

    /**
     * 문자열에 큰따옴표 붙이기
     * @param str 문자열
     * @return 'str'
     */
    private String valueForYaml(Object str) {
        return "\"" + str + "\"";
    }
}

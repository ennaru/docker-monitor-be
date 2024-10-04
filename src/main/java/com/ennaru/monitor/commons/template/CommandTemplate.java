package com.ennaru.monitor.commons.template;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class CommandTemplate {

    //private ChannelExec channelExec;
    private Session session;

    @Value("${jsch.attempt}")
    private int ATTEMPT;

    @Value("${jsch.host}")
    private String HOST;

    @Value("${jsch.username}")
    private String USERNAME;

    @Value("${jsch.password}")
    private String PASSWORD;

    @PreDestroy
    void destroy() {
        session.disconnect();
    }

    public Session setSession() {

        if(this.session != null) {
            return this.session;
        }

        JSch jsch = new JSch();
        try {
            this.session = jsch.getSession(USERNAME, HOST);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.setPassword(PASSWORD);
            this.session.connect();

            return this.session;
        } catch (JSchException e) {
            this.session = null;
            throw new IllegalStateException("인증에 실패하였습니다.");
        }
    }

//    private ChannelExec getChannelExec() {
//
//        // session nullable?
//        Session session = setSession();
//
//        // session connect
//        try {
//            int i = 0;
//            do {
//                if(!session.isConnected()) {
//                    session.connect();
//                }
//
//                if(session.isConnected()) {
//                    log.info("session connected");
//                    var channel = session.openChannel("exec");
//
//                    return (ChannelExec) channel;
//                }
//
//            } while(i++ < ATTEMPT);
//
//            throw new IllegalStateException("세션에 연결할 수 없습니다.");
//
//        } catch (JSchException e) {
//            throw new IllegalStateException(e.getMessage());
//        }
//    }


    public String[] executeCommand(String command) {

        var session = setSession();

        try {
            var channelExec = ((ChannelExec) session.openChannel("exec"));
            channelExec.setCommand(command);
            channelExec.connect();

            var results = StreamUtils.copyToString(channelExec.getInputStream(), StandardCharsets.UTF_8);
            channelExec.disconnect();

            return results.split("\n");

        } catch (JSchException | IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

}

package com.ennaru.monitor;

import com.jcraft.jsch.*;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CommandTest {

    @Test
    public void jschTest() {

        JSch jSch = new JSch();
        jSch.setConfig("StrictHostKeyChecking", "no");

        try {
            Session session = jSch.getSession("ennaru", "127.0.0.1");
            session.setPassword("fksfks11!!");

            session.connect(10000);

            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;

            channelExec.setCommand("docker -v");
            channelExec.connect(2000);

            System.out.println(StreamUtils.copyToString(channelExec.getInputStream(), StandardCharsets.UTF_8));

            System.out.println("Status: " + channelExec.getExitStatus());


        } catch(JSchException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void commandTest() {

        final String COMMAND = "ping 127.0.0.1";

        try {
            Runtime rt = Runtime.getRuntime();
            Process ps = rt.exec(COMMAND);

            System.out.println(StreamUtils.copyToString(ps.getInputStream(), StandardCharsets.UTF_8));

//        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process prcss = runtime.exec("dir");
//            System.out.println(StreamUtils.copyToString(prcss.getInputStream(), StandardCharsets.UTF_8));
//            runtime.gc();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }
}

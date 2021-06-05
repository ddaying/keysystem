package com.ddaying.kakaopay.keysystem.config.redis;


import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Profile({"local", "memory"})
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        // 테스트시 여러 context 생성으로 인한 redis port 충돌 처리
        int port = isRunning(executeGrepProcess(redisPort)) ? getAvailablePort() : redisPort;

        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    // 사용 가능한 포트 조회
    public int getAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcess(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new ApiException(ApiStatus.INTERNAL_ERROR, "사용 가능한 포트가 존재하지 않습니다.");
    }

    // 해당 포트를 사용중인 프로세스 확인
    private Process executeGrepProcess(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN | grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    // 프로세스 실행 여부 확인
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception ignored) {
        }

        return !StringUtils.isEmpty(pidInfo.toString());
    }
}

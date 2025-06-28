package com.investment.advisor.controller.health;

import com.investment.advisor.api.health.DatabaseHealthApi;
import com.investment.advisor.service.impl.DatabaseHealthService;
import com.investment.common.health.dto.ConnectionPoolInfo;
import com.investment.common.health.dto.DatabaseHealthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DatabaseHealthController implements DatabaseHealthApi {

    private final DatabaseHealthService databaseHealthService;

    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> checkDatabaseHealth() {
        Map<String, Object> response = new HashMap<>();
        boolean isHealthy = databaseHealthService.isDatabaseHealthy();

        response.put("status", isHealthy ? "UP" : "DOWN");
        response.put("database", isHealthy ? "HEALTHY" : "UNHEALTHY");
        response.put("timestamp", System.currentTimeMillis());

        HttpStatus status = isHealthy ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/db/detailed")
    public ResponseEntity<DatabaseHealthInfo> getDetailedDatabaseHealth() {
        DatabaseHealthInfo healthInfo = databaseHealthService.getDatabaseHealthInfo();

        HttpStatus status = healthInfo.isHealthy() ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(healthInfo);
    }

    @GetMapping("/db/pool")
    public ResponseEntity<ConnectionPoolInfo> getConnectionPoolInfo() {
        ConnectionPoolInfo poolInfo = databaseHealthService.getConnectionPoolInfo();
        return ResponseEntity.ok(poolInfo);
    }

    @GetMapping("/db/test")
    public ResponseEntity<Map<String, Object>> testConnection(
            @RequestParam(defaultValue = "5") int timeoutSeconds) {

        Map<String, Object> response = new HashMap<>();
        boolean isValid = databaseHealthService.testConnectionWithTimeout(timeoutSeconds);

        response.put("connectionValid", isValid);
        response.put("timeoutSeconds", timeoutSeconds);
        response.put("timestamp", System.currentTimeMillis());

        HttpStatus status = isValid ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(response);
    }
}
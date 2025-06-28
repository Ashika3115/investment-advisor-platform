package com.investment.advisor.api.health;

import com.investment.common.health.dto.ConnectionPoolInfo;
import com.investment.common.health.dto.DatabaseHealthInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Database Health", description = "APIs for monitoring database health and connection pool status")
@RequestMapping("/api/health")
public interface DatabaseHealthApi {

    @Operation(summary = "Check database health", description = "Returns a simple health status of the database")
    ResponseEntity<Map<String, Object>> checkDatabaseHealth();

    /**
     * Detailed database health information
     */
    @Operation(summary = "Get detailed database health", description = "Returns detailed health information about the database")
    ResponseEntity<DatabaseHealthInfo> getDetailedDatabaseHealth();

    /**
     * Connection pool information
     */
    @Operation(summary = "Get connection pool info", description = "Returns information about the database connection pool")
    ResponseEntity<ConnectionPoolInfo> getConnectionPoolInfo();

    /**
     * Test connection with custom timeout
     */
    @Operation(summary = "Test database connection", description = "Tests the database connection with a custom timeout in seconds")
    ResponseEntity<Map<String, Object>> testConnection(int timeoutSeconds);
}


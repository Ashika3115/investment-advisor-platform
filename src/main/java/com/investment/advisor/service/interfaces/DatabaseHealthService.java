package com.investment.advisor.service.interfaces;

import com.investment.common.health.dto.ConnectionPoolInfo;
import com.investment.common.health.dto.DatabaseHealthInfo;

public interface DatabaseHealthService {

    /**
     * Check if database connection is available
     */
    boolean isDatabaseHealthy();

    /**
     * Get detailed database connection information
     */
    DatabaseHealthInfo getDatabaseHealthInfo();

    /**
     * Test database connection with timeout
     */
    boolean testConnectionWithTimeout(int timeoutSeconds);

    /**
     * Get active connection count (if using HikariCP)
     */
    ConnectionPoolInfo getConnectionPoolInfo();
}

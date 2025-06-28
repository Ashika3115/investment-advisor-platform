package com.investment.advisor.service.impl;

import com.investment.common.health.dto.ConnectionPoolInfo;
import com.investment.common.health.dto.DatabaseHealthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseHealthService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthService.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Check if database connection is available
     */
    public boolean isDatabaseHealthy() {
        try {
            // Simple query to test connection
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.debug("Database health check: HEALTHY");
            return true;
        } catch (DataAccessException e) {
            logger.error("Database health check: UNHEALTHY - {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get detailed database connection information
     */
    public DatabaseHealthInfo getDatabaseHealthInfo() {
        DatabaseHealthInfo.DatabaseHealthInfoBuilder builder = DatabaseHealthInfo.builder();

        try (Connection connection = dataSource.getConnection()) {
            builder.isHealthy(true)
                    .url(connection.getMetaData().getURL())
                    .driverName(connection.getMetaData().getDriverName())
                    .driverVersion(connection.getMetaData().getDriverVersion())
                    .databaseProductName(connection.getMetaData().getDatabaseProductName())
                    .databaseProductVersion(connection.getMetaData().getDatabaseProductVersion())
                    .isAutoCommit(connection.getAutoCommit())
                    .isReadOnly(connection.isReadOnly())
                    .transactionIsolation(connection.getTransactionIsolation());

            logger.info("Database connection successful");

        } catch (SQLException e) {
            logger.error("Failed to get database connection: {}", e.getMessage());
            builder.isHealthy(false)
                    .errorMessage(e.getMessage())
                    .sqlState(e.getSQLState())
                    .errorCode(e.getErrorCode());
        }

        return builder.build();
    }

    /**
     * Test database connection with timeout
     */
    public boolean testConnectionWithTimeout(int timeoutSeconds) {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(timeoutSeconds);
        } catch (SQLException e) {
            logger.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get active connection count (if using HikariCP)
     */
    public ConnectionPoolInfo getConnectionPoolInfo() {
        ConnectionPoolInfo.ConnectionPoolInfoBuilder builder = ConnectionPoolInfo.builder();

        try {
            // Check if it's HikariCP
            if (dataSource.getClass().getName().contains("HikariDataSource")) {
                com.zaxxer.hikari.HikariDataSource hikariDS = (com.zaxxer.hikari.HikariDataSource) dataSource;
                com.zaxxer.hikari.HikariPoolMXBean poolBean = hikariDS.getHikariPoolMXBean();

                builder.totalConnections(poolBean.getTotalConnections())
                        .activeConnections(poolBean.getActiveConnections())
                        .idleConnections(poolBean.getIdleConnections())
                        .threadsAwaitingConnection(poolBean.getThreadsAwaitingConnection())
                        .maximumPoolSize(hikariDS.getMaximumPoolSize())
                        .minimumIdle(hikariDS.getMinimumIdle());
            }
        } catch (Exception e) {
            logger.warn("Could not retrieve connection pool information: {}", e.getMessage());
            builder.errorMessage("Could not retrieve pool info: " + e.getMessage());
        }

        return builder.build();
    }
}
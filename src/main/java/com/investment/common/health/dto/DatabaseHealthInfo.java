package com.investment.common.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseHealthInfo {
    private boolean isHealthy;
    private String url;
    private String driverName;
    private String driverVersion;
    private String databaseProductName;
    private String databaseProductVersion;
    private boolean isAutoCommit;
    private boolean isReadOnly;
    private int transactionIsolation;
    private String errorMessage;
    private String sqlState;
    private int errorCode;
}
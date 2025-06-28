package com.investment.common.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionPoolInfo {
    private int totalConnections;
    private int activeConnections;
    private int idleConnections;
    private int threadsAwaitingConnection;
    private int maximumPoolSize;
    private int minimumIdle;
    private String errorMessage;
}
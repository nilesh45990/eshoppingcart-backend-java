/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dto;

/**
 *
 * @author nileshkumar
 */
public class ClientDetailDto {

    private Integer clientId;
    private String clientName;
    private String clientEmail;
    private String clientContactNo;
    private String clientEntDate;
    private String clientStatus;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientContactNo() {
        return clientContactNo;
    }

    public void setClientContactNo(String clientContactNo) {
        this.clientContactNo = clientContactNo;
    }

    public String getClientEntDate() {
        return clientEntDate;
    }

    public void setClientEntDate(String clientEntDate) {
        this.clientEntDate = clientEntDate;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }
}

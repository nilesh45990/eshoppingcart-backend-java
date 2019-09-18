/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.utils;

/**
 *
 * @author Mishti
 */
public class AppEnum {

    public enum OrderStages {

        CANCELED("Canceled"), REQUESTED("Requested"), CONFIRMED("Confirmed"), IN_PROCESS("In process"), DISPATCHED("Dispatched"), DELIVERED("Delivered");

        private String orderStatus;

        OrderStages(String stage) {
            this.orderStatus = stage;
        }

        public String getStage() {
            return this.orderStatus;
        }
    }

}

package com.example.bean;

import lombok.Data;

@Data
public class LogisticsVo {
    private String logisticsNumber;//物流号
    private String orderNumber; //订单号
    private String vehiclecar; //车牌号
    private String employeename; //配送员
    private String shoujianren; //收件人
    private String phone;   //联系电话
    private String address; //
    private String statuslogistics;//

    public LogisticsVo(String logisticsNumber, String orderNumber, String vehiclecar, String employeename, String shoujianren, String phone, String address, String statuslogistics) {
        this.logisticsNumber = logisticsNumber;
        this.orderNumber = orderNumber;
        this.vehiclecar = vehiclecar;
        this.employeename = employeename;
        this.shoujianren = shoujianren;
        this.phone = phone;
        this.address = address;
        this.statuslogistics = statuslogistics;
    }
}

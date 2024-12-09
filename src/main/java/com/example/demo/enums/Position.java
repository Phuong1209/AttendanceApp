package com.example.demo.enums;


public enum Position {
    ADMIN ("上司"),
    USER("部下");
    private String msg;
    Position(String msg){
        this.msg=msg;
    }
    public String des(){
        return this.msg;
    }


}
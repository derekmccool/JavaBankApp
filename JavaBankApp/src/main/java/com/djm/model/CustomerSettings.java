package com.djm.model;

public class CustomerSettings {
    String borderSymbol1;
    String borderSymbol2;
    String borderSymbol3;
    int consoleWidth;
    
    public CustomerSettings() {
        this.borderSymbol1 = "-";
        this.borderSymbol2 = "=";
        this.borderSymbol3 = "=";
        this.consoleWidth = 50;
    }
    public String getBorderSymbol1() {
        return this.borderSymbol1;
    }

    public void setBorderSymbol1(String borderSymbol1) {
        this.borderSymbol1 = borderSymbol1;
    }

    public String getBorderSymbol2() {
        return this.borderSymbol2;
    }

    public void setBorderSymbol2(String borderSymbol2) {
        this.borderSymbol2 = borderSymbol2;
    }

    public String getBorderSymbol3() {
        return this.borderSymbol3;
    }

    public void setBorderSymbol3(String borderSymbol3) {
        this.borderSymbol3 = borderSymbol3;
    }

    public int getConsoleWidth() {
        return this.consoleWidth;
    }

    public void setConsoleWidth(int consoleWidth) {
        this.consoleWidth = consoleWidth;
    }


}

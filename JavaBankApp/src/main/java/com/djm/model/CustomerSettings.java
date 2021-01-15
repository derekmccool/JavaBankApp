package com.djm.model;

public class CustomerSettings {
    private final int MINWIDTH = 100;
    private final int MAXWIDTH = 250;

    private char borderSymbol1;
    private char borderSymbol2;
    private char borderSymbol3;
    int consoleWidth;

    public CustomerSettings() {
        this.borderSymbol1 = '-';
        this.borderSymbol2 = '=';
        this.borderSymbol3 = '=';
        this.consoleWidth = 100;
    }

    public void resetToDefault(){
        this.borderSymbol1 = '-';
        this.borderSymbol2 = '=';
        this.borderSymbol3 = '=';
        this.consoleWidth = 100;
    }
    public char getBorderSymbol1() {
        return this.borderSymbol1;
    }

    public void setBorderSymbol1(char borderSymbol1) {
        this.borderSymbol1 = borderSymbol1;
    }

    public char getBorderSymbol2() {
        return this.borderSymbol2;
    }

    public void setBorderSymbol2(char borderSymbol2) {
        this.borderSymbol2 = borderSymbol2;
    }

    public char getBorderSymbol3() {
        return this.borderSymbol3;
    }

    public void setBorderSymbol3(char borderSymbol3) {
        this.borderSymbol3 = borderSymbol3;
    }

    public int getConsoleWidth() {
        return this.consoleWidth;
    }

    public void setConsoleWidth(int consoleWidth) throws Exception {

        if(consoleWidth > MAXWIDTH){
            throw new Exception("CONSOLE WIDTH CANNOT EXCEED " + MAXWIDTH);
        }else if(consoleWidth < MINWIDTH ){
            throw new Exception("CONSOLE WIDTH CANNOT BE LESS THAN " + MINWIDTH);
        }else{
            this.consoleWidth = consoleWidth;
        }
    }


}

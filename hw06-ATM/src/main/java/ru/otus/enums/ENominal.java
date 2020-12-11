package ru.otus.enums;

public enum ENominal {
    N50R(50)
    ,N100R(100)
    ,N200R(200)
    ,N500R(500)
    ,N1000R(1000)
    ,N2000R(2000)
    ,N5000R(5000);

    private int value;

    private ENominal(int value) {
        this.value = value;
    }

    public int toInt(){
        return value;
    }

    public String toString(){
        return String.format("%d рублей",value);
    }
}

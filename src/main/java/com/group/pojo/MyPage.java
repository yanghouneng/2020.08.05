package com.group.pojo;

public class MyPage {

    private int page = 1;
    private int row = 2;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "page=" + page +
                ", row=" + row +
                '}';
    }
}

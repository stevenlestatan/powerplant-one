package com.example.androidbasedcourseware.datalayer;

public class ItemsCaterer {
    private String studentId;
    private int itemNo;
    private String studentAnswer;

    public ItemsCaterer(String studentId, int itemNo, String studentAnswer) {
        this.studentId = studentId;
        this.itemNo = itemNo;
        this.studentAnswer = studentAnswer;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }
}

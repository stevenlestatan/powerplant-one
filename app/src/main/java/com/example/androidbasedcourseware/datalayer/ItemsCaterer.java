package com.example.androidbasedcourseware.datalayer;

public class ItemsCaterer {
    private int studentId;
    private int itemNo;
    private String studentAnswer;

    public ItemsCaterer(int studentId, int itemNo, String studentAnswer) {
        this.studentId = studentId;
        this.itemNo = itemNo;
        this.studentAnswer = studentAnswer;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }
}

package entity;

import annotation.Column;
import annotation.Table;

@Table("film")
public class Film {
    @Column("fid")
    private int fid;
    @Column("fname")
    private String fname;
    @Column("ftype")
    private  String ftype;
    @Column("dname")
    private String dname;
    @Column("length")
    private int length;

    @Column("grade")
    private int grade;

    public int getFid() {
        return fid;
    }

    public String getFname() {
        return fname;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getDname() {
        return dname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getLength() {
        return length;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }



    public void setLength(int length) {
        this.length = length;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }





    public String toString(){
        return "film { fid = " + fid +
        " fname = " + fname +
        " ftype = " + ftype +
        " dname = " + dname +
        " length = " + length +

        " grade = " + grade +
        " }";
    }

}

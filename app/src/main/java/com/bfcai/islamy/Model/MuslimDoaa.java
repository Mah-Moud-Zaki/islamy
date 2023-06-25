package com.bfcai.islamy.Model;

public class MuslimDoaa {
    String title;
    String subtitle;
    String num;
    String share;
    String copy;
    String listen;

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public  MuslimDoaa(){}
    public MuslimDoaa(String title, String subtitle, String num,  String share,String copy,String listen) {
        this.title = title;
        this.subtitle = subtitle;
        this.num = num;
        this.share = share;
        this.copy= copy;
        this.listen=listen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

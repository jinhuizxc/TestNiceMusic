package com.example.jh.testnicemusic.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author lzx
 * @date 2018/1/22
 */

public class SingerInfo {
    private String name;
    @SerializedName("avatar_s500")
    private String avatar;
    private String intro;
    private String country;
    @SerializedName("songs_total")
    private String songsTotal;
    private String birth;
    private String company;

    public String getNickname() {
        return name;
    }

    public void setNickname(String nickname) {
        this.name = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSongsTotal() {
        return songsTotal;
    }

    public void setSongsTotal(String songsTotal) {
        this.songsTotal = songsTotal;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

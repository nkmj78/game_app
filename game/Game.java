package edu.android.appgame.game;

public class Game {

    private String gName;   // 게임 이름
    private String gDesc;   // 게임 설명
    private int photoId;    // 게임 이미지

    public Game(String gName, String gDesc, int photoId) {
        this.gName = gName;
        this.gDesc = gDesc;
        this.photoId = photoId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgDesc() {
        return gDesc;
    }

    public void setgDesc(String gDesc) {
        this.gDesc = gDesc;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gName='" + gName + '\'' +
                ", gDesc='" + gDesc + '\'' +
                ", photoId=" + photoId +
                '}';
    }
} // end class Game

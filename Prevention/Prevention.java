package edu.android.appgame.Prevention;

public class Prevention {
    private String pName; // 예방법 이름
    private String pDesc; // 예방법 설명
    private int photoId;

    public Prevention(String pName, String pDesc, int photoId) {
        this.pName = pName;
        this.pDesc = pDesc;
        this.photoId = photoId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}

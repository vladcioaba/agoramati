package com.agoramati.downloader.vo;

public class AvatarResponseVO {
    private String avatar_id;
    private String avatar_url;

    public String getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(String avatar_id) {
        this.avatar_id = avatar_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public AvatarResponseVO(String avatarId, String avatarUrl) {
        this.avatar_id = avatarId;
        this.avatar_url = avatarUrl;
    }
}

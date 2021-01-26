package com.agoramati.downloader.vo;

public class AvatarResponseVO {
    private String avatarId;
    private String avatarUrl;

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatar_id) {
        this.avatarId = avatar_id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatarUrl = avatar_url;
    }

    public AvatarResponseVO(String avatarId, String avatarUrl) {
        this.avatarId = avatarId;
        this.avatarUrl = avatarUrl;
    }
}

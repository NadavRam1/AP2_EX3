package com.example.ap2_ex3;

public class Chat {

    private String displayName;
    private int profilePic;
    private String lastMessage;
    private String lastMessageTime;

    public Chat(String displayName, int profilePic, String lastMessage, String lastMessageTime) {
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void select() {
        displayName = displayName + " selected";
    }
}

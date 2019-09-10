package com.alvin.sidebar.model;

import com.alvin.sidebar.util.CharacterParserUtil;

import java.util.Comparator;

public class FriendEntity {
    private String userId;
    private String userName;
    private String headUrl;
    private int userType;//

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public static final Comparator<FriendEntity> COMPARATOR = new Comparator<FriendEntity>() {
        @Override
        public int compare(FriendEntity o1, FriendEntity o2) {
            String key1 = CharacterParserUtil.getSelling(o1.getUserName());
            String key2 = CharacterParserUtil.getSelling(o2.getUserName());
            String sortLetter1 = CharacterParserUtil.getSortLetterBySortKey(key1);
            String sortLetter2 = CharacterParserUtil.getSortLetterBySortKey(key2);
            return sortLetter1.compareTo(sortLetter2);
        }
    };
}

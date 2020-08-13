package com.tencentcloud.spring.boot.tim.req.group;

import java.util.List;

public class GroupMember {
    private String groupId;
    private List<String> memberToDelAccount;
    private List<AppMember> memberList;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setMemberToDelAccount(List<String> memberToDelAccount) {
        this.memberToDelAccount = memberToDelAccount;
    }

    public List<String> getMemberToDelAccount() {
        return memberToDelAccount;
    }

    public void setMemberList(List<AppMember> memberList) {
        this.memberList = memberList;
    }

    public List<AppMember> getMemberList() {
        return memberList;
    }
}

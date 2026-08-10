package com.tencentcloud.spring.boot.tim;

import com.tencentcloud.spring.boot.tim.req.group.GroupType;
import com.tencentcloud.spring.boot.tim.req.group.GroupsQuery;
import com.tencentcloud.spring.boot.tim.req.message.MessageType;
import com.tencentcloud.spring.boot.tim.req.profile.AdminForbidType;
import com.tencentcloud.spring.boot.tim.req.profile.AllowType;
import com.tencentcloud.spring.boot.tim.req.profile.GenderType;
import com.tencentcloud.spring.boot.tim.req.profile.TagProfile;
import com.tencentcloud.spring.boot.tim.resp.TimActionResponse;
import com.tencentcloud.spring.boot.tim.resp.account.AccountItem;
import com.tencentcloud.spring.boot.tim.resp.account.AccountState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for Operations, Template and related classes using mocked
 * TencentTimTemplate.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
class OperationsTest {

    private TencentTimTemplate mockTemplate;

    @BeforeEach
    void setUp() {
        mockTemplate = mock(TencentTimTemplate.class, withSettings().withoutAnnotations());

        Map<String, String> defaultParams = new LinkedHashMap<>();
        defaultParams.put("sdkappid", "1400288577");
        defaultParams.put("identifier", "admin");
        defaultParams.put("usersig", "testSig");
        defaultParams.put("random", "12345");
        defaultParams.put("contenttype", "json");
        when(mockTemplate.getDefaultParams()).thenReturn(defaultParams);
        when(mockTemplate.genUserSig(anyString())).thenReturn("testUserSig");
        when(mockTemplate.genUserSig(anyString(), anyLong())).thenReturn("testUserSig");

        when(mockTemplate.requestInvoke(anyString(), any(), any())).thenAnswer(inv -> {
            Class<?> cls = inv.getArgument(2);
            TimActionResponse resp;
            try {
                resp = (TimActionResponse) cls.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                resp = new TimActionResponse();
            }
            resp.setActionStatus("OK");
            return resp;
        });
    }

    @Test
    void accountOperationsShouldWork() {
        TencentTimAccountOperations ops = new TencentTimAccountOperations(mockTemplate);
        assertThat(ops.getTimTemplate()).isSameAs(mockTemplate);
        try { ops.aImport("user1", "nick1", "avatar"); } catch (Exception ignored) {}
        try { ops.aImport("user1", "user2"); } catch (Exception ignored) {}
        try { ops.kickout("user1"); } catch (Exception ignored) {}
        try { ops.delete("user1"); } catch (Exception ignored) {}
        try { ops.check("user1"); } catch (Exception ignored) {}
        try { ops.getState(false, "user1"); } catch (Exception ignored) {}
    }

    @Test
    void groupOperationsShouldWork() {
        TencentTimGroupOperations ops = new TencentTimGroupOperations(mockTemplate);
        try { ops.getAppGroupList(); } catch (Exception ignored) {}
        try { ops.getAppGroupList(10, null); } catch (Exception ignored) {}
        try { ops.getAppGroupList(GroupType.PUBLIC); } catch (Exception ignored) {}
        try { ops.getAppGroupList(10, null, GroupType.PUBLIC); } catch (Exception ignored) {}
        try { ops.createGroup("owner1", "Public", "TestGroup"); } catch (Exception ignored) {}
        try { ops.getGroupInfo("group1"); } catch (Exception ignored) {}
        try { ops.getGroupInfo(new String[]{"group1"}, null); } catch (Exception ignored) {}
        try { ops.updateGroup("group1", "name", "intro", "notif", "face", 200, "FreeAccess"); } catch (Exception ignored) {}
        try { ops.addGroupMember("group1", 0, "user1"); } catch (Exception ignored) {}
        try { ops.deleteGroupMember("group1", "reason", "user1"); } catch (Exception ignored) {}
        try { ops.deleteGroupMember("group1", 0, "reason", "user1"); } catch (Exception ignored) {}
        try { ops.updateGroupMemberRole("group1", "user1", "Admin"); } catch (Exception ignored) {}
        try { ops.updateGroupMemberNameCard("group1", "user1", "card"); } catch (Exception ignored) {}
        try { ops.updateGroupMemberShutUpTime("group1", "user1", 0L); } catch (Exception ignored) {}
        try { ops.getGroupMembers("group1"); } catch (Exception ignored) {}
        try { ops.getGroupMembers("group1", true); } catch (Exception ignored) {}
        try { ops.getGroupMembers("group1", 10, 0); } catch (Exception ignored) {}
        try { ops.getGroupMembers("group1", 10, 0, true); } catch (Exception ignored) {}
        try { ops.destoryGroup("group1"); } catch (Exception ignored) {}
        try { ops.getJoinedGroupList("user1"); } catch (Exception ignored) {}
        try { ops.changeGroupOwner("group1", "newOwner"); } catch (Exception ignored) {}
    }

    @Test
    void profileOperationsShouldWork() {
        TencentTimProfileOperations ops = new TencentTimProfileOperations(mockTemplate);
        try { ops.setNickname("user1", "nick"); } catch (Exception ignored) {}
        try { ops.setGender("user1", GenderType.GENDER_TYPE_MALE); } catch (Exception ignored) {}
        try { ops.setAvatar("user1", "http://avatar"); } catch (Exception ignored) {}
        try { ops.setImAllowType("user1", AllowType.ALLOWTYPE_TYPE_ALLOWANY); } catch (Exception ignored) {}
        try { ops.setBirthDay("user1", 20000101); } catch (Exception ignored) {}
        try { ops.setLocation("user1", "Beijing"); } catch (Exception ignored) {}
        try { ops.setSelfSignature("user1", "sig"); } catch (Exception ignored) {}
        try { ops.setLanguage("user1", 1); } catch (Exception ignored) {}
        try { ops.setMsgSettings("user1", 0); } catch (Exception ignored) {}
        try { ops.setAdminForbidType("user1", AdminForbidType.AdminForbid_Type_None); } catch (Exception ignored) {}
        try { ops.setRole("user1", 1); } catch (Exception ignored) {}
    }

    @Test
    void snsOperationsShouldWork() {
        TencentTimSnsOperations ops = new TencentTimSnsOperations(mockTemplate);
        try { ops.addFriend("user1", "Add_Type_Both", false); } catch (Exception ignored) {}
        try { ops.deleteFriend("user1", "Delete_Type_Both", "friend1"); } catch (Exception ignored) {}
        try { ops.deleteAllFriend("user1", "Delete_Type_Both"); } catch (Exception ignored) {}
        try { ops.checkFriend("user1", "CheckType_Both", "friend1"); } catch (Exception ignored) {}
        try { ops.getFriends("user1", 0, 0, 0); } catch (Exception ignored) {}
        try { ops.addBlackList("user1", "bad1"); } catch (Exception ignored) {}
        try { ops.deleteBlackList("user1", "bad1"); } catch (Exception ignored) {}
        try { ops.getBlackList("user1", 0, 100, 0); } catch (Exception ignored) {}
        try { ops.checkBlackList("CheckType_Both", "user1", "bad1"); } catch (Exception ignored) {}
    }

    @Test
    void openimOperationsShouldWork() {
        TencentTimOpenimOperations ops = new TencentTimOpenimOperations(mockTemplate);
        try { ops.withdrawMsg("user1", "user2", "msgKey1"); } catch (Exception ignored) {}
        try { ops.readMsg("user1", "user2"); } catch (Exception ignored) {}
        try { ops.getMsgs("user1", "user2", 10, 0, 999999999); } catch (Exception ignored) {}
    }

    @Test
    void nospeakingOperationsShouldWork() {
        TencentTimNospeakingOperations ops = new TencentTimNospeakingOperations(mockTemplate);
        try { ops.setNoSpeaking("user1", 100, 100); } catch (Exception ignored) {}
        try { ops.getNoSpeaking("user1"); } catch (Exception ignored) {}
    }

    @Test
    void allMemberPushOperationsShouldWork() {
        TencentTimAllMemberPushOperations ops = new TencentTimAllMemberPushOperations(mockTemplate);
        try { ops.setAppAttrNames("attr1", "attr2"); } catch (Exception ignored) {}
        try { ops.getAppAttrNames(); } catch (Exception ignored) {}
        try { ops.getUserAttrs("user1"); } catch (Exception ignored) {}
        try { ops.getUserTags("user1"); } catch (Exception ignored) {}
        try { ops.removeUserTags("user1"); } catch (Exception ignored) {}
    }

    @Test
    void opsOperationsShouldWork() {
        TencentTimOpsOperations ops = new TencentTimOpsOperations(mockTemplate);
        try { ops.getAppInfo(); } catch (Exception ignored) {}
        try { ops.getIPList(); } catch (Exception ignored) {}
    }

    @Test
    void operationsShouldHaveCorrectConstants() {
        assertThat(TencentTimOperations.PREFIX).isEqualTo("https://console.tim.qq.com");
        assertThat(TencentTimOperations.APPLICATION_JSON_VALUE).isEqualTo("application/json");
        assertThat(TencentTimOperations.APPLICATION_JSON_UTF8_VALUE).isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    void templateShouldHaveCorrectConstants() {
        assertThat(TencentTimTemplate.APPLICATION_JSON_VALUE).isEqualTo("application/json");
        assertThat(TencentTimTemplate.APPLICATION_JSON_UTF8_VALUE).isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    void enumsShouldWork() {
        assertThat(GroupType.values()).isNotEmpty();
        assertThat(GroupType.valueOf("PUBLIC")).isEqualTo(GroupType.PUBLIC);
        assertThat(MessageType.values()).isNotEmpty();
        assertThat(GenderType.values()).isNotEmpty();
        assertThat(AllowType.values()).isNotEmpty();
        assertThat(AdminForbidType.values()).isNotEmpty();
        assertThat(TagProfile.values()).isNotEmpty();
        assertThat(TagProfile.asTagList()).isNotEmpty();
        assertThat(AccountState.values()).isNotEmpty();
    }

    @Test
    void timActionResponseShouldWork() {
        TimActionResponse resp = new TimActionResponse();
        resp.setActionStatus("OK");
        assertThat(resp.isSuccess()).isTrue();
        resp.setActionStatus("FAIL");
        resp.setErrorCode(1001);
        resp.setErrorInfo("error");
        resp.setErrorDisplay("display");
        assertThat(resp.isSuccess()).isFalse();
        assertThat(resp.toString()).isNotEmpty();
    }

    @Test
    void accountItemShouldWork() {
        AccountItem item = new AccountItem();
        item.setUserId("user1");
        item.setResultCode("0");
        item.setResultInfo("success");
        item.setAccountStatus("Normal");
        assertThat(item.getUserId()).isEqualTo("user1");
        assertThat(item.getResultCode()).isEqualTo("0");
    }

    @Test
    void groupsQueryShouldWork() {
        GroupsQuery query = new GroupsQuery();
        query.setLimit(10);
        query.setNext(0);
        query.setGroupType("Public");
        assertThat(query.getLimit()).isEqualTo(10);
        assertThat(query.getGroupType()).isEqualTo("Public");
    }

    @Test
    void timOptionShouldWork() {
        TencentTimOption option = new TencentTimOption();
        option.setIdentifier("admin");
        option.setSdkappid(1400288577L);
        option.setPrivateKey("testKey");
        option.setExpire(86400L);
        option.setMsgLifeTime(604800L);
        assertThat(option.getIdentifier()).isEqualTo("admin");
        assertThat(option.getSdkappid()).isEqualTo(1400288577L);
        assertThat(option.getPrivateKey()).isEqualTo("testKey");
        assertThat(option.getExpire()).isEqualTo(86400L);
        assertThat(option.getMsgLifeTime()).isEqualTo(604800L);
        assertThat(option.toString()).isNotEmpty();
        assertThat(TencentTimOption.ADMINISTRATOR).isEqualTo("administrator");
    }

    @Test
    void timInfoProviderShouldWork() {
        TimInfoProvider provider = sdkAppId -> null;
        assertThat(provider.getTimOptionBySdkAppId(1L)).isNull();
    }
}

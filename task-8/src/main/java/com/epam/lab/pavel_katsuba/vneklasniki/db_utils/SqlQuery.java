package com.epam.lab.pavel_katsuba.vneklasniki.db_utils;

import com.epam.lab.pavel_katsuba.vneklasniki.interfaces.Query;

public class SqlQuery implements Query {
    private final String GET_ALL_USERS = "select * from users";
    private final String GET_USER = "select * from users where users.id = (?)";
    private final String ADD_USER = "insert into users (name, surname, birthday) values (?, ?, ?)";
    private final String UPDATE_USER = "update users set users.name = (?), users.surname = (?), users.birthday = (?) where users.id = (?)";
    private final String DELETE_USER = "delete from users where users.id = (?)";
    private final String GET_USER_ID = "select users.id from users where users.name = (?) and users.surname = (?) and users.birthday = (?)";

    private final String GET_ALL_PRESENTS = "select * from presents";
    private final String GET_PRESENT = "select * from presents where presents.id = (?)";
    private final String ADD_PRESENT = "insert into presents (creationDate, description, fromId, toId) values (?, ?, ?, ?)";
    private final String UPDATE_PRESENT = "update presents set presents.creationDate = (?), presents.description = (?), presents.fromId = (?), presents.toId = (?) where presents.id = (?)";
    private final String DELETE_PRESENT = "delete from presents where presents.id = (?)";
    private final String GET_PRESENT_ID = "select presents.id from presents where presents.creationDate = (?) and presents.description = (?) and presents.fromId = (?) and presents.toId = (?)";

    private final String GET_ALL_FRIEDS = "select t.friendUserId, t.selfUserId, t.creationDate, t.name as userFriendName, t.surname as userFriendSurname,t.birthday as userFriendBirthday, users.name as userName, users.surname as userSurname, users.birthday as userBirthday from (select * from friends inner join users on users.id = friends.friendUserId) as t inner join users on users.id = t.selfUserId";
    private final String GET_FRIED = "select t.friendUserId, t.selfUserId, t.creationDate, t.name as userFriendName, t.surname as userFriendSurname,t.birthday as userFriendBirthday, users.name as userName, users.surname as userSurname, users.birthday as userBirthday from (select * from friends inner join users on users.id = friends.friendUserId where friends.friendsId = (?)) as t inner join users on users.id = t.selfUserId";
    private final String ADD_FRIED = "insert into friends (selfUserId, friendUserId, creationDate) values (?, ?, ?)";
    private final String UPDATE_FRIED = "update friends set friends.selfUserId = (?), friends.friendUserId = (?), friends.creationDate = (?) where friends.friendsId = (?)";
    private final String DELETE_FRIED = "delete from friends where friends.friendsId = (?)";
    private final String GET_FRIEND_ID = "select friends.friendsId from friends where friends.selfUserId = (?) and friends.friendUserId = (?)";

    private final String GET_ALL_GROUP = "select * from vgroups";
    private final String GET_GROUP = "select * from vgroups where vgroups.id = (?)";
    private final String GET_GROUP_ID = "select vgroups.id from vgroups where vgroups.groupName = (?) and vgroups.author = (?) and vgroups.creationDate = (?)";
    private final String ADD_GROUP = "insert into vgroups (groupName, author, creationDate) values (?, ?, ?)";
    private final String UPDATE_GROUP = "update vgroups set vgroups.groupName = (?), vgroups.author = (?), vgroups.creationDate = (?) where vgroups.id = (?)";
    private final String DELETE_GROUP = "delete from vgroups where vgroups.id = (?)";

    private final String GET_ALL_GROUPS_USERS = "select * from (select userGroup.userId, vgroups.id as groupId, userGroup.subscribeDate, vgroups.groupName, vgroups.author, vgroups.creationDate from vgroups inner join userGroup on vgroups.id = userGroup.groupId) as b inner join users on users.id = b.userId";
    private final String GET_GROUP_USERS = "select * from (select userGroup.userId, vgroups.id as groupId, userGroup.subscribeDate, vgroups.groupName, vgroups.author, vgroups.creationDate from vgroups inner join userGroup on vgroups.id = userGroup.groupId where userGroup.id = (?)) as b inner join users on users.id = b.userId";
    private final String GET_GROUP_USER_ID = "select userGroup.id from userGroup where userGroup.userId = (?) and userGroup.groupId = (?)";
    private final String ADD_GROUP_USER_RELATE = "insert into userGroup (userId, groupId, subscribeDate) values (?, ?, ?)";
    private final String UPDATE_GROUP_USER = "update userGroup set userGroup.userId = (?), userGroup.groupId = (?), userGroup.subscribeDate = (?) where userGroup.id = (?)";
    private final String DELETE_GROUP_USER = "delete from userGroup where userGroup.id = (?)";

    private final String GET_ALL_MESSAGES = "select * from messages";
    private final String GET_MESSAGE = "select * from messages where messages.id = (?)";
    private final String ADD_MESSAGE = "insert into messages (userFromId, userToId, creationDate, message) values (?, ?, ?, ?)";
    private final String UPDATE_MESSAGE = "update messages set messages.userToId = (?), messages.userFromId = (?), " +
            "messages.creationDate = (?), messages.message = (?) where messages.id = (?)";
    private final String DELETE_MESSAGE = "delete from messages where messages.id = (?)";
    private final String GET_MESSAGE_ID = "select messages.id from messages where messages.userFromId = (?) " +
            "and messages.userToId = (?) and messages.creationDate = (?) and messages.message = (?)";

    public static final String GET_ALL_SERVICES = "select * from services";
    public static final String GET_SERVICE = "select * from services where services.id = (?)";
    public static final String ADD_SERVICE = "insert into services (login, pass, countStatus, requestCount) values (?, ?, ?, ?)";
    public static final String UPDATE_SERVICE = "update services set services.login = (?), services.pass = (?), services.countStatus = (?), services.requestCount = (?) where services.id = (?)";
    public static final String DELETE_SERVICE = "delete from services where services.id = (?)";
    public static final String GET_SERVICE_ID = "select services.id from services where services.login = (?)";

    public static final String GET_ALL_ACCESSES = "select * from accesses";
    public static final String GET_ACCESS = "select * from accesses where accesses.id = (?)";
    public static final String ADD_ACCESS = "insert into accesses (access) values (?)";
    public static final String UPDATE_ACCESS = "update accesses set accesses.access = (?) where accesses.id = (?)";
    public static final String DELETE_ACCESS = "delete from accesses where accesses.id = (?)";
    public static final String GET_ACCESS_ID = "select accesses.id from accesses where accesses.access = (?)";

    public static final String GET_ALL_SERVICE_ACCESSES = "select * from (select serviceAccess.serviceId, services.login, services.pass, services.countStatus, services.requestCount, serviceAccess.accessId  from services inner join serviceAccess on services.id = serviceAccess.serviceId) as s inner join accesses on accesses.id = s.accessId";
    public static final String GET_SERVICE_ACCESS = "select * from (select serviceAccess.serviceId, services.login, services.pass, services.countStatus, services.requestCount, serviceAccess.accessId  from services inner join serviceAccess on services.id = serviceAccess.serviceId where serviceAccess.id = (?)) as s inner join accesses on accesses.id = s.accessId";
    public static final String ADD_SERVICE_ACCESS = "insert into serviceAccess (serviceId, accessId) values (?, ?)";
    public static final String UPDATE_SERVICE_ACCESS = "update serviceAccess set serviceAccess.serviceId = (?), serviceAccess.accessId = (?) where serviceAccess.id = (?)";
    public static final String DELETE_SERVICE_ACCESS = "delete from serviceAccess where serviceAccess.id = (?)";
    public static final String GET_SERVICE_ACCESS_ID = "select serviceAccess.id from serviceAccess where serviceAccess.serviceId = (?) and serviceAccess.accessId = (?)";

    public String getReadAllUsersQuery() {
        return GET_ALL_USERS;
    }

    public String getReadUserQuery() {
        return GET_USER;
    }

    public String getAddUserQuery() {
        return ADD_USER;
    }

    public String getUpdateUserQuery() {
        return UPDATE_USER;
    }

    public String getDeleteUserQuery() {
        return DELETE_USER;
    }

    @Override
    public String getUserIDQuery() {
        return GET_USER_ID;
    }

    public String getGET_ALL_FRIEDS() {
        return GET_ALL_FRIEDS;
    }

    public String getGET_FRIED() {
        return GET_FRIED;
    }

    public String getGET_FRIED_ID() {
        return GET_FRIEND_ID;
    }

    public String getADD_FRIED() {
        return ADD_FRIED;
    }

    public String getUPDATE_FRIED() {
        return UPDATE_FRIED;
    }

    public String getDELETE_FRIED() {
        return DELETE_FRIED;
    }

    public String getGET_ALL_GROUP() {
        return GET_ALL_GROUP;
    }

    public String getGET_GROUP() {
        return GET_GROUP;
    }

    public String getGET_GROUP_ID() {
        return GET_GROUP_ID;
    }

    public String getADD_GROUP() {
        return ADD_GROUP;
    }

    public String getUPDATE_GROUP() {
        return UPDATE_GROUP;
    }

    public String getDELETE_GROUP() {
        return DELETE_GROUP;
    }

    public String getGET_ALL_MESSAGES() {
        return GET_ALL_MESSAGES;
    }

    public String getGET_MESSAGE() {
        return GET_MESSAGE;
    }

    public String getADD_MESSAGE() {
        return ADD_MESSAGE;
    }

    public String getUPDATE_MESSAGE() {
        return UPDATE_MESSAGE;
    }

    public String getDELETE_MESSAGE() {
        return DELETE_MESSAGE;
    }

    @Override
    public String getMessageIDQuery() {
        return GET_MESSAGE_ID;
    }

    public String getAllPresentsQuery() {
        return GET_ALL_PRESENTS;
    }

    public String getPresentQuery() {
        return GET_PRESENT;
    }

    public String getPresentIdQuery() {
        return GET_PRESENT_ID;
    }

    public String getAddPresentQuery() {
        return ADD_PRESENT;
    }

    public String getUpdatePresentQuery() {
        return UPDATE_PRESENT;
    }

    public String getDeletePresentQuery() {
        return DELETE_PRESENT;
    }

    @Override
    public String getGET_ALL_GROUPS_USERS() {
        return GET_ALL_GROUPS_USERS;
    }

    @Override
    public String getGET_GROUP_USERS() {
        return GET_GROUP_USERS;
    }

    @Override
    public String getGET_GROUP_USER_ID() {
        return GET_GROUP_USER_ID;
    }

    @Override
    public String getADD_RELATE_GROUP_USER() {
        return ADD_GROUP_USER_RELATE;
    }

    @Override
    public String getUPDATE_RELATE_GROUP_USER() {
        return UPDATE_GROUP_USER;
    }

    @Override
    public String getDELETE_RELATE_GROUP_USER() {
        return DELETE_GROUP_USER;
    }

}

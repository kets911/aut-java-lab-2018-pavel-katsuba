package com.epam.lab.pavel_katsuba.vneklasniki.interfaces;

public interface Query {
    String getReadAllUsersQuery();

    String getReadUserQuery();

    String getAddUserQuery();

    String getUpdateUserQuery();

    String getDeleteUserQuery();

    String getUserIDQuery();

    String getMessageIDQuery();

    String getGET_ALL_FRIEDS();

    String getGET_FRIED();

    String getGET_FRIED_ID();

    String getADD_FRIED();

    String getUPDATE_FRIED();

    String getDELETE_FRIED();

    String getGET_ALL_GROUP();

    String getGET_GROUP();

    String getADD_GROUP();

    String getGET_GROUP_ID();

    String getUPDATE_GROUP();

    String getDELETE_GROUP();

    String getGET_ALL_MESSAGES();

    String getGET_MESSAGE();

    String getADD_MESSAGE();

    String getUPDATE_MESSAGE();

    String getDELETE_MESSAGE();

    String getAllPresentsQuery();

    String getPresentQuery();

    String getPresentIdQuery();

    String getAddPresentQuery();

    String getUpdatePresentQuery();

    String getDeletePresentQuery();

    String getGET_ALL_GROUPS_USERS();

    String getGET_GROUP_USERS();

    String getGET_GROUP_USER_ID();

    String getADD_RELATE_GROUP_USER();

    String getUPDATE_RELATE_GROUP_USER();

    String getDELETE_RELATE_GROUP_USER();

}

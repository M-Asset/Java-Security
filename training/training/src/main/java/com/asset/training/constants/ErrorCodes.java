package com.asset.training.constants;

public interface ErrorCodes {
    public static interface SUCCESS {

        int SUCCESS = 0;
    }

    interface ERROR {
        int INVALID_USERNAME_OR_PASSWORD = -400;
        int EXPIRED_TOKEN = -401;
        int NOT_AUTHORIZED = -402;
        int INVALID_TOKEN = -403;
        int DATABASE_ERROR = -404;
        int UPDATE_FAILED = -405;
        int UNKNOWN_ERROR = -406;
        int LDAP_AUTH_FAILED = -407;
        int CANNOT_GENERATE_TOKEN = -408;
        int USER_NOT_FOUND = -409;
        int NO_USERS_FOUND = -412;
        int DELETE_FAILED = -415;

        int USERNAME_SHOULD_BE_UNIQUE = -419;

        int CONCURRENT_SESSIONS_DETECTED=-422;

    }
}

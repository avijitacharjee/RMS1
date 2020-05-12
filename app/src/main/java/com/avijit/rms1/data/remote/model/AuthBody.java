package com.avijit.rms1.data.remote.model;

public class AuthBody {
    public final String grant_type = "password";
    public final String client_id="2";
    public final String client_secret="Xhqe0rptK0qCmRjuXbw2PADML5fjaE3RJOBhTMHn";
    public String username;
    public String password;

    public String getGrant_type() {
        return grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

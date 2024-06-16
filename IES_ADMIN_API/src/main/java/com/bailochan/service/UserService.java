package com.bailochan.service;

import com.bailochan.bindings.DashboardCard;
import com.bailochan.bindings.LoginForm;
import com.bailochan.bindings.UserAccForm;


public interface UserService {

    public String login(LoginForm loginForm);

    public boolean recoverPwd(String email);

    public DashboardCard fetchDashboardInfo();
    
    public UserAccForm getUserByEmail(String email);
    

}

package com.bailochan.service;

import java.util.List;

import com.bailochan.bindings.UnlockAccForm;
import com.bailochan.bindings.UserAccForm;

public interface AccountService {

    public SignUpResponse createUserAccount(UserAccForm accForm);

    public List<UserAccForm> fetchUserAccounts( );

    public UserAccForm getUserAccById(Integer accId);

    public String changeAccStatus(Integer accId, String status);

    public String unlockUserAccount(UnlockAccForm unlockAccForm);
    
    public String deleteUserAccount(Integer userId); // late added

}

package com.cui.atcrowdfunding.manager.service;

import com.cui.atcrowdfunding.util.Page;

import java.util.Map;

public interface RoleService {
    Page queryPage(Map<String,Object> paramMap);
}

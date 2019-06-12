package com.cui.atcrowdfunding.potal.service;

import com.cui.atcrowdfunding.bean.Member;

import java.util.Map;

public interface MemberService {
    Member queryMemberLogin(Map<String,Object> paramMap);
}

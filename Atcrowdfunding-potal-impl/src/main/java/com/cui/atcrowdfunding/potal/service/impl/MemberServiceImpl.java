package com.cui.atcrowdfunding.potal.service.impl;

import com.cui.atcrowdfunding.bean.Member;
import com.cui.atcrowdfunding.potal.dao.MemberMapper;
import com.cui.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;


    @Override
    public Member queryMemberLogin(Map<String, Object> paramMap) {
        return memberMapper.queryMemberLogin(paramMap);
    }
}

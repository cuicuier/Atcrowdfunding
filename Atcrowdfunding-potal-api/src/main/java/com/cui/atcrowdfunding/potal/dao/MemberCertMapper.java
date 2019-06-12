package com.cui.atcrowdfunding.potal.dao;

import com.cui.atcrowdfunding.bean.MemberCert;
import java.util.List;

public interface MemberCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCert record);

    MemberCert selectByPrimaryKey(Integer id);

    List<MemberCert> selectAll();

    int updateByPrimaryKey(MemberCert record);
}
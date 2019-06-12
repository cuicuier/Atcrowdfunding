package com.cui.atcrowdfunding.vo;

import com.cui.atcrowdfunding.bean.User;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<User> getDatas() {
        return datas;
    }

    public void setDatas(List<User> datas) {
        this.datas = datas;
    }

    private List<User> datas=new ArrayList<User>();
    private List<Integer> ids=new ArrayList<Integer>();

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}

package com.taotao.manage.service;

import com.taotao.manage.mapper.TestMapper;
import com.taotao.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public String queryCurrentDate() {
        return testMapper.queryCurrentDate();
    }
}

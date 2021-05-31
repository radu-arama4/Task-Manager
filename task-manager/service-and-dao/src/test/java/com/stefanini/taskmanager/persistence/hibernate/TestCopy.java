package com.stefanini.taskmanager.persistence.hibernate;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class TestCopy {

    @Test
    public void test(){
        UserTO newUser = new UserTO("jora", "pechea", "jora");
        User user = new User();

        try {
            BeanUtils.copyProperties(user, newUser);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(user.getUserName());
    }

}

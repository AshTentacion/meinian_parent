package com.atguigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class JustTest {

    @Test
    public void test01(){
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        System.out.println(bpe.encode("123"));
    }
}

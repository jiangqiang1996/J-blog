package xin.jiangqiang.blog.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xin.jiangqiang.blog.util.config.JblogProperties;

/**
 * @author JiangQiang
 * @date 2020/10/14 10:09
 */
@RunWith(SpringRunner.class)
//启动Spring
@SpringBootTest
public class JblogPropertiesTest {
    @Autowired
    private JblogProperties jblogProperties;


    @Test
    public void test() throws Exception {
        System.out.println(jblogProperties);
    }


}

package biz.thehacker.demo.earsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

import static biz.thehacker.demo.earsystem.test.HasBeanMatcher.hasBean;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class EarSystemDemoApplicationTest {

    @Autowired private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void dataSourceIsAvailable() {
        assertThat(applicationContext, hasBean(DataSource.class));
    }
}

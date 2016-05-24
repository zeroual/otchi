package com.otchi.utils;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.otchi.infrastructure.config.DatabaseConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static com.otchi.infrastructure.config.Constants.SPRING_PROFILE_DEVELOPMENT;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})

@ActiveProfiles(SPRING_PROFILE_DEVELOPMENT)
public class AbstractRepositoryTest {
}

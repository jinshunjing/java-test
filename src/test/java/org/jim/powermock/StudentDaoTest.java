package org.jim.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PowerMock
 * Mockito
 *
 * Spring test
 * Springockito
 *
 * 问题1：用了spring test之后，一定要在xml里定义bean吗？Service注解不生效了？
 *
 * @author JSJ
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = SpringockitoContextLoader.class,
        locations = {"classpath:applicationContext-test.xml"}
)
public class StudentDaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private StudentManager manager;

    @ReplaceWithMock
    @Autowired
    private StudentService service;

    @Test
    public void testUpdateName() {
        int id = 10;
        String name = "Jim";
        int result = 1;

        PowerMockito.when(service.updateName(id, name)).thenReturn(result);

        Assert.assertEquals(result, manager.updateName(id, name));
    }

}

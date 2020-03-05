package org.jim.powermock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock
 * Mockito
 *
 * @author JSJ
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
@PrepareForTest({MockUtil.class})
public class StudentManagerTest {

    @InjectMocks
    private StudentManager manager;

    @Mock
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

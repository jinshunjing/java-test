package org.jim.easymock;

import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class HzManagerTest extends EasyMockSupport {

    @TestSubject
    private HzManager manager = new HzManager();

    @Mock
    private HzService service;

    @Test
    public void testProcess() {
        String msg = "Hello";

        service.consume(msg);
        service.consume(null);
        expectLastCall().andThrow(new RuntimeException());
        replayAll();

        manager.process(msg);
        manager.process(null);
        verifyAll();
    }

    @Test
    public void testHandle() {
        String msg = "Hello";
        int result = 1;

        expect(service.handle(msg)).andReturn(result);
        replayAll();

        manager.handle(msg);
        verifyAll();
    }

    @Test
    public void testProvide() {
        String msg = "Hello";

        expect(service.provide()).andReturn(msg);
        replayAll();

        manager.provide();
        verifyAll();
    }
}

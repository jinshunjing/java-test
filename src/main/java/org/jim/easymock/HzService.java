package org.jim.easymock;

public interface HzService {

    void consume(String message);

    int handle(String msg);

    String provide();

}

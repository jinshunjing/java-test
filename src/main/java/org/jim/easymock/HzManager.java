package org.jim.easymock;

public class HzManager {

    private HzService hzService;

    public void process(String msg) {
        System.out.println("Enter process");
        try {
            hzService.consume(msg);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("Exit process");
    }

    public void handle(String msg) {
        System.out.println("Enter handle");
        int result = hzService.handle(msg);
        System.out.println("Exit handle: " + result);
    }

    public void provide() {
        System.out.println("Enter provide");
        String msg = hzService.provide();
        System.out.println("Exit provide: " + msg);
    }

    public void setHzService(HzService hzService) {
        this.hzService = hzService;
    }
}

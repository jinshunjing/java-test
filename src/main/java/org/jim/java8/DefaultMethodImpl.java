package org.jim.java8;

public class DefaultMethodImpl implements IDefaultMethod {
    private String ver;

    public DefaultMethodImpl(String version) {
        this.ver = version;
    }

    @Override
    public double calc(double v) {
        return v + v;
    }

    @Override
    public String version() {
        return ver;
    }
}

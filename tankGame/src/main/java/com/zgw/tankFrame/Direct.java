package com.zgw.tankFrame;

public enum Direct {
    UP,DOWN,LEFT,RIGHT;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

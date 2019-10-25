package com.zgw.encoder;

import io.netty.channel.Channel;

public abstract class Msg {

    public abstract byte[] toBytes();

    public abstract void  handle();

    public abstract MsgType getMsgType();

    public abstract void parse(byte[] bytes);
}

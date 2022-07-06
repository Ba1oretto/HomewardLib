package com.baioretto.homewardlib.api.packet;

import com.baioretto.homewardlib.api.Wrapper;

@SuppressWarnings("unused")
public class PacketUtil {
    public static IPacketUtil get() {
        return getInstance().packet;
    }

    private final IPacketUtil packet;

    private PacketUtil() {
        packet = Wrapper.getClassInstance(IPacketUtil.class);
    }

    private static volatile PacketUtil instance;

    private static PacketUtil getInstance() {
        if (instance == null)
            synchronized (PacketUtil.class) {
                if (instance == null) return new PacketUtil();
            }
        return instance;
    }
}

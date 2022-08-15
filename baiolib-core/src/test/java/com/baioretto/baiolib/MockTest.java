package com.baioretto.baiolib;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

@SuppressWarnings("FieldCanBeLocal")
public class MockTest {
    private static ServerMock server;
    private static BaioLib plugin;

    @BeforeAll
    static void setUp() {
        server = MockBukkit.getMock();
        plugin = MockBukkit.load(BaioLib.class);
    }

    @AfterAll
    static void cleanUp() {
        MockBukkit.unmock();
    }
}

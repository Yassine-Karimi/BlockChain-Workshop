package yas.kr.blockchainworkshop.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {
    @BeforeEach
    void beforeEach()
    {
        Block block = new Block(0,"First Block",null);
    }

    @Test
    void calculateHash() {

        Block block = new Block(0,"First Block",null);
        System.out.println(block.getCurrentHash());
        System.out.println(block.calculateHash());

    }

    @Test
    void validateBlock() {
        Block block = new Block(0,"First Block",null);
        assertNotNull(block.calculateHash());

    }
}
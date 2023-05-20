package yas.kr.blockchainworkshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import yas.kr.blockchainworkshop.HashUtil;

import java.time.Instant;

@RequiredArgsConstructor
@Setter
@Getter

public class Block {
    private Integer index;
    private Instant timeStamp;
    private String previousHash;
    private String currentHash;
    private String data;

    public Block(Integer _index, String _data,String _previousHash ){
        this.timeStamp=Instant.now();
        this.index= _index;
        this.previousHash=_previousHash;
        this.currentHash=calculateHash();
        this.data=_data;
    }
    public String calculateHash()
    {
        String toBeHashed = index + previousHash+timeStamp.getEpochSecond()+data;
        return HashUtil.calculateSHA256(toBeHashed);
    }
     public boolean validateBlock()
     {
         String calculatedHash= calculateHash();
         return  this.currentHash.equals(calculatedHash);

     }

}


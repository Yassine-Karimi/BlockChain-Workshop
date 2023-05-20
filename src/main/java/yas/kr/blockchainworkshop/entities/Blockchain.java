package yas.kr.blockchainworkshop.entities;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Blockchain {

    private List<Block> chain;

  //  private TransactionPool transactionPool;

    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
     //   this.transactionPool = new TransactionPool();
        this.difficulty = difficulty;
        // Create the genesis block
        Block genesisBlock = createGenesisBlock();
        chain.add(genesisBlock);
    }

    private Block createGenesisBlock() {
        // Create the first block in the blockchain (genesis block)

     //   List<Transaction> transactions = new ArrayList<>(); // Empty list for genesis block

        return new Block(0, "0", 0);
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public Block addBlock(Block block) {
        if (isValidBlock(block)) {
            chain.add(block);
           // transactionPool.removeTransactions(block.getTransactions());
            return block;
        }
        throw new InvalidParameterException();
    }

    public boolean isValidBlock(Block block) {
        Block previousBlock = getLatestBlock();

        // Check if the index is incrementing by 1
        if (block.getIndex() != previousBlock.getIndex() + 1) {
            return false;
        }

        // Check if the previous hash matches
        if (!block.getPreviousHash().equals(previousBlock.getCurrentHash())) {
            return false;
        }

        // Check if the block hash meets the difficulty requirement
        String prefix = getDifficultyPrefix(difficulty);
        return block.getCurrentHash().startsWith(prefix);

    }

    public Block mineBlock() {

        Block block = new Block(getChain().size(), getLatestBlock().getCurrentHash(), 0);

        block.setPreviousHash(getLatestBlock().getCurrentHash());

        var calculatedHash = block.calculateHash();

        while (!calculatedHash.startsWith(getDifficultyPrefix(difficulty))) {
            block.incrementNonce();
            calculatedHash = block.calculateHash();
        }

        block.setCurrentHash(calculatedHash);

        return addBlock(block);
    }


    private String getDifficultyPrefix(int difficulty) {
        return "0".repeat(difficulty);
    }

    public boolean validateChain() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Check if the current hash of the block is valid
            if (!currentBlock.getCurrentHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Check if the previous hash is equal to the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getCurrentHash())) {
                return false;
            }
        }
        return true;
    }
/*
    public void addTransaction(Transaction transaction) {
        transactionPool.addTransaction(transaction);
    }*/
}

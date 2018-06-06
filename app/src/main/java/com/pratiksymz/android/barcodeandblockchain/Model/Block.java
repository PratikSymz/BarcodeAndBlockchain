package com.pratiksymz.android.barcodeandblockchain.Model;

import com.pratiksymz.android.barcodeandblockchain.Utils.StringUtil;

import java.util.Date;

public class Block {
    private String mPreviousHash;
    private String mData;
    private long mTimestamp;
    private String mBlockHash;
    private int mNonce;

    public Block(String previousHash, String data) {
        this.mPreviousHash = previousHash;
        this.mData = data;
        this.mTimestamp = new Date().getTime();
        // Since HashCode only works for arrays, we have to make our own
        // StringUtil.applySHA256
        this.mBlockHash = calculateHash();
    }

    public String getPreviousHash() {
        return mPreviousHash;
    }

    public String getData() {
        return mData;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getBlockHash() {
        return mBlockHash;
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySHA256(
                mPreviousHash +
                        String.valueOf(mTimestamp) +
                        mData +
                        String.valueOf(mNonce)
        );

        return calculatedHash;
    }

    public void mineBlock(int difficulty) {
        // Create a string with difficulty * '0', i.e., '00000....'
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!mBlockHash.substring(0, difficulty).equals(target)) {
            mNonce++;
            mBlockHash = calculateHash();
        }
        System.out.println("Block Mined!! : " + mBlockHash);
    }
}

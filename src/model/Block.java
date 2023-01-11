package model;

public class Block {

    private BlockType blockType;

    public Block() {
        blockType = BlockType.NONE;
    }

    public Block(BlockType blockType) {
        this.blockType = blockType;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}

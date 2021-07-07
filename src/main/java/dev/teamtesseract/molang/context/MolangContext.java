package dev.teamtesseract.molang.context;

import net.minecraft.world.World;

public abstract class MolangContext {

    private World world;

    public MolangContext(World w) {
        this.world = w;
    }
}

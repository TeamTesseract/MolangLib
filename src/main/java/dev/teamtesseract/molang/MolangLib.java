package dev.teamtesseract.molang;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class MolangLib implements ModInitializer {

    public static final String MOD_ID = "molang";

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() { }
}

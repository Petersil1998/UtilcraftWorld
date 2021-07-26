package net.petersil98.utilcraft_world.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.petersil98.utilcraft_world.UtilcraftWorld;

import javax.annotation.Nonnull;

public class Languages {

    @Nonnull
    public static English getEnglish(DataGenerator generator){
        return new English(generator);
    }

    @Nonnull
    public static German getGerman(DataGenerator generator){
        return new German(generator);
    }

    private static class English extends LanguageProvider {

        public English(DataGenerator generator) {
            super(generator, UtilcraftWorld.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
        }
    }

    private static class German extends LanguageProvider {

        public German(DataGenerator generator) {
            super(generator, UtilcraftWorld.MOD_ID, "de_de");
        }

        @Override
        protected void addTranslations() {
        }
    }
}

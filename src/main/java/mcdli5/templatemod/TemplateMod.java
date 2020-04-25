package mcdli5.templatemod;

import mcdli5.templatemod.setup.ModRegistrate;
import mcdli5.templatemod.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TemplateMod.MODID)
public class TemplateMod {
    public static final String MODID = "templatemod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public TemplateMod() {
        ModSetup.init();
        ModRegistrate.init();
        LOGGER.debug("Hello");
    }
}

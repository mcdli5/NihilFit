package mcdli5.exnihilo;

import mcdli5.exnihilo.setup.ModRegistrate;
import mcdli5.exnihilo.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExNihilo.MOD_ID)
public class ExNihilo {
    public static final String MOD_ID = "exnihilo";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ExNihilo() {
        ModSetup.init();
        ModRegistrate.init();
        LOGGER.debug("Hello from Ex Nihilo!");
    }
}

package mcdli5.nihilfit;

import mcdli5.nihilfit.setup.ModRegistrate;
import mcdli5.nihilfit.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NihilFit.MODID)
public class NihilFit {
    public static final String MODID = "nihilfit";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public NihilFit() {
        ModSetup.init();
        ModRegistrate.init();
        LOGGER.debug("Hello from Nihil Fit!");
    }
}

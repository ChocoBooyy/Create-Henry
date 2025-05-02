package com.chocoboy.create_henry.infrastructure.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.chocoboy.create_henry.HenryCreate;

public class HenryPonderIndex {

    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(HenryCreate.MOD_ID);

    public static final boolean REGISTER_DEBUG_SCENES = false;

    public static void register() {
    }

    public static boolean editingModeActive() {
        return AllConfigs.client().editingMode.get();
    }

}
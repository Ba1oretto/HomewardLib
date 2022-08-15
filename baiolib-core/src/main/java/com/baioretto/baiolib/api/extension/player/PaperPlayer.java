package com.baioretto.baiolib.api.extension.player;

import com.baioretto.baiolib.api.AbstractUtil;

/**
 * Get {@link IPlayer} implementation class instance.
 *
 * @author baioretto
 * @since 1.1.0
 */

public class PaperPlayer extends AbstractUtil<IPlayer> {
    protected PaperPlayer() {
        super(IPlayer.class);
    }
}

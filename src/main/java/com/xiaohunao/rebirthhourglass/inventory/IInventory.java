package com.xiaohunao.rebirthhourglass.inventory;

import com.xiaohunao.rebirthhourglass.capability.IPlayerRebirthCapability;
import com.xiaohunao.rebirthhourglass.capability.PlayerRebirthCapability;

public interface IInventory {
    void Save(IPlayerRebirthCapability deathInfo);

    void Load(IPlayerRebirthCapability deathInfo);
}

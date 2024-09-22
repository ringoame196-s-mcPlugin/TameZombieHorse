package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent

class EntityTargetEvent : Listener {
    @EventHandler
    fun onEntityTarget(e: EntityTargetEvent) {
        val escortZombie = e.entity as? Zombie ?: return
        val name = escortZombie.customName // ゾンビの名前
        val target = e.target
        val uuid = target?.uniqueId.toString()

        // オーナーは敵対化されないように
        if (name == Data.ESCORT_ZOMBIE_NAME && escortZombie.scoreboardTags.contains(uuid)) {
            e.isCancelled = true
        }
    }
}

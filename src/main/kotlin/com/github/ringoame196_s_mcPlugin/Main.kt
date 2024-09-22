package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.EntityDamageByEntityEvent
import com.github.ringoame196_s_mcPlugin.events.EntityTargetEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEntityEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        // イベント登録
        server.pluginManager.registerEvents(PlayerInteractEntityEvent(), plugin)
        server.pluginManager.registerEvents(PlayerInteractEvent(), plugin)
        server.pluginManager.registerEvents(EntityDamageByEntityEvent(plugin), plugin)
        server.pluginManager.registerEvents(EntityTargetEvent(), plugin)
    }
}

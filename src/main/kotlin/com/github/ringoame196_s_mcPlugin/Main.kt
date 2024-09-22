package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEntityEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        server.pluginManager.registerEvents(Events(), plugin)
        server.pluginManager.registerEvents(PlayerInteractEntityEvent(plugin), plugin)
    }
}

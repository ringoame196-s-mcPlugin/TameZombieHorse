package com.github.ringoame196_s_mcPlugin.events

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.ZombieHorse
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractEvent() : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val item = e.item ?: return
        val zombieHorse = player.vehicle as? ZombieHorse ?: return

        val rottenFleshMaterial = Material.ROTTEN_FLESH

        if (item.type == rottenFleshMaterial) {
            val sound = Sound.ENTITY_GENERIC_EXPLODE
            val movementSpeed = 2.0

            item.amount -- // アイテムを消費
            player.playSound(player, sound, 1f, 1f) // 音を流す
            // ブースト
            val direction = player.location.direction.normalize()
            zombieHorse.velocity = direction.multiply(movementSpeed)
            // メッセージを表示
            val message = "${ChatColor.GOLD}ブースト！"
            val component = TextComponent(message)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component)
        }
    }
}

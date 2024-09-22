package com.github.ringoame196_s_mcPlugin.events

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Drowned
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.entity.ZombieHorse
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

class PlayerInteractEntityEvent : Listener {
    @EventHandler
    fun onPlayerInteractEntity(e: PlayerInteractEntityEvent) {
        val player = e.player
        val zombie = e.rightClicked as? Zombie ?: return
        changeZombieHorse(zombie, player)
    }

    private fun changeZombieHorse(zombie: Zombie, player: Player) {
        val playerItem = player.inventory.itemInMainHand
        val goldenCarrotMaterial = Material.GOLDEN_CARROT

        if (playerItem.type == goldenCarrotMaterial) {
            val location = zombie.location
            val sound = Sound.ENTITY_ZOMBIE_VILLAGER_CURE
            val particle = Particle.EXPLOSION_LARGE
            playerItem.amount --
            player.playSound(location, sound, 1f, 1f) // 音を鳴らす
            location.world?.spawnParticle(particle, location, 30, 0.5, 0.5, 0.5, 0.05) // パーティクルを出す
            summonZombieHorse(zombie, player) // 召喚するゾンビホース
        }
    }
    private fun summonZombieHorse(zombie: Zombie, player: Player) {
        zombie.remove() // ゾンビ削除
        val location = zombie.location // 座標
        val world = location.world // world
        val gameMode = player.gameMode

        // ドラウンド
        val drowned = world?.spawnEntity(location, EntityType.DROWNED) as Drowned
        val ironHelmet = ItemStack(Material.IRON_HELMET) // ヘルメット
        val trident = ItemStack(Material.TRIDENT) // トライデント
        drowned.equipment?.helmet = ironHelmet // 装備をつける
        drowned.equipment?.setItemInMainHand(trident) // トライデントを渡す

        val zombieHorse = world.spawnEntity(location, EntityType.ZOMBIE_HORSE) as ZombieHorse

        zombieHorse.owner = player // ゾンビホースを手なづけておく
        if (gameMode == GameMode.ADVENTURE || gameMode == GameMode.SURVIVAL) {
            // アドベンチャー サバイバルのみ実行
            drowned.target = player // ドラウンドの敵対化させる
        }

        zombieHorse.addPassenger(drowned) // ゾンビホースの上にドラウンドを乗せる
    }
}

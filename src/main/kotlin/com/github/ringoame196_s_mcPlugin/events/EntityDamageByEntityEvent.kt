package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.entity.ZombieHorse
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class EntityDamageByEntityEvent(private val plugin: Plugin) : Listener {
    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val player = e.entity as? Player ?: return
        val attacker = e.damager
        val location = attacker.location
        val zombieHorse = player.vehicle as? ZombieHorse ?: return
        val owner = zombieHorse.owner // ゾンビホースのオーナー

        if (owner != null && owner != attacker) {
            summonEscortZombie(attacker, owner as Player) // 護衛ゾンビ召喚
        }
    }

    private fun summonEscortZombie(attacker: Entity, owner: Player) {
        val location = attacker.location
        val world = location.world
        val helmet = ItemStack(Material.IRON_HELMET) // ヘルメット
        val zombie = world?.spawnEntity(location, EntityType.ZOMBIE) as Zombie // ゾンビ召喚
        zombie.equipment?.helmet = helmet // ゾンビにヘルメットをつける
        zombie.target = attacker as? LivingEntity ?: return // 攻撃したやつを敵対にする
        zombie.customName = Data.ESCORT_ZOMBIE_NAME // 名前を変える
        zombie.scoreboardTags.add(owner.uniqueId.toString()) // オーナーをtagに保存する

        Bukkit.getScheduler().runTaskLater(
            plugin,
            Runnable { // 1秒後に実行したいコードをここに書く
                zombie.remove() // 15秒したら自動で削除される
            },
            300L
        )
    }
}

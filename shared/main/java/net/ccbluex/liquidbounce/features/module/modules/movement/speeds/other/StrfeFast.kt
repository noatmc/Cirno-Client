/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MovementUtils

class StrfeFast : SpeedMode("StrafeFast") {
    override fun onMotion() {
        if (MovementUtils.isMoving) {
            if (mc.thePlayer!!.onGround) {
                mc.thePlayer!!.jump()
            }
            if (!mc.thePlayer!!.onGround) {
                MovementUtils.strafe(MovementUtils.speed * 1.35F)
            }
        }
    }

    override fun onUpdate() {}
    override fun onMove(event: MoveEvent) {}
}
/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.*
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.*
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.*
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan.SpartanYPort
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreBHop
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreLowHop
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreOnGround
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.ListValue
import java.util.*

@ModuleInfo(name = "Speed", description = "Allows you to move faster.", category = ModuleCategory.MOVEMENT)
class Speed : Module() {
    private val speedModes = arrayOf( // NCP
            NCPBHop(),
            ThotPatrol(),
            NCPFHop(),
            SNCPBHop(),
            NCPHop(),
            YPort(),
            YPort2(),
            Larkus(),
            NCPYPort(),
            Boost(),
            Frame(),
            MiJump(),
            OnGround(),  // AAC
            AACBHop(),
            AAC2BHop(),
            AAC3BHop(),
            AAC4BHop(),
            AAC5BHop(),
            AAC6BHop(),
            AAC7BHop(),
            Yport3(),
            AACHop3313(),
            AACHop350(),
            AACLowHop(),
            Strfe(),
            StrfeFast(),
            AACLowHop2(),
            AACLowHop3(),
            AACGround(),
            AACGround2(),
            AACYPort(),
            AACYPort2(),
            AACPort(),
            OldAACBHop(),  // Spartan
            SpartanYPort(),  // Spectre
            SpectreLowHop(),
            SpectreBHop(),
            SpectreOnGround(),
            TeleportCubeCraft(),  // Server
            HiveHop(),
            HypixelHop(),
            Mineplex(),
            OldMatrix(), // matrix
            OldMatrix2(),
            OldMatrixFast(),
            OldMatrix3(),
            Matrix(),
            MatrixCustom(),
            MatrixOnGround(),
            MineplexGround(),  // Other
            WarAC(), // WarAC
            SlowHop(),
            Zoom(),
            CustomSpeed()
    )

    val modeValue: ListValue = object : ListValue("Mode", modes, "NCPBHop") {
        override fun onChange(oldValue: String, newValue: String) {
            if (state)
                onDisable()
        }

        override fun onChanged(oldValue: String, newValue: String) {
            if (state)
                onEnable()
        }
    }
    val matrixcustomHclipValue = FloatValue("MatrixHClip", 0.1f, 0f, 1f)
    val matrixcustomAirSpeedValue = FloatValue("MatrixAirSpeed", 0.0206f, 0f, 0.1f)
    val matrixcustomStrafeValue = FloatValue("MatrixStrafe", 1.01f, 0f, 2f)
    val matrixcustomAirTimerValue = FloatValue("MatrixAirTimer", 1.05f, 1.05f, 2f)
    val matrixcustomGroundTimerValue = FloatValue("MatrixGroundTimer", 1.6f, 0.1f, 4.5f)
    val customFallTimer3 = FloatValue("MatrixFallTimer3", 1.05f, 1f, 4.5f)
    val customFallTimer2 = FloatValue("MatrixFallTimer2", 0.42f, 0.1f, 4.5f)
    val customFallTimer1 = FloatValue("MatrixFallTimer1", 1.81f, 1.05f, 4.5f)
    val customSpeedValue = FloatValue("CustomSpeed", 1.6f, 0.2f, 2f)
    val customYValue = FloatValue("CustomY", 0f, 0f, 4f)
    val customTimerValue = FloatValue("CustomTimer", 1f, 0.1f, 2f)
    val customStrafeValue = BoolValue("CustomStrafe", true)
    val CustommatrixHclip = BoolValue("CustomMatrixHclip", false)
    val CustommatrixStrafeValue = BoolValue("CustomMatrixStrafe", false)
    val CustommatrixFallTimerValue = BoolValue("CustomMatrixFallTimer", true)
    val resetXZValue = BoolValue("CustomResetXZ", false)
    val resetYValue = BoolValue("CustomResetY", false)
    val portMax = FloatValue("AAC-PortLength", 1f, 1f, 20f)
    val aacGroundTimerValue = FloatValue("AACGround-Timer", 3f, 1.1f, 10f)
    val cubecraftPortLengthValue = FloatValue("CubeCraft-PortLength", 1f, 0.1f, 2f)
    val mineplexGroundSpeedValue = FloatValue("MineplexGround-Speed", 0.5f, 0.1f, 1f)

    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        val thePlayer = mc.thePlayer ?: return

        if (thePlayer.sneaking)
            return

        if (MovementUtils.isMoving) {
            thePlayer.sprinting = true
        }

        mode?.onUpdate()
    }

    @EventTarget
    fun onMotion(event: MotionEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (thePlayer.sneaking || event.eventState != EventState.PRE)
            return

        if (MovementUtils.isMoving)
            thePlayer.sprinting = true

        mode?.onMotion()
    }

    @EventTarget
    fun onMove(event: MoveEvent?) {
        if (mc.thePlayer!!.sneaking)
            return
        mode?.onMove(event!!)
    }

    @EventTarget
    fun onTick(event: TickEvent?) {
        if (mc.thePlayer!!.sneaking)
            return

        mode?.onTick()
    }

    override fun onEnable() {
        if (mc.thePlayer == null)
            return

        mc.timer.timerSpeed = 1f

        mode?.onEnable()
    }

    override fun onDisable() {
        if (mc.thePlayer == null)
            return

        mc.timer.timerSpeed = 1f

        mode?.onDisable()
    }

    override val tag: String
        get() = modeValue.get()

    private val mode: SpeedMode?
        get() {
            val mode = modeValue.get()

            for (speedMode in speedModes) if (speedMode.modeName.equals(mode, ignoreCase = true))
                return speedMode

            return null
        }

    private val modes: Array<String>
        get() {
            val list: MutableList<String> = ArrayList()
            for (speedMode in speedModes) list.add(speedMode.modeName)
            return list.toTypedArray()
        }
}

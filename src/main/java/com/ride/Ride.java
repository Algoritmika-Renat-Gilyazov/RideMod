package com.ride;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Ride {
    @SubscribeEvent
    public void onPlayerControl(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // Проверяем, сидит ли игрок на сущности
        if (player.isPassenger() && player.getVehicle() instanceof LivingEntity mob) {
            // Проверяем сторону клиента/сервера
            if (!player.level().isClientSide) {
                // Получаем ввод игрока
                double forward = player.zza; // Вперёд-назад
                double strafe = player.xxa;  // Влево-вправо
                float yaw = player.getYRot(); // Поворот игрока

                double speed = 0.5; // Скорость
                Vec3 movement = new Vec3(
                    -Math.sin(Math.toRadians(yaw)) * forward * speed + Math.cos(Math.toRadians(yaw)) * strafe * speed,
                    mob.getDeltaMovement().y,
                    Math.cos(Math.toRadians(yaw)) * forward * speed + Math.sin(Math.toRadians(yaw)) * strafe * speed
                );

                mob.setDeltaMovement(movement);
                mob.setYRot(yaw); // Устанавливаем поворот
                mob.yBodyRot = yaw; // Синхронизируем тело
                mob.yHeadRot = yaw; // Синхронизируем голову
            }
        }
    }
}

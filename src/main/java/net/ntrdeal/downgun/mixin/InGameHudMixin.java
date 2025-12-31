package net.ntrdeal.downgun.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.ntrdeal.downgun.misc.ModGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique private static final DecimalFormat DECIMAL_FORMAT = Util.make(
            new DecimalFormat("#.#"), format -> format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));

    @Shadow public abstract TextRenderer getTextRenderer();

    @WrapMethod(method = "renderHealthBar")
    private void ntrdeal$changeHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float i, int lastHealth, int j, int absorption, boolean blinking, Operation<Void> original) {
        int boxWidth = 160, boxHeight = 25, boxX = 7, boxY = context.getScaledWindowHeight() - boxHeight - 7;
        float health = player.getHealth(), maxHealth = player.getMaxHealth(), healthRatio = health / maxHealth;
        context.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0x7f000000);
        float fillWidth = (boxWidth - 4) * healthRatio;
        context.fill(boxX + 2, boxY + 2, (int) (boxX + 2 + fillWidth), boxY + boxHeight - 2, ColorHelper.Argb.lerp(healthRatio, 0xffff0000, 0xff00ff00));
        String text = DECIMAL_FORMAT.format(health) + "/" + DECIMAL_FORMAT.format(maxHealth);
        int textWidth = this.getTextRenderer().getWidth(text);
        int textX = (int) (boxX + (boxWidth * 0.5f) - (textWidth * 0.5f)), textY = (int) (boxY + (boxHeight * 0.5f) - 4.5f);
        context.drawTextWithShadow(this.getTextRenderer(), text, textX, textY, 0xffffffff);
        context.drawTextWithShadow(this.getTextRenderer(), "LIVES: " + 3 + "/" + player.getWorld().getGameRules().getInt(ModGameRules.LIVES), boxX + 1, boxY - 10, 0xffffffff);
    }


    @WrapMethod(method = "renderExperienceLevel")
    private void ntrdeal$remove(DrawContext context, RenderTickCounter tickCounter, Operation<Void> original) {}
}

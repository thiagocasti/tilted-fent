package gameObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import math.Vector2D;

public class DamageIndicator {
    private Vector2D position;
    private String text;
    private int duration; // Duración en fotogramas
    private Color color;

    public DamageIndicator(Vector2D position, String text, int duration) {
        this.position = position;
        this.text = text;
        this.duration = duration;
        this.color = Color.RED;
    }

    public void update() {
        position.setY(position.getY() - 1); // Movimiento ascendente
        duration--;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    public void draw(Graphics g) {
        if (duration > 0) {
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.setColor(color);
            g.drawString(text, (int) position.getX(), (int) position.getY());
        }
    }
}


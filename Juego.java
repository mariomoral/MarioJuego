import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Juego {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Juego de Aventuras");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1280, 720);
        ventana.setResizable(false);

        Inicio inicio = new Inicio();
        ventana.add(inicio);
        ventana.setVisible(true);
    }

    private static class Inicio extends JPanel implements KeyListener {
        private int seleccion = 0; // 0: Iniciar Juego, 1: Salir
        private BufferedImage backgroundImage;
        private Font pixelFont;

        public Inicio() {
            addKeyListener(this);
            setFocusable(true);
            setBackground(Color.BLACK);

            // Cargar la imagen de fondo
            try {
                backgroundImage = ImageIO.read(new File("G:\\Mario Gros\\final.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Cargar fuente pixelada desde la carpeta "fonts"
            try {
                InputStream is = getClass().getResourceAsStream("/fonts/Fuente_retro.ttf");
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(25f); // Tamaño de 25
            } catch (Exception e) {
                e.printStackTrace();
                pixelFont = new Font("Arial", Font.PLAIN, 40); // Fuente de respaldo
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Dibujar la imagen de fondo
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            g.setFont(pixelFont); // Aplicar la fuente pixelada
            FontMetrics metrics = g.getFontMetrics(pixelFont);

            // Dibujar opciones de menú centradas horizontalmente
            String iniciarTexto = "Iniciar Juego";
            String salirTexto = "Salir";

            int iniciarX = (getWidth() - metrics.stringWidth(iniciarTexto)) / 2;
            int salirX = (getWidth() - metrics.stringWidth(salirTexto)) / 2;

            // Cambiar color según la selección
            if (seleccion == 0) {
                g.setColor(new Color(0xff5721));
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(iniciarTexto, iniciarX, 400); // Ajustar posición vertical

            if (seleccion == 1) {
                g.setColor(new Color(0xff5721));
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(salirTexto, salirX, 500); // Ajustar posición vertical
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP) {
                seleccion = (seleccion == 0) ? 1 : 0;
                repaint();
            }

            if (key == KeyEvent.VK_DOWN) {
                seleccion = (seleccion == 1) ? 0 : 1;
                repaint();
            }

            if (key == KeyEvent.VK_ENTER) {
                if (seleccion == 0) {
                    iniciarJuego();
                } else if (seleccion == 1) {
                    System.exit(0);
                }
            }
        }

        private void iniciarJuego() {
            JFrame ventana = (JFrame) this.getTopLevelAncestor();
            ventana.remove(this);
            Nivel1 nivel1 = new Nivel1(); // Cambiar a Nivel1 o el primer nivel que desees
            ventana.add(nivel1);
            nivel1.startGame();
            ventana.revalidate();
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
    }
}

package autopathing.interfaz;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Tablero extends javax.swing.JPanel implements MouseListener {

    private int gridSize = 10;
    private JPanel[][] casillas;
    private boolean mousePressed = false;
    private Color selectedColor = new Color(0, 120, 200);
    private boolean mostrandoCamino = false;

    private ArrayList<int[]> camino = new ArrayList<>();
    private int[] coordStart = new int[2];
    private int[] coordEnd = new int[2];

    public Tablero() {
        initComponents();
        casillas = new JPanel[0][0];
        createGrid();
    }

    public void createGrid() {
        this.removeAll();
        this.setLayout(new GridLayout(gridSize, gridSize));
        casillas = new JPanel[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                casillas[i][j] = new JPanel();
                casillas[i][j].setBackground(new Color(0, 150, 0));
                casillas[i][j].setName("" + i + "," + j);
                casillas[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                casillas[i][j].addMouseListener(this);
                this.add(casillas[i][j]);
            }
        }
        this.updateUI();
    }

    public String generateCode() {
        String ret = "";

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                Color c = casillas[i][j].getBackground();
                if (c.equals(pared)) {
                    ret += "1";
                } else if (c.equals(start)) {
                    ret += "3";
                } else if (c.equals(end)) {
                    ret += "4";
                } else {
                    ret += "0";
                }
            }
        }

        return ret;
    }

    //Condicion: Se asume que la raiz del largo de code es un natural.
    public void loadCode(String code) {
        if (Math.sqrt(code.length()) != gridSize) {
            gridSize = (int) Math.sqrt(code.length());
            createGrid();
        }

        int cont = 0;

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas.length; j++) {
                switch (code.charAt(cont)) {
                    case '1':
                        casillas[i][j].setBackground(pared);
                        break;
                    case '3':
                        casillas[i][j].setBackground(start);
                        break;
                    case '4':
                        casillas[i][j].setBackground(end);
                        break;
                    default:
                        casillas[i][j].setBackground(piso);
                        break;
                }
                cont++;
            }
        }
    }

    public boolean verifyCode(String code) {
        double aux = Math.sqrt(code.length());
        int aux2 = (int) aux;
        return aux - aux2 == 0;
    }

    public void pintarCamino() {
        for (int i = 1; i < camino.size() - 1; i++) {
            casillas[camino.get(i)[1]][camino.get(i)[0]].setBackground(new Color(250, 230, 0));
        }
        mostrandoCamino = true;
    }

    public void borrarCamino() {
        if (mostrandoCamino) {
            for (int i = 1; i < camino.size() - 1; i++) {
                casillas[camino.get(i)[1]][camino.get(i)[0]].setBackground(piso);
            }
        }
        mostrandoCamino = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(2, 2, 2));
        setPreferredSize(new java.awt.Dimension(1000, 1000));
        setLayout(new java.awt.GridLayout());
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        mousePressed = true;
        mouseEntered(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (mousePressed) {
            int[] coord = getCoord(((JPanel) me.getSource()).getName());

            if (coord[0] == coordStart[0] && coord[1] == coordStart[1]) {
                coordStart[0] = -1;
                coordStart[1] = -1;
            }

            if (coord[0] == coordEnd[0] && coord[1] == coordEnd[1]) {
                coordEnd[0] = -1;
                coordEnd[1] = -1;
            }

            if (selectedColor.equals(start)) {
                if (coordStart[0] != -1) {
                    casillas[coordStart[0]][coordStart[1]].setBackground(piso);
                }
                coordStart[0] = coord[0];
                coordStart[1] = coord[1];
            }

            if (selectedColor.equals(end)) {
                if (coordEnd[0] != -1) {
                    casillas[coordEnd[0]][coordEnd[1]].setBackground(piso);
                }
                coordEnd[0] = coord[0];
                coordEnd[1] = coord[1];
            }
            
            borrarCamino();
            casillas[coord[0]][coord[1]].setBackground(selectedColor);
        }
    }

    private int[] getCoord(String name) {
        int[] ret = new int[2];

        String aux = "";
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ',') {
                ret[0] = Integer.parseInt(aux);
                aux = "";
            } else {
                aux += name.charAt(i);
            }
        }
        ret[1] = Integer.parseInt(aux);

        return ret;
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color c) {
        this.selectedColor = c;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int size) {
        this.gridSize = size;
    }

    public ArrayList<int[]> getCamino() {
        return camino;
    }

    public void setCamino(ArrayList<int[]> camino) {
        this.camino = camino;
    }

    public Color piso = new Color(0, 150, 0);
    public Color pared = new Color(0, 120, 200);
    public Color start = new Color(220, 0, 0);
    public Color end = new Color(120, 0, 200);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

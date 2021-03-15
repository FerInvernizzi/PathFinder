package autopathing;

import autopathing.interfaz.Ventana;
import java.util.ArrayList;
import java.util.Arrays;

public class PathFinder {

    private int[][] mapa;
    private Ventana ventana;
    private int largoCamino = -1;

    public PathFinder() {
        ventana = new Ventana(this);
        ventana.setVisible(true);
    }

    // Recibe un String con ceros, unos, un 3 y un 4. Y crea un mapa en base a ese String.
    public void crearMapa(String codigo) {
        int size = (int) Math.sqrt(codigo.length());
        mapa = new int[size][size];
        int cont = 0;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (cont < codigo.length()) {
                    mapa[x][y] = Integer.parseInt("" + codigo.charAt(cont));
                } else {
                    mapa[x][y] = 0;
                }
                cont++;
            }
        }
    }

    // Retorna una lista con las coordenadas que dibujan el camino mas eficiente para llegar al destino
    public ArrayList<int[]> buscarCamino() {
        largoCamino = -1;
        return buscarCamino(hallarLandMark(3), hallarLandMark(4), new ArrayList<>());
    }

    // Retorna una lista con las coordenadas que dibujan el camino más corto para llegar al destino
    private ArrayList<int[]> buscarCamino(int[] coorA, int[] coorD, ArrayList<int[]> camino) {
        camino.add(coorA);

        if (mapa[coorA[0]][coorA[1]] == 4) {
            if (!esMuyLargo(camino)) {
                largoCamino = camino.size();
            }
            return camino;
        }

        mapa[coorA[0]][coorA[1]] = 2;

        if (!esMuyLargo(camino)) {
            ArrayList<int[]> camino1 = null;
            ArrayList<int[]> camino2 = null;
            ArrayList<int[]> camino3 = null;
            ArrayList<int[]> camino4 = null;

            int[] prioridad = elegirPrioridades(coorA, coorD);
            for (int i = 0; i < prioridad.length; i++) {
                switch (prioridad[i]) {
                    case 1:
                        camino1 = buscarCaminoRecCall(coorA[0], coorA[1] + 1, coorD, camino); // Abajo
                        break;
                    case 2:
                        camino2 = buscarCaminoRecCall(coorA[0] - 1, coorA[1], coorD, camino); // Izq
                        break;
                    case 3:
                        camino3 = buscarCaminoRecCall(coorA[0] + 1, coorA[1], coorD, camino); // Der
                        break;
                    default:
                        camino4 = buscarCaminoRecCall(coorA[0], coorA[1] - 1, coorD, camino); // Arriba
                        break;
                }
            }

            mapa[coorA[0]][coorA[1]] = 0;
            return caminoMasCorto(caminoMasCorto(camino1, camino2), caminoMasCorto(camino3, camino4));
        }

        mapa[coorA[0]][coorA[1]] = 0;
        return new ArrayList<>();
    }

    public int[] elegirPrioridades(int[] pActual, int[] pFinal) {
        int deltaX = pFinal[0] - pActual[0];
        int deltaY = pFinal[1] - pActual[1];
        int[] ret = new int[4];

        if (deltaY > 0) {
            ret[0] = 1; // Abajo más prioritario que Arriba
            ret[2] = 4;
        } else {
            ret[0] = 4; // Arriba más prioritario que Abajo
            ret[2] = 1;
        }

        if (deltaX > 0) { // Der más prioritario que Izq
            if (Math.abs(deltaX) > Math.abs(deltaY)) { // Dir en eje X más prioritario que la dir en eje Y
                ret[1] = ret[0];
                ret[0] = 3;
                ret[3] = 2;
            } else {
                ret[1] = 3;
                ret[3] = ret[2];
                ret[2] = 2;
            }
        } else { // Izq más prioritario que Der
            if (Math.abs(deltaX) > Math.abs(deltaY)) { // Dir en eje X más prioritario que la dir en eje Y
                ret[1] = ret[0];
                ret[0] = 2;
                ret[3] = 3;
            } else {
                ret[1] = 2;
                ret[3] = ret[2];
                ret[2] = 3;
            }
        }
        return ret;
    }

    public ArrayList<int[]> buscarCaminoRecCall(int coorX, int coorY, int[] coorD, ArrayList<int[]> camino) {
        if (checkIndex(coorX, coorY, mapa.length) && !checkObstaculo(coorX, coorY)) { // Checkea si esa casilla es "caminable"
            int[] nuevasCoord = {coorX, coorY};
            return buscarCamino(nuevasCoord, coorD, (ArrayList<int[]>) camino.clone());
        }
        return new ArrayList<>();
    }

    // Retorna true si el camino actual es mas largo que el mejor camino encontrado hasta ahora
    public boolean esMuyLargo(ArrayList<int[]> camino) {
        if (largoCamino == -1) {
            return false;
        }
        return largoCamino <= camino.size();
    }

    //Retorna true si no hay espacios disponibles para seguir con su camino
    public boolean estaAcorralado(int[] coor) {
        boolean ret = true;

        if (checkIndex(coor[0], coor[1] + 1, mapa.length) && !checkObstaculo(coor[0], coor[1] + 1)) {
            ret = false;
        } else if (checkIndex(coor[0] - 1, coor[1], mapa.length) && !checkObstaculo(coor[0] - 1, coor[1])) {
            ret = false;
        } else if (checkIndex(coor[0] + 1, coor[1], mapa.length) && !checkObstaculo(coor[0] + 1, coor[1])) {
            ret = false;
        } else if (checkIndex(coor[0], coor[1] - 1, mapa.length) && !checkObstaculo(coor[0], coor[1] - 1)) {
            ret = false;
        }

        return ret;
    }

    //Recibe dos caminos y retorna el mas corto
    public ArrayList<int[]> caminoMasCorto(ArrayList<int[]> camino1, ArrayList<int[]> camino2) {
        if (camino1.isEmpty()) {
            return camino2;
        } else if (camino2.isEmpty()) {
            return camino1;
        } else if (camino1.size() < camino2.size()) {
            return camino1;
        }
        return camino2;
    }

    //Retorna true si hay un obstaculo (camino o pared)
    public boolean checkObstaculo(int x, int y) {
        return mapa[x][y] == 1 || mapa[x][y] == 2;
    }

    //Checkea si las coordenadas pertenecen o no a la matriz
    public boolean checkIndex(int x, int y, int max) {
        return x >= 0 && y >= 0 && x < max && y < max;
    }

    //Retorna la posicion del ultimo int a que encuentra
    public int[] hallarLandMark(int a) {
        int[] ret = new int[2];

        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa.length; x++) {
                if (mapa[x][y] == a) {
                    ret[0] = x;
                    ret[1] = y;
                }
            }
        }
        return ret;
    }
}

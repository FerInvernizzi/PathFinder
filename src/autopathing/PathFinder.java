package autopathing;

import autopathing.interfaz.Ventana;
import java.util.ArrayList;

public class PathFinder {
    private int[][] mapa;
    private Ventana v;
    
    public PathFinder() {
        v = new Ventana(this);
        v.setVisible(true);
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
    
    public ArrayList<int[]> buscarCamino(){
        return buscarCamino(hallarLandMark(3), new ArrayList<>());
    }
    
    private ArrayList<int[]> buscarCamino(int[] coor, ArrayList<int[]> camino) {
        camino.add(coor);

        //Casos base
        if (mapa[coor[0]][coor[1]] == 4) {
            return camino;
        }
        //Pasos recursivos
        mapa[coor[0]][coor[1]] = 2;

        ArrayList<int[]> camino1 = new ArrayList<>();
        ArrayList<int[]> camino2 = new ArrayList<>();
        ArrayList<int[]> camino3 = new ArrayList<>();
        ArrayList<int[]> camino4 = new ArrayList<>();

        if (checkIndex(coor[0], coor[1] + 1, mapa.length) && !checkObstaculo(coor[0], coor[1] + 1)) {
            int[] nuevas_coord = {coor[0], coor[1] + 1};
            camino1 = buscarCamino(nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0] - 1, coor[1], mapa.length) && !checkObstaculo(coor[0] - 1, coor[1])) {
            int[] nuevas_coord = {coor[0] - 1, coor[1]};
            camino2 = buscarCamino(nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0] + 1, coor[1], mapa.length) && !checkObstaculo(coor[0] + 1, coor[1])) {
            int[] nuevas_coord = {coor[0] + 1, coor[1]};
            camino3 = buscarCamino(nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0], coor[1] - 1, mapa.length) && !checkObstaculo(coor[0], coor[1] - 1)) {
            int[] nuevas_coord = {coor[0], coor[1] - 1};
            camino4 = buscarCamino(nuevas_coord, (ArrayList<int[]>) camino.clone());
        }

        mapa[coor[0]][coor[1]] = 0;

        ArrayList<int[]> finalista1 = caminoMasCorto(camino1, camino2);
        ArrayList<int[]> finalista2 = caminoMasCorto(camino3, camino4);

        return caminoMasCorto(finalista1, finalista2); //A.K.A Ganador
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

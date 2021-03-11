package autopathing;

import java.util.ArrayList;

public class AutoPathing {

    public static void main(String[] args) {

        //Mapa de prueba
        int[][] mapa = crearMapa(10, "3000000010011101111000100000100111101000000100111111110110000101000010000101101001110111100000000004");
        
        int[] pos_per = hallarLandMark(mapa, 3);
        int[] fin = hallarLandMark(mapa, 4);

        imprimirMapa(mapa);

        ArrayList<int[]> cam = new ArrayList<>();
        ArrayList<int[]> camino = buscarCamino(mapa, pos_per, cam);

        pintarCamino(camino, mapa, pos_per, fin);

        imprimirMapa(mapa);
        System.out.println("Hay que moverse " + (camino.size() - 1) + " casillas para llegar al destino");
        System.out.println("");
    }

    /*
    Piso = 0
    Muros = 1
    Camino = 2              |   Esta funcion encuentra el camino mas eficiente para llegar del
    Posicion inicial = 3    |   punto de partida al final
    Destino = 4
     */
    public static ArrayList<int[]> buscarCamino(int[][] mapa, int[] coor, ArrayList<int[]> camino) {

        camino.add(coor);

        //Casos base
        if (mapa[coor[0]][coor[1]] == 4) {
            return camino;
        }
        if (estaAcorralado(coor, mapa)) {
            return new ArrayList<>();
        }

        //Pasos recursivos
        mapa[coor[0]][coor[1]] = 2;

        ArrayList<int[]> camino1 = new ArrayList<>();
        ArrayList<int[]> camino2 = new ArrayList<>();
        ArrayList<int[]> camino3 = new ArrayList<>();
        ArrayList<int[]> camino4 = new ArrayList<>();

        if (checkIndex(coor[0], coor[1] + 1, mapa.length) && !checkObstaculo(coor[0], coor[1] + 1, mapa)) {
            int[] nuevas_coord = {coor[0], coor[1] + 1};
            camino1 = buscarCamino(mapa, nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0] - 1, coor[1], mapa.length) && !checkObstaculo(coor[0] - 1, coor[1], mapa)) {
            int[] nuevas_coord = {coor[0] - 1, coor[1]};
            camino2 = buscarCamino(mapa, nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0] + 1, coor[1], mapa.length) && !checkObstaculo(coor[0] + 1, coor[1], mapa)) {
            int[] nuevas_coord = {coor[0] + 1, coor[1]};
            camino3 = buscarCamino(mapa, nuevas_coord, (ArrayList<int[]>) camino.clone());
        }
        if (checkIndex(coor[0], coor[1] - 1, mapa.length) && !checkObstaculo(coor[0], coor[1] - 1, mapa)) {
            int[] nuevas_coord = {coor[0], coor[1] - 1};
            camino4 = buscarCamino(mapa, nuevas_coord, (ArrayList<int[]>) camino.clone());
        }

        mapa[coor[0]][coor[1]] = 0;

        ArrayList<int[]> finalista1 = caminoMasCorto(camino1, camino2);
        ArrayList<int[]> finalista2 = caminoMasCorto(camino3, camino4);

        return caminoMasCorto(finalista1, finalista2); //A.K.A Ganador
    }

    //Retorna true si no hay espacios disponibles para seguir con su camino
    public static boolean estaAcorralado(int[] coor, int[][] mapa) {
        boolean ret = true;

        if (checkIndex(coor[0], coor[1] + 1, mapa.length) && !checkObstaculo(coor[0], coor[1] + 1, mapa)) {
            ret = false;
        } else if (checkIndex(coor[0] - 1, coor[1], mapa.length) && !checkObstaculo(coor[0] - 1, coor[1], mapa)) {
            ret = false;
        } else if (checkIndex(coor[0] + 1, coor[1], mapa.length) && !checkObstaculo(coor[0] + 1, coor[1], mapa)) {
            ret = false;
        } else if (checkIndex(coor[0], coor[1] - 1, mapa.length) && !checkObstaculo(coor[0], coor[1] - 1, mapa)) {
            ret = false;
        }

        return ret;
    }

    //Recibe dos caminos y retorna el mas corto
    public static ArrayList<int[]> caminoMasCorto(ArrayList<int[]> camino1, ArrayList<int[]> camino2) {
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
    public static boolean checkObstaculo(int x, int y, int[][] mapa) {
        return mapa[x][y] == 1 || mapa[x][y] == 2;
    }

    //Checkea si las coordenadas pertenecen o no a la matriz
    public static boolean checkIndex(int x, int y, int max) {
        return x >= 0 && y >= 0 && x < max && y < max;
    }

    //Imprime nuestro mapa
    public static void imprimirMapa(int[][] mapa) {
        for (int x = mapa.length - 1; x >= 0; x--) {
            for (int y = 0; y < mapa.length; y++) {

                switch (mapa[y][x]) {
                    case 1:
                        System.out.print(ANSI_PURPLE + "■ ");
                        break;
                    case 2:
                        System.out.print(ANSI_YELLOW + "■ ");
                        break;
                    case 3:
                        System.out.print(ANSI_WHITE + "■ ");
                        break;
                    case 4:
                        System.out.print(ANSI_RED + "■ ");
                        break;
                    default:
                        System.out.print(ANSI_GREEN + "■ ");
                        break;
                }

            }
            System.out.println("");
        }
        System.out.println("");
    }

    //Agrega al mapa las casillas que representan el camino
    public static void pintarCamino(ArrayList<int[]> camino, int[][] mapa, int[] pos_per, int[] fin) {
        for (int i = 1; i < camino.size(); i++) {
            mapa[camino.get(i)[0]][camino.get(i)[1]] = 2;
        }
        mapa[pos_per[0]][pos_per[1]] = 3;
        mapa[fin[0]][fin[1]] = 4;
    }

    // Recibe un String con ceros, unos, un 3 y un 4. Y crea un mapa en base a ese String.
    public static int[][] crearMapa(int size, String codigo) {
        int[][] mapa = new int[size][size];
        int cont = 0;
        
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                if (cont < codigo.length()) {
                    mapa[x][y] = Integer.parseInt("" + codigo.charAt(cont));
                } else {
                    mapa[x][y] = 0;
                }
                cont++;
            }
        }

        return mapa;
    }
    
    public static int[] hallarLandMark(int[][] mapa, int a) {
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

    //Colores ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

}

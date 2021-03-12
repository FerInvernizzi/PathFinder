package autopathing;

public class Main {

    private static PathFinder pf;
    
    
    public static void main(String[] args) {
        pf = new PathFinder();
    }

    /*
    Piso = 0
    Muros = 1
    Camino = 2              |   Esta funcion encuentra el camino mas eficiente para llegar del
    Posicion inicial = 3    |   punto de partida al final
    Destino = 4
     */
    

    

    //Imprime nuestro mapa
    /*
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
    }*/

    

    

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

package src.edd;
import java.util.Iterator;
import java.util.Vector;

import src.edd.Cola;
import src.edd.IteradorLista;
import src.edd.Lista;
public class Practica3 {
    public void sumaCercana(Lista<Integer> lista, int n){

    }

    /**
     * Metodo para imprimir todas las permutaciones posibles de una cadena
     * @param cadena
     */
    public static void permutaCadena(String cadena){
        //Hacemos el método permuta con la cadena. Este metodo nos regresa una lista que guardaremos en la lista permutaciones
        Lista<String> permutaciones=permuta(cadena);
        //Hacemos el iterador de la lista de permutaciones para imprimir cada permutación obtenida con el método permuta
        Iterator<String> iterador=permutaciones.iterator();
        for(int i=0;i<permutaciones.size();i++){
            System.out.println(iterador.next());
        }
    }

    /**
     * Metodo privado recursivo para obtener una lista de las permutaciones sin repeticiones de una cadena 
     * @param cadena
     * @return Lista<String>
     */
    private static Lista<String> permuta(String cadena){
        //Caso base: Si la longitud de la cadena es 0 o 1
        if(cadena.length()==0||cadena.length()==1){
            //Creamos la lista que regresaremos y le añadimos la cadena
            Lista<String> lista= new Lista<String>();
            lista.add(cadena);
            return lista;
        }else{
            //Estrategia que utilizaremos: intercambiar el primer elemento por el j-ésimo y generar todas las permutaciones para los n-1 siguientes
            //Hacemos recursión con la cadena actual (actual en la recursión) sin el caracter inicial
            Lista<String> lista1=permuta(cadena.substring(1));
            //Obtenemos el primer caracter de la cadena
            char inicial=cadena.charAt(0);
            //Hacemos el iterador para recorrer la lista obtenida con recursión
            Iterator<String> it=lista1.iterator();
            //Variable auxiliar para recorrer la lista1 con el iterador
            String actual="";
            //Creamos la lista donde iremos guardando las permutaciones
            Lista<String> lista2=new Lista<String>();
            //ciclo for para recorrer todos los elementos de la lista 1
            for(int i=0;i<lista1.size();i++){
                //actual es el elemento (cadena) siguiente de la lista
                actual=it.next();
                //ciclo for para recorrer todos los caracteres de la cadena actual
                ciclo:
                for(int j=0;j<=actual.length();j++){
                    //Comparamos el ultimo caracter de la cadena actual con el caracter inicial para ver si hay repeticiones
                    if(actual.substring(0,j).endsWith(""+inicial)){
                        //si hay repeticiones rompemos el ciclo for
                        break ciclo;
                    }else{
                        //si no hay repeticiones, ponemos al caracter inicial antes de j y la añadimos la cadena resultante a la lista que regresaremos 
                        lista2.add(actual.substring(0,j)+inicial+actual.substring(j));
                    }
                }
            }
            return lista2;
        }

    }
    /*public static void N_reinas(int n){
        int[][] tablero=new int[n][n];
        int[] arreglo=new int[n];
        int primeraFilaVacia=0;
        nReinas(arreglo,primeraFilaVacia, n);
        for(int i=0;i<n;i++){
            System.out.println(arreglo[i]);
        }
    }

    private static void nReinas(int[] arreglo, int primeraFilaVacia, int n){
        if(primeraFilaVacia>n){
            return;
        }else{
            for(int i=0;i<n;i++){
                boolean valido=true;
                for(int j=1;j<primeraFilaVacia;j++){
                    if(arreglo[j]==i||arreglo[j]==i+primeraFilaVacia-j||arreglo[j]==i-primeraFilaVacia+j){
                        valido=false;
                    }
                    if(valido==true){
                        arreglo[primeraFilaVacia]=j;
                        nReinas(arreglo,primeraFilaVacia+1,n);
                    }
                }
            }
        }
    }*/

    /*public static void n_reinas(int n){
        String[][] tablero=new String[n][n];
        int[] c=new int[n];
        boolean[] f = new boolean[n];
        for (int i = 0; i < n; i++) {
            f[i]=true;
        }
        int m=2*n-1;
        boolean[] dp=new boolean[m];
        for (int i = 0; i < m ; i++) {
            dp[i]=true;
        }
        boolean[] ds=new boolean[m];
        for(int i=0; i<m;i++){
            ds[i] = true;
        }
        buscarReinas(n,0,c,f,dp,ds);
        for(int i=0;i<n;i++){
            tablero[c[i]][i]="R"+i;
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(tablero[i][j]==null){
                    System.out.print("-- ");
                }else{
                    System.out.print(tablero[i][j]+" ");
                }
                
            }
            System.out.println();
        }
    }*/

    /*private static void buscarReinas(int n, int i, int[] solucion, boolean[] f, boolean[] dp, boolean[] ds){
        for (int j = 0; j < n; j++) {
            if(f[j]&&dp[i-j+n-1]&&ds[i+j]){
                solucion[i]=j;
                f[j]=false;
                dp[i-j+n-1]=false;
                ds[i+j]=false;
                if(i==n-1){

                }
                else{
                    buscarReinas(n, i+1, solucion, f, dp, ds);
                }

                f[j]=true;
                dp[i-j+n-1]=true;
                ds[i+j]=true;
            }
        }
    }*/

    public static void N_reinas(int n){
        String[][] tablero=new String[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                tablero[i][j]="- ";
            }
        }
        backtracking(tablero,0,n);
        
    }

    private static void backtracking(String[][] tablero, int numColumna, int n){
        for(int numFila=0; numFila<tablero.length;numFila++){
            if(validaPosicion(tablero, numFila, numColumna)){
                tablero[numFila][numColumna]="R ";
                if(numColumna<n-1){
                    backtracking(tablero,numColumna+1,n);
                }else{
                    for(int i=0;i<n;i++){
                        for(int j=0;j<n;j++){
                            System.out.print(tablero[i][j]+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("**********************************************************************");
                }
                tablero[numFila][numColumna]="- ";
            }
        }   
    }

    private static boolean validaPosicion(String[][] tablero, int numFila, int numColumna){
        for(int i=0;i<numColumna;i++){
            if(tablero[numFila][i].equals("R ")){
                return false;
            }
        }
        for(int i=0;i<tablero.length&&(numFila-i)>=0&&(numColumna-i)>=0;i++){
            if(tablero[numFila-i][numColumna-i].equals("R ")){
                return false;
            }
        }
        for(int i=0;i<tablero.length&&(numFila+i)<tablero.length&&numColumna-i>=0;i++){
            if(tablero[numFila+i][numColumna-i].equals("R ")){
                return false;
            }
        }
        return true;
    }

    public static void primosQueSuman(int suma, int primo, int n){
        //lista de numeros primos hasta suma
        Cola <Integer> primos=cribaEratostenes(suma);
        //quitar los primos menores que primo
        for(int i=0;i<primos.size();i++){
            if(primos.peek()<primo){
                primos.pop();
            }
        }
        //pila para poner las soluciones parciales
        Pila<Integer> pila=new Pila<Integer>();
        int s=0;
        suma(primos,n,suma, pila,0, s);
    }

    private static void suma(Cola<Integer> primos, int n, int suma, Pila<Integer> pila,int nivel, int sumaActual){
        if(nivel==n){//si ya estamos hasta el ultimo nivel del arbol
            if(sumaActual==suma){
                System.out.println(pila);
            }
        }else{
            for(int i=nivel;i<primos.size();i++){

            }
        }
    }
    

    public static Cola<Integer> cribaEratostenes(int n) {
        //Lista donde pondremos los numeros primos de 0 a n
        Cola<Integer> primos=new Cola<Integer>();
        //Ciclo para verificar cada numero de 2 a n
        for (int numero = 2; numero < n; numero++) {
            boolean esPrimo = true;
            // Ciclo for para tomar a los posibles divisores
            for (int divisor = 2; divisor <= numero / 2; divisor++) {
                // Condicional para ver si el número tiene divisores
                if (numero % divisor == 0) {
                    esPrimo = false; // Si el número tiene algún divisor, esPrimo cambia a false
                }
            }
            // Condicional que verifica si esPrimo se mantiene en true
            if (esPrimo) {
                primos.push(numero); // Si esPrimo se mantiene en true, imprimir el número
            }
        }
        return primos;
    }
    

    public static void main(String[] args){
        //permutaCadena("ABC");
        //N_reinas(4);
        Cola<Integer> prueba =cribaEratostenes(10);
        System.out.println(prueba);
    }

    
}

//compareto


package src.edd;
import java.util.Iterator;

import src.edd.Cola;
import src.edd.Pila;
public class P3 {
    
    private static int s=0;

    //#####################################################################################################################################################################################################################################
    /**
     * Metodo para obtener todas las posibles combinaciones de los caracteres de una cadena
     * @param cadena
     */
    public static void permutaCadena(String cadena){
        Pila<Character> pila=new Pila<Character>();
        Cola<Character> cola=new Cola<Character>();
        for(int i=0; i<cadena.length();i++){
            cola.push(cadena.charAt(i));
        }
        permuta(0,pila,cadena.length(),cola);
    }
    
    /**
     * Metodo auxiliar del método permutaCadena
     * @param numRecursion nivel en el arbol
     * @param pila 
     * @param numCaracteres todo lo que podemos bajar en el arbol hasta encontrar una solucion posible
     * @param cola
     */
    private static void permuta(int numRecursion, Pila<Character> pila, int numCaracteres, Cola<Character> cola){
        if(numRecursion==numCaracteres){//cuando ya estamos en el ultimo nivel del arbol
            System.out.println(pila);//mostramos la solución generada
        }else{
            //hacia los lados
            for(int i=numRecursion; i<numCaracteres;i++){//desde el nivel en que estamos hasta el ultimo nivel al que podemos llegar en el arbol porque el numero de permutaciones al bajar en el arbol va disminuyendo
                pila.push(cola.pop());

                permuta(numRecursion+1,pila, numCaracteres,cola);//hacemos llamada recursiva, bajando un nivel en el arbol

                cola.push(pila.pop()); //regresamos los caracteres a la cola cuando regresamos un nivel en el arbol
            }

        }
    }

    //###################################################################################################################################################################################################################################
    /**
     * Método para encontrar todos los posibles tableros en los que se colocan n reinas sin que se ataquen
     * @param n el numero de reinas
     */
    public static void N_reinas(int n){
        //Tablero donde colocaremos a las reinas
        String[][] tablero=new String[n][n];
        //Arreglo que representa las columnas para guardar la posición de cada reina en las filas
        int[] posFilas=new int[n];
        int[] copia=new int[n];
        //Llenamos los arreglos
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                tablero[i][j]="- ";
            }
            posFilas[i]=-1;
        }
        recursiva(0,posFilas, tablero);
    }

    /**
     * Metodo auxiliar del método N_Reinas
     * @param nivel
     * @param posFilas
     * @param tablero
     */
    private static void recursiva(int nivel, int[] posFilas, String[][] tablero){
        int numReinas=posFilas.length;
        //System.out.println(numReinas);
        //System.out.println(nivel);
        if(nivel==numReinas){//Caso base: encontramos una solución cuando el nivel del arbol es el ultimo, cuando ya pudimos poner a todas las reinas
            //System.out.println("base");
            s++;
            /*System.out.println(s);
            for(int i=0;i<numReinas;i++){
                System.out.print(posFilas[i]+" ");
            }*/
            //System.out.println(s);
            System.out.println(s+" ############################");
            for(int i=0;i<numReinas;i++){
                tablero[i][posFilas[i]]="R ";
            }
            for(int i=0; i<numReinas;i++){
                for(int j=0;j<numReinas;j++){
                    System.out.print(tablero[i][j]);
                }
                System.out.println();
            }
            
            for(int i=0;i<numReinas;i++){
                tablero[i][posFilas[i]]="- ";
            }
        
        }else{
            for(posFilas[nivel]=0;posFilas[nivel]<numReinas;posFilas[nivel]++){//nos movemos a través de los nodos en el mismo nivel del arbol
                if(validaMovimiento(posFilas,nivel)){//validamos que el movimiento se pueda realizar
                    //System.out.println("Recursiva");
                    recursiva(nivel+1, posFilas, tablero);//con cada recursión aumentamos un nodo hacia abajo en el arbol
                }
            }
        }
    } 
    /**
     * Metodo auxiliar del metodo recursiva para el método N_reinas
     * @param posFilas arreglo para la posición de las filas de cada reina
     * @param nivel el nivel del arbol
     * @return
     */
    private static boolean validaMovimiento(int[] posFilas, int nivel){
        for(int i=0;i<nivel;i++){
            //Si las reinas están en la misma fila o en la misma diagonal
            if(posFilas[i]==posFilas[nivel]||Math.abs(nivel-i)==Math.abs(posFilas[nivel]-posFilas[i])){
                return false;
            }
        }
        return true;
    }

    //###############################################################################################################################################################################################################################################################3
    public static void primosQueSuman(int suma, int primo, int n){
        //lista de numeros primos hasta suma
        Cola <Integer> primos=cribaEratostenes(suma);
        //quitamos los primos menores que primo
        for(int i=0;i<primos.size();i++){
            if(primos.peek()<=primo){
                primos.pop();
            }
        }
        //pila para poner las soluciones parciales
        Pila<Integer> pila=new Pila<Integer>();
        int s=0;//nivel con el que empezaremos
        int contador=0;
        suma(primos,n,suma, pila,0, s,contador);
    }

    
    private static void suma(Cola<Integer> primos, int n, int suma, Pila<Integer> pila,int nivel, int sumaActual, int contador){
        
        /*if(nivel==1&&){
            System.out.println("Eliminando primer elemento de la pila");
            Pila<Integer> aux=new Pila<Integer>();
                for(int i=0;i<pila.size();i++){
                    aux.push(pila.pop());
                }
                if(!aux.isEmpty()){
                    sumaActual-=aux.pop();
                }
                for(int i=0;i<aux.size();i++){
                    pila.push(aux.pop());
                }
                System.out.println("Pila"+pila);
        }
        //contador++;*/
        if(nivel==n){//Caso base: ya estamos hasta el ultimo nivel del arbol
            System.out.print("Suma: "+sumaActual+" ");
            System.out.print("Nivel: "+nivel+" ");
            System.out.println();
            System.out.println("Caso base");
            if(sumaActual==suma){//Si la suma es el numero, imprimir solución
                System.out.println(pila);
            }
        }else{//si todavía no estamos en el ultimo nivel del arbol
            for(int i=nivel;i<primos.size();i++){
                if(sumaActual+primos.peek()<=suma){//si la sumaActual todavía no se pasa de suma, hacemos la llamada recursiva
                    //sumamos el primer elemento de la cola de primos
                    sumaActual+=primos.peek();
                    //ponemos en la pila el primer elemento de la cola de primos finRango lo eliminamos de primos
                    pila.push(primos.pop());
                    //Hacemos la llamada recursiva bajando un nivel en el arbol
                    System.out.print("Suma: "+sumaActual+" ");
                    System.out.print("Nivel: "+nivel+" ");
                    System.out.println();
                    System.out.println("Pila: "+pila);
                    System.out.println("Cola: "+primos);
                    System.out.println();
                    suma(primos,n,suma,pila,nivel+1,sumaActual, contador);
                    //try{
                        sumaActual-=pila.peek();//restamos el elemento que habíamos puesto antes de la llamada recursiva
                    //}catch(Exception e){

                    //}
                    //try{
                        //if(pila.size()==1&&nivel==1){
                          //  pila.pop();
                            
                        //}  else{
                            primos.push(pila.pop());//regresamos a la cola de primos el elemento que habíamos quitado 
                        //}                      
                    //}catch(Exception e){

                    //}
                    
                }
            } 
        }
    }
    
    /**
     * Metodo auxiliar para obtemner los numeros primos desde 2 hasta un número dado
     * @param n el numero limite
     * @return
     */
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

    //#############################################################################################################################################################################################################################################################
    /**
     * Metodo para encontrar la raíz cuadrada de un numero usando búsqueda binaria
     * Margen de error: 1e − 5
     * @param n numero del que queremos saber su raíz
     */
    public static void sqrtBusqBin(double n){
        //Tomamos un intervalo de 0 a n
        double inicioRango=0;
        double finRango= n;
        //sacamos la mitad del intervalo
        double mitad=(inicioRango+finRango)/2;
        //Margen de error
        double e=0.00001;
        while(Math.abs((mitad*mitad)-n)>=e){
            //si la mitad al cuadrado es menor a n
            if(mitad*mitad<n){
                //actualizamos el inicio del rango a la mitad
                inicioRango=mitad;
            }else{//si la mitad al cuadrado es mayor a n
                //actualizamos el fin del intervalo para que se evalúe hasta la mitad
                finRango=mitad; 
            }
            //sacamos la mitad del nuevo intervalo
            mitad=(inicioRango+finRango)/2;
        }
        System.out.println(mitad);
    }

    //###################################################################################################################################################################################################################################
    public static void main(String[] args){
        //permutaCadena("ABB");
        //N_reinas(8);
        //primosQueSuman(23, 2, 3);
        sqrtBusqBin(45);
    }
}
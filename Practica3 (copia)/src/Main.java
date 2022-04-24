package src.edd;
/**
 * Clase de prueba de la baraja
 */
public class Main {
    public static void main(String[] args){
        /*Baraja baraja = new Baraja();
        System.out.println(baraja.cartas);
        //baraja.CartaSiguiente();
        System.out.println(baraja.CartaSiguiente());
        System.out.println(baraja.CartaSiguiente());
        baraja.shuffle();
        System.out.println(baraja.cartas);
        System.out.println(baraja.CartaSiguiente());*/
        /*Lista<Integer> lista=new Lista<Integer>();
        System.out.println(lista);
        System.out.println("listo");
        lista.add(1);
        System.out.println(lista);
        lista.eliminaEnPos(0);
        System.out.println(lista);*/

        /*Cola<Integer> pila  =new Cola<Integer>();
        pila.push(1);
        pila.push(2);
        System.out.println(pila);
        pila.pop();
        System.out.println(pila);*/
        Lista<Integer> lista=new Lista<Integer>();
        lista.add(20);
        lista.add(15);
        lista.add(10);
        lista.add(5);
        lista.add(17);
        lista.add(2);
        lista.add(7);
        lista.add(12);
        lista.add(4);
        lista.add(7);
        lista.add(18);
        lista.add(19);
        lista.add(16);
        lista.add(3);
        lista.add(30);
        ArbolBinarioBusqueda<Integer> arbol=new ArbolBinarioBusqueda<Integer>();
        arbol=arbol.buildUnsorted(lista);
        System.out.println(arbol);
        //System.out.println(arbol.auxiliar(5));
        //System.out.println(arbol.auxiliar(31));
    }
}

package src.edd;

import java.util.Iterator;

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
        /*Lista<Integer> lista=new Lista<Integer>();
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
        System.out.println(arbol.buscaElemento(2));
        ArbolBinarioBusqueda<Integer> arbol1=new ArbolBinarioBusqueda<Integer>();
        arbol1.convertBST(arbol);
        System.out.println(arbol1);
        System.out.println(arbol.buscaElemento(2));*/
        //System.out.println(arbol.buscReg(2));
        //System.out.println(arbol.auxiliar(5));
        //System.out.println(arbol.auxiliar(31));
        //Iterator<Integer> it=arbol.iterator();
        /*for(int i=0;i<arbol.size();i++){
            System.out.println(it.next());
        }
        while(it.hasNext()){
            System.out.println(it.next());
        }*/
        Lista<Integer> listaP=new Lista<Integer>();
        /*listaP.add(5);
        listaP.add(4);
        listaP.add(3);
        listaP.add(2);
        listaP.add(1);*/
        listaP.add(1);
        listaP.add(2);
        listaP.add(3);
        listaP.add(4);
        listaP.add(5);
        ArbolBinarioBusqueda<Integer> arbolito=new ArbolBinarioBusqueda<Integer>();
        arbolito.buildSorted(listaP);
        System.out.println(arbolito);
    }
}

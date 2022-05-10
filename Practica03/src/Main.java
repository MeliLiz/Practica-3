package src.edd;

import java.util.Iterator;

/**
 * Clase de prueba de los metodos de arboles
 */
public class Main {
    public static void main(String[] args){
        
        /*Lista<Integer> listaP=new Lista<Integer>();
        listaP.add(1);
        listaP.add(2);
        listaP.add(3);
        listaP.add(4);
        listaP.add(5);
        listaP.add(6);
        listaP.add(7);
        listaP.add(8);
        listaP.add(9);
        listaP.add(10);
        System.out.println("Arbol hecho con buildUnsorted a partir de una lista ordenada (será como una lista)");
        ArbolBinarioBusqueda<Integer> arbolito=new ArbolBinarioBusqueda<Integer>();
        arbolito.buildUnsorted(listaP);
        System.out.println(arbolito);
        System.out.println("Arbol hecho con buildSorted a partir de una lista ordenada");
        ArbolBinarioBusqueda<Integer> arbol=new ArbolBinarioBusqueda<Integer>();
        arbol.buildSorted(listaP);
        System.out.println(arbol);
        System.out.println("Arbol hecho con buildSorted a partir de una lista ordenada usando el constructor");
        ArbolBinarioBusqueda<Integer> a=new ArbolBinarioBusqueda<>(listaP, true, true);
        System.out.println(a);
        System.out.println("Arbol hecho con buildUnsorted a partir de una lista ordenada usando el constructor (sera como una lista)");
        ArbolBinarioBusqueda<Integer> b=new ArbolBinarioBusqueda<>(listaP, false, false);
        System.out.println(b);
        Lista<Integer> lista=new Lista<Integer>();
        lista.add(20);
        lista.add(15);
        lista.add(10);
        lista.add(5);
        lista.add(17);
        lista.add(2);
        lista.add(7);
        lista.add(7);
        lista.add(12);
        lista.add(4);
        lista.add(18);
        lista.add(19);
        lista.add(16);
        lista.add(3);
        lista.add(30);
        lista.quitaRepetidos();
        ArbolBinario.Vertice vertice=b.search2(b.raiz, 6);
        b.balance(vertice);
        System.out.println("Balanceamos el subarbol a partir del elemento 6");
        System.out.println(b);
        b.delete(b.raiz, 8);
        System.out.println("Eliminamos el elemento 8 que tiene 2 hijos");
        System.out.println(b);
        System.out.println("Eliminamos el elemento 7 que tiene un hijo");
        b.delete(b.raiz, 7);
        System.out.println(b);
        System.out.println("Eliminamos el elemento 10 que es una hoja");
        b.delete(b.raiz, 10);
        System.out.println(b);
        System.out.println("Agregamos el elemento 8");
        b.insert(8);
        System.out.println(b);
        System.out.println("Agregamos el elemento 10");
        b.insert(10);
        System.out.println(b);
        System.out.println("Buscamos un elemento. Está el 5?");
        System.out.println(b.buscaElemento(5));
        System.out.println("Buscamos un elemento. Está el 50?");
        System.out.println(b.buscaElemento(50));

        System.out.println("Usamos convertBST para convertir el arbol anterior en uno balanceado");
        ArbolBinarioBusqueda<Integer> c=new ArbolBinarioBusqueda<Integer>();
        c.convertBST(b);
        System.out.println(c);
        System.out.println("Probamos el toString");
        System.out.println(b.toString2());*/

        /*ArbolAVL<Integer> arbol=new ArbolAVL();
        System.out.println("Añadimos el primer elemento 21");
        arbol.add(21);
        System.out.println(arbol);
        System.out.println("Añadimos el 15");
        arbol.add(15);
        System.out.println(arbol);
        System.out.println("Añadimos el 7");
        arbol.add(7);
        System.out.println(arbol);
        System.out.println("Añadimos el 1");
        arbol.add(1);
        System.out.println(arbol);
        System.out.println("Añadimos el 0");
        arbol.add(0);
        System.out.println(arbol);
        System.out.println("Añadimos el -1");
        arbol.add(-1);
        System.out.println(arbol);
        System.out.println("Añadimos el -2");
        arbol.add(-2);
        System.out.println(arbol);
        System.out.println("Borramos el 1");
        arbol.delete(1);
        System.out.println(arbol);
        System.out.println("Borramos el -1");
        arbol.delete(-1);
        System.out.println(arbol);
        System.out.println("Borramos el 0");
        arbol.delete(0);
        System.out.println(arbol);*/

        ArbolAVL4<Integer> arbol=new ArbolAVL4<Integer>();
        System.out.println("Agregamos el 21 y el 15");
        arbol.insertar(21);
        arbol.insertar(15);
        System.out.println(arbol);
        System.out.println("Agregamos el 7");
        arbol.insertar(7);
        System.out.println(arbol);
        System.out.println("Agregamos el 80");
        arbol.insertar(80);
        
        System.out.println(arbol);
        System.out.println("Agregamos el 79");
        arbol.insertar(79);
        //arbol.delete(21);
        System.out.println(arbol);
        System.out.println("Agregamos el 8");
        arbol.insertar(8);
        System.out.println(arbol);
        System.out.println("Agregamos el 60");
        arbol.insertar(60);
        System.out.println(arbol);
        System.out.println("Eliminamos el 21");
        arbol.delete(21);
        System.out.println(arbol);
        System.out.println("Eliminamos el 8");
        arbol.delete(8);
        System.out.println(arbol);
        System.out.println("Eliminamos el 15");
        arbol.delete(15);
        System.out.println(arbol);
        
    }
}

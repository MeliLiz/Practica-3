package src.edd;

import java.util.Iterator;

/**
 * Clase de prueba de los metodos de arboles
 */
public class Main {
    public static void main(String[] args) {
        
        /*
         * Lista<Integer> lis P=new Lista<Integer>(
         * listaP.add(1)
         * listaP.add(2)
         * listaP.add(3);
         * listaP.add(
         * listaP.add(5);
         
        li
         * staP.add(6);
         * listaP.add(7);
         * listaP.add(8);
         * listaP.add(9);
         * listaP.add(10
         * System.out.pri
         * ArbolBinarioB
         * arbolito.buil
         * System.out.pri
         * System.out.pr
         * ArbolBinarioB
         * arbol.buildSor
         * System.out.pri
         * System.out.pri
         * ArbolBinarioB
         * System.out.pri
         * System.out.println("Arbol hecho con buildUnsorted a partir de una lista 
         * ArbolBinarioBusqueda<Intege
         * System.out.println(b);
         * Lista<Integer> lista=new Lista<Integer>();
         
        li sta.add(20);
        li sta.add(15);
        li sta.add(10);
        li sta.add(5);
        li sta.add(17);
        li sta.add(2);
        li sta.add(7);
        li sta.add(7);
        li
         * sta.add(12);
         * a.add(4);
         * l
         * lista.add(19);
         * a.add(16);
         * l
         ista.add(3);
        lista.add(30);  
        li
         * sta.quitaRepet
         * ArbolBinario.V
         * b.balance(vert
         * System.out.pri
         * System.out.pri
         ntln(b);
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
        b. insert(8);
        System.out.println(b);  
        System.out.println("Agregamos el elemento 10");
        b.insert(10);
        System.out.println(b);  
        System.out.println("Buscamos un elemento. Está el 5?");
        System.out.println(b.buscaElemento(5));
        System.out.println("Buscamos un   elemento. Está el 50?");
        System.out.println(b.buscaElemento(50));
  
        System.out.println("Usamos convertBST para convertir el arbol anterior en uno balanceado");
        Ar bolBinarioBusqueda<Integer> c=new ArbolBinarioBusqueda<Integer>();
        c. convertBST(b);
        System.out.println(c ) ;
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
        ar bol.add(0);
        Sy
         * stem.out.println(arbol);
         * System.out.println("Añadimos el -1");
         * arbol.add(-1);
         
        System.out.println(arbol);
        Sy stem.out.println("Añadimos el -2");
        arbol.add(-2);  
        System.out.println(arbol);
        System.out.println("Borramos el 1");
        ar
         * bol.delete(1);
         * System.out.println(arb
         * System.out.println("
         * arbol.delete(-1);
         
        Sy stem.out.println(arbol);
        Sy stem.out.println("Borr

        Sy stem.out.println(arbol);*/
 
        System.out.println("PRUEBAS ÁRBOL AVL");

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

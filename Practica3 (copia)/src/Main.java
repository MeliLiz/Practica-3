package src.edd;

import java.util.Iterator;

/**
 * Clase de prueba de los metodos de arboles
 */
public class Main {
    public static void main(String[] args){

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
        arbol.buildUnsorted(lista);
        System.out.println(arbol);
        System.out.println(arbol.buscaElemento(2));*/
        //ArbolBinarioBusqueda<Integer> arbol1=new ArbolBinarioBusqueda<Integer>();
        //arbol1.convertBST(arbol);
        //System.out.println(arbol1);
        //System.out.println(arbol.buscaElemento(2));
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
        listaP.add(6);
        listaP.add(7);
        listaP.add(8);
        listaP.add(9);
        listaP.add(10);
        //listaP.add(30);
        ArbolBinarioBusqueda<Integer> arbolito=new ArbolBinarioBusqueda<Integer>();
        arbolito.buildUnsorted(listaP);
        System.out.println(arbolito);
        ArbolBinarioBusqueda<Integer> arbol=new ArbolBinarioBusqueda<Integer>();
        arbol.buildSorted(listaP);
        System.out.println(arbol);
        ArbolBinarioBusqueda<Integer> a=new ArbolBinarioBusqueda<>(listaP, true, true);
        System.out.println(a);
        ArbolBinarioBusqueda<Integer> b=new ArbolBinarioBusqueda<>(listaP, false, false);
        System.out.println(b);
        //System.out.println(Math.ceil(15/2));
        //System.out.println(5/2);
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
        System.out.println(lista);
        //System.out.println(lista);
        /*ArbolBinarioBusqueda<Integer> arbolito=new ArbolBinarioBusqueda<Integer>();
        arbolito=arbolito.buildUnsorted(lista);
        System.out.println(arbolito);*/
        System.out.println("*******************************************************");
        //b.add(30);
        ArbolBinario.Vertice vertice=b.search2(b.raiz, 6);
        b.balance(vertice);
        System.out.println(b);
        /*b.delete(b.raiz, 9);
        System.out.println(b);
        b.delete(b.raiz, 7);
        System.out.println(b);*/
        //b.delete(b.raiz, 8);
        //System.out.println(b);
        
        //b.balance(b.raiz);
        //System.out.println(b);
    }
}

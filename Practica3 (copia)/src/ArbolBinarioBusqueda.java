package src.edd;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

import src.edd.ArbolBinario;
import src.edd.Lista;
import src.edd.Pila;

public class ArbolBinarioBusqueda<T extends Comparable> extends ArbolBinario<T> {
    //Clase privada para un iterador de árboles bst
    private class Iterador implements Iterator<T>{
        //pila para ir almacenando los elemetos. primero los de la izq y luego los de la derecha
        Pila<Vertice> pila=new Pila<Vertice>();
        /**
         * Constructor
         */
        public Iterador(){
            //Metemos todos los nodos izquierdos a la pilaa partir de la raíz
            while(raiz!=null){
                pila.push(raiz);
                if(raiz.hayIzquierdo()){
                    raiz=raiz.izquierdo;
                }else{
                    raiz=null;
                }
                
            }
        }
        /**
         * Método para saber si el árbol tiene siguiente elemento
         */
        public boolean hasNext(){
            return !pila.isEmpty();
        }
        
        /**
         * Metodo que devuelve el siguiente elemento del arbol
         * Recorrido inorrder
         * @throws NoSuchElementException
         */
        public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Vertice vertice=pila.pop();
            T elemento=vertice.elemento;
            if(vertice.hayDerecho()){
                vertice=vertice.derecho;
                while(vertice!=null){
                    pila.push(vertice);
                    if(vertice.hayIzquierdo()){
                        vertice=vertice.izquierdo;
                    }else{
                        vertice=null;
                    }
                }
            }
            
            
            return elemento;

        }

    }

    @Override public void add(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException();
        }
        //El nuevo vertice
        Vertice nuevo = nuevoVertice(elemento);
        if (isEmpty()) {
            raiz = nuevo;
        }else{
            Vertice aux=raiz;
            verifica(nuevo, aux);
        }
    }

    private void verifica(Vertice nuevo, Vertice actual){
        //Si el elemento es igual al auxiliar no hacemos nada porque sería un elemento repetido
        if(!actual.elemento.equals(nuevo.elemento)){
            if(actual.elemento.compareTo(nuevo.elemento)<1){
                if(actual.hayIzquierdo()){
                    actual=actual.izquierdo;
                    verifica(nuevo,actual);
                }else{
                    actual.izquierdo=nuevo;
                    nuevo.padre=actual;
                }
            }else{
                if(actual.hayDerecho()){
                    actual=actual.derecho;
                    verifica(nuevo,actual);
                }else{
                    actual.derecho=nuevo;
                    nuevo.padre=actual;
                }
            }
        }
    }

    /**
     * Metodo para contruir una arbol a partir de una lista no ordenada
     * @param lista
     * @return
     */
    public ArbolBinarioBusqueda<T> buildUnsorted(Lista<T> lista){
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        Iterator<T> iterador=lista.iterator();
        for(int i=0;i<lista.size();i++){
            arbol.add(iterador.next());
        }
        return arbol;
    }

    /**
     * Metodo para contruir una arbol a partir de una lista ordenada
     * @param lista
     * @return
     */
    public ArbolBinarioBusqueda<T> buildSorted(Lista<T> lista){
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        Iterator<T> iterador=lista.iterator();
        for(int i=0;i<lista.size();i++){
            arbol.add(iterador.next());
        }
        return arbol;
    }

    public boolean search(Vertice raiz, T elemento){
        if(raiz.elemento.equals(elemento)){
            return true;
        }else{
            if(raiz.elemento.compareTo(elemento)<0){
                if(raiz.hayIzquierdo()){
                    return search(raiz.izquierdo, elemento);
                }else{
                    return false;
                }
            }else{
                if(raiz.hayDerecho()){
                    return search(raiz.derecho, elemento);
                }else{
                    return false;
                }
            }
        }
    }

    /*public boolean auxiliar(T elemento){
        return search(this.raiz,elemento);
    }*/

    @Override public Iterator<T> iterator(){
        return new Iterador();
    }
}

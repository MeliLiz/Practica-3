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

    /**
     * Método para añadir un elemento al arbol bst
     * @param elemento
     */
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

    /**
     * Metodo auxiliar de add para verificar la posición en la que se debe añadir el nuevo elemento
     * @param nuevo vertice a insertar
     * @param actual vertice actual auxiliar
     */
    private void verifica(Vertice nuevo, Vertice actual){
        //Si el elemento es igual al auxiliar no hacemos nada porque sería un elemento repetido
        if(!actual.elemento.equals(nuevo.elemento)){
            
            if(actual.elemento.compareTo(nuevo.elemento)<1){//Si el elemento del nodo nuevo es menor que el elemento del vertice actual
                if(actual.hayIzquierdo()){//y el nodo actual tiene hijo izquierdo
                    actual=actual.izquierdo;//el hijo izquierdo será el nodo actual
                    verifica(nuevo,actual);//y hacemos la verificación con el nodo actual(que ahora es el hijo izquierdo)
                }else{//si no tiene hijo izquierdo, entonces insertamos el nodo nuevo a la izquierda del actual
                    actual.izquierdo=nuevo;
                    nuevo.padre=actual;
                    elementos++;
                }
            }else{//si el elemento del nodo nuevo es mayor al del nodo actual, hacemos lo mismo que lo anterior pero con los hijos derechos
                if(actual.hayDerecho()){
                    actual=actual.derecho;
                    verifica(nuevo,actual);
                }else{
                    actual.derecho=nuevo;
                    nuevo.padre=actual;
                    elementos++;
                }
            }
        }
    }

    /**
     * Metodo para contruir una arbol a partir de una lista no ordenada
     * @param lista
     * @return
     */
    //O(nlogn) porque el método add es de O(logn) y este proceso lo hacemos n veces
    public ArbolBinarioBusqueda<T> buildUnsorted(Lista<T> lista){
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();//arbol que regresaremos
        Iterator<T> iterador=lista.iterator();//iterador de la lista
        //iteramos la lista y vamos añadiendo cada elemento en el arbol
        for(int i=0;i<lista.size();i++){
            arbol.add(iterador.next());
        }
        return arbol;//regresamos el arbol creado
    }

    /**
     * Metodo para contruir una arbol a partir de una lista ordenada
     * @param lista
     * @return
     */
    public ArbolBinarioBusqueda<T> buildSorted(Lista<T> lista){
        //Hacemos lo mismo con el build unsorted. Será de O(n) porque todos los elementos se irán agregando de un solo lado del arbol, haciendo que sea como ir añadiendo los elementos al final de una lista
        return buildUnsorted(lista);
    }

    /**
     * Metodo para buscar un elemento en el arbol
     * @param raiz el nodo raiz del arbol
     * @param elemento el elemento a buscar
     * @return boolean
     */
    public boolean search(Vertice raiz, T elemento){
        //Si la el elemento de la raíz es igual al elemento buscado, hemos encontrado al elemento
        if(raiz.elemento.equals(elemento)){
            return true;
        }else{//Si el elemento de la raíz no es igual al elemento buscado
            if(raiz.elemento.compareTo(elemento)<0){   //y el buscado es menor que elelemento de la raíz
                if(raiz.hayIzquierdo()){   //si la raíz tiene hijo izquierdo
                    //entonces buscamos en el subarbol izquierdo
                    return search(raiz.izquierdo, elemento);
                }else{   //si no tiene hijo izquierdo
                    return false;//entonces el elemento buscado no está en el arbol
                }
            }else{//Si el buscado es mayor que la raíz
                if(raiz.hayDerecho()){  //y la raíz tiene hijo derecho
                    //entonces buscamos en el subarbol derecho
                    return search(raiz.derecho, elemento);
                }else{ //si no hay hijo derecho
                    //entonces el elemento no está en el árbol
                    return false;
                }
            }
        }
    }

    /**
     * Metodo para buscar un elemento
     * @param elemento el elemento a buscar
     * @return
     */
    public boolean buscaElemento(T elemento){
        return search(this.raiz,elemento);
    }

    /**
     * Metodo para obtener un iterador del arbol BST
     * @return Iterator<T>
     */
    @Override public Iterator<T> iterator(){
        return new Iterador();
    }
}

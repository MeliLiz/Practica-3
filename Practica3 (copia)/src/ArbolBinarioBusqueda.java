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
            Vertice aux=raiz;
            while(raiz!=null){
                pila.push(raiz);
                if(raiz.hayIzquierdo()){
                    raiz=raiz.izquierdo;
                }else{
                    raiz=null;
                }    
            }
            raiz=aux;
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
            elementos++;
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
            
            if(nuevo.elemento.compareTo(actual.elemento)<0){//Si el elemento del nodo nuevo es menor que el elemento del vertice actual
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
     * @throws IllegalArgumentException
     * @return boolean
     */
    public boolean search(Vertice raiz, T elemento){
        if(elemento==null){
            throw new IllegalArgumentException();
        }
        if(this.isEmpty()){
            return false;
        }
        //Si la el elemento de la raíz es igual al elemento buscado, hemos encontrado al elemento
        if(raiz.elemento.equals(elemento)){
            //System.out.println(raiz.altura());
            return true;
        }else{//Si el elemento de la raíz no es igual al elemento buscado
            if(elemento.compareTo(raiz.elemento)<0){   //y el buscado es menor que elelemento de la raíz
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
     * Metodo para buscar un elemento en el arbol actual
     * @param elemento el elemento a buscar
     * @return
     */
    public boolean buscaElemento(T elemento){
        return search(this.raiz,elemento);
    }

    /**
     * Metodo que se aplica a arboles vacios. Hace que el arbol actual sea el arbol ordenado balanceado del arbol parametro
     * @param arbol 
     */
    public void convertBST(ArbolBinario<T> arbol){
        if(!isEmpty()){
            throw new IllegalArgumentException("Este metodo requiere que el arbol actual sea vacio");
        }
        //Iterador del arbol que nos dan
        Iterator<T> iterador=arbol.iterator();
        //Añadimos todos los elementos del arbol binario que nos dan
        while(iterador.hasNext()){
            add2(iterador.next());
        }
    }

    
    /**
     * Metodo auxiliar de convertBST para hacer la verificacion antes de añadir el elemento
     * @param elemento
     */
    private void add2(T elemento){
        //ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        if(elemento==null){
            throw new IllegalArgumentException("No se puede agregar null al arbol");
        }else if(isEmpty()){//si el arbol esta vacio, el nuevo vertice sera la raiz
            raiz=new Vertice(elemento);
            elementos++;
        }else{//si el arbol no es la raiz
            //System.out.println("add 2");
            raiz=addBalanceado(raiz, elemento);//la nueva raiz sera el vertice regresado por addBalanceado
        }
    }

    /**
     * Metodo auxiliar para añadir un elemento en el arbol de manera ordenada y balanceada
     * @param padre 
     * @param elemento
     * @return
     */
    private Vertice addBalanceado(Vertice padre, T elemento){
        if(!padre.elemento.equals(elemento)){//Verificamos que el elemento a añadir no este repetido
            if(elemento.compareTo(padre.elemento)<0){//Si el elemento a añadir es menor al elemento del vertice que estamos comparando, verificamos a la izquierda del arbol
                if(padre.hayIzquierdo()){//si hay nodo izquierdo, hacemos recursion
                    padre.izquierdo=addBalanceado(padre.izquierdo, elemento);
                }else{//si no hay izquierdo añadimos el vertice
                    Vertice nuevo=new Vertice(elemento);
                    padre.izquierdo=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                    
                }
                //verificamos si debemos hacer rotaciones para conservar el balance del arbol
                int alturaDerecha=0;
                int alturaIzquierda=0;
                //tomamos las alturas de los subarboles izquierdo y derecho y las comparamos para saber si su resta se es mayor a uno(lo cual no debe suceder para que el arbol este balanceado)
                if(padre.hayIzquierdo()){
                    alturaIzquierda=padre.izquierdo.altura();
                }
                if(padre.hayDerecho()){
                    alturaDerecha=padre.derecho.altura();
                }
                
                //System.out.println("Altura elemento: "+elemento+ " altura izq : "+alturaIzquierda+ " altura der: "+ alturaDerecha);
                if(alturaIzquierda-alturaDerecha>1){
                    if(elemento.compareTo(padre.izquierdo.elemento)<0){//si el elemento es menor que el izquierdo del padre
                        padre=rotarDerecha(padre);//rotamos una vez a la derecha
                    }else{//si no
                        padre=rotarDerecha2(padre);//rotamos primero a la izquierda y luego a la derecha
                    }
                }
            }else{//Si el elemento a añadir es mayor al elemento del vertice que estamos comparando, verificamos a la derecha del arbol
                if(padre.hayDerecho()){//y el vertice que estamos comparando tiene derecho
                    padre.derecho=addBalanceado(padre.derecho, elemento);//hecemos recursion
                }else{//si no tiene derecho, añadimos el vertice
                    Vertice nuevo=new Vertice(elemento);
                    padre.derecho=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                }
                //verificamos si debemos hacer rotaciones para conservar el balance del arbol
                int alturaDerecha=0;
                int alturaIzquierda=0;
                //tomamos las alturas de los subarboles izquierdo y derecho y las comparamos para saber si su resta se es mayor a uno(lo cual no debe suceder para que el arbol este balanceado)
                if(padre.hayIzquierdo()){
                    alturaIzquierda=padre.izquierdo.altura();
                }
                if(padre.hayDerecho()){
                    alturaDerecha=padre.derecho.altura();
                }
                    
                //System.out.println("Altura elemento: "+elemento+ " altura izq : "+alturaIzquierda+ " altura der: "+ alturaDerecha);
                if(alturaDerecha-alturaIzquierda>1){
                    //System.out.println("Elemento: "+ elemento+" Padre: "+padre);
                    if(elemento.compareTo(padre.derecho.elemento)<0){//si el elemento es menor que el derecho del padre
                        padre=rotarIzquierda2(padre);//rotamos una vez a la izquierda
                    }else{//si no
                        padre=rotarIzquierda(padre);//rotamos primero a la derecha y luego a la izquierda
                    }     
                }
                
                
            }
        }
        return padre;
    }

    /**
     * Metodo para rotar un vertice a la derecha
     * @param raiz el vertice a rotar
     * @return Vertice
     */
    private Vertice rotarDerecha(Vertice raiz){
        if(!raiz.hayIzquierdo()){//si el vertice a rotsar no tiene hijo izquierdo
            throw new IllegalArgumentException("No se puede rotar a la derecha");//no podemos hacer la rotación
        }
        Vertice izquierdo=raiz.izquierdo;
        raiz.izquierdo=izquierdo.derecho;
        izquierdo.derecho=raiz;
        return izquierdo;//raíz nueva
    }

    /**
     * Metodo para rotar un vertice a la izquierda
     * @param raiz El vertice a rotar
     * @return Vertice
     */
    private Vertice rotarIzquierda(Vertice raiz){
        if(!raiz.hayDerecho()){//si el vertice a rotsar no tiene hijo derecho
            throw new IllegalArgumentException("No se puede rotar a la izquierda");//no podemos hacer la rotación
        }
        Vertice derecho=raiz.derecho;
        raiz.derecho=derecho.izquierdo;
        derecho.izquierdo=raiz;
        
        return derecho;//raíz nueva
    }

    /**
     * Metodo para rotar un vertice primero a la derecha y luego a la izquierda
     * @param raiz El vertice a rotar
     * @return Vertice
     */
    private Vertice rotarIzquierda2(Vertice raiz){
        raiz.izquierdo=rotarDerecha(raiz.izquierdo);
        return rotarIzquierda(raiz);
    }

    /**
     * Metodo para rotar un vertice primero a la izquierda y luego a la derecha
     * @param raiz El vertice a rotar
     * @return Vertice
     */
    private Vertice rotarDerecha2(Vertice raiz){
        raiz.derecho=rotarIzquierda(raiz.derecho);
        return rotarDerecha(raiz);
    }


    /**
     * Metodo para obtener un iterador del arbol BST
     * @return Iterator<T>
     */
    @Override public Iterator<T> iterator(){
        return new Iterador();
    }
}

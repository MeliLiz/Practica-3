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


    public void convertBST(ArbolBinario<T> arbol){
        if(!isEmpty()){
            throw new IllegalArgumentException();
        }
        //arbol BST que regresaremos
        //ArbolBinarioBusqueda<T> regreso=new ArbolBinarioBusqueda<T>();
        //Iterador del arbol que nos dan
        Iterator<T> iterador=arbol.iterator();
        //Añadimos todos los elementos del arbol binario que nos dan
        while(iterador.hasNext()){
            add2(iterador.next());
        }
    }

    

    private void add2(T elemento){
        //ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        if(elemento==null){
            throw new IllegalArgumentException();
        }else if(isEmpty()){
            raiz=new Vertice(elemento);
            elementos++;
        }else{
            //System.out.println("add 2");
            raiz=addBalanceado(raiz, elemento);
        }
    }

    private Vertice addBalanceado(Vertice padre, T elemento){
        if(!padre.elemento.equals(elemento)){
            if(elemento.compareTo(padre.elemento)<0){
                if(padre.hayIzquierdo()){
                    padre.izquierdo=addBalanceado(padre.izquierdo, elemento);
                }else{
                    Vertice nuevo=new Vertice(elemento);
                    padre.izquierdo=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                    
                }
                int alturaDerecha=0;
                int alturaIzquierda=0;
                if(this.raiz.hayIzquierdo()){
                    alturaIzquierda=this.raiz.izquierdo.altura();
                }
                if(this.raiz.hayDerecho()){
                    alturaDerecha=this.raiz.derecho.altura();
                }
                System.out.println("Altura elemento: "+elemento+ " altura izq : "+alturaIzquierda+ " altura der: "+ alturaDerecha);
                if(Math.abs(alturaIzquierda-alturaDerecha)>1){
                    if(elemento.compareTo(padre.izquierdo.elemento)<0){
                        padre=rotarDerecha(padre);
                    }else{
                        padre=rotarDerecha2(padre);
                    }
                }
                
                
                
            }else{
                if(padre.hayDerecho()){
                    padre.derecho=addBalanceado(padre.derecho, elemento);
                }else{
                    Vertice nuevo=new Vertice(elemento);
                    padre.derecho=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                    int alturaDerecha=0;
                    int alturaIzquierda=0;
                    if(raiz.hayIzquierdo()){
                        alturaIzquierda=raiz.izquierdo.altura();
                    }
                    if(raiz.hayDerecho()){
                        alturaDerecha=raiz.derecho.altura();
                    }
                    System.out.println("Altura elemento: "+elemento+ " altura izq : "+alturaIzquierda+ " altura der: "+ alturaDerecha);
                    if(Math.abs(alturaDerecha-alturaIzquierda)>1){
                        System.out.println("Elemento: "+ elemento+" Padre: "+padre);
                            if(elemento.compareTo(padre.derecho.elemento)<0){
                                padre=rotarIzquierda2(padre);
                            }else{
                                padre=rotarIzquierda(padre);
                            }     
                    }
                    
                }
                
                
            }
        }
        return padre;
    }

    private Vertice rotarDerecha(Vertice raiz){
        if(!raiz.hayIzquierdo()){//si el vertice a rotsar no tiene hijo izquierdo
            throw new IllegalArgumentException("No se puede rotar a la derecha");//no podemos hacer la rotación
        }
        Vertice izquierdo=raiz.izquierdo;
        raiz.izquierdo=izquierdo.derecho;
        izquierdo.derecho=raiz;
        return izquierdo;//raíz nueva
    }

    private Vertice rotarIzquierda(Vertice raiz){
        if(!raiz.hayDerecho()){//si el vertice a rotsar no tiene hijo derecho
            throw new IllegalArgumentException("No se puede rotar a la izquierda");//no podemos hacer la rotación
        }
        Vertice derecho=raiz.derecho;
        raiz.derecho=derecho.izquierdo;
        derecho.izquierdo=raiz;
        
        return derecho;//raíz nueva
    }

    private Vertice rotarIzquierda2(Vertice raiz){
        raiz.izquierdo=rotarDerecha(raiz.izquierdo);
        return rotarIzquierda(raiz);
    }

    private Vertice rotarDerecha2(Vertice raiz){
        raiz.derecho=rotarIzquierda(raiz.derecho);
        return rotarDerecha(raiz);
    }


    /*public void convertBST(ArbolBinario<T> arbol){
        if(!isEmpty()){//El arbol actual deberá ser vacio porque ahí agregaremos los elementos del arbol parametro
            throw new IllegalArgumentException("Este metodo se aplica solo a arboles vacios");
        }else{
            //iterador del arbol parametro
            Iterator<T> iterador=arbol.iterator();
            //añadimos cada elemento del arbol parametro
            while(iterador.hasNext()){
                addConvert(iterador.next());
            }
        }
    }

    private void addConvert(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException();
        }else if(isEmpty()){
            raiz=new Vertice(elemento);
            elementos++;
        }else{
            addBalanceado(raiz,raiz,elemento);
        }
    }

    private void addBalanceado(Vertice raizcte,Vertice raiz, T elemento){
        if(elemento==null){
            throw new IllegalArgumentException();
        }else{
            if(!elemento.equals(raiz.elemento)){
                if(elemento.compareTo(raiz.elemento)<0){
                    if(raiz.hayIzquierdo()){
                        addBalanceado(raizcte,raiz.izquierdo, elemento);;
                    }else{
                        Vertice nuevo=new Vertice(elemento);
                        raiz.izquierdo=nuevo;
                        nuevo.padre=raiz;
                        elementos++;
                        int alturaDerecha=0;
                        int alturaIzquierda=0;
                        if(raiz.hayPadre()){
                            if(!raiz.padre.hayDerecho()){
                                Vertice v=rotarDerecha(raiz);
                                if(!v.hayPadre()){
                                    this.raiz=v;
                                }
                            }else if(!raiz.padre.hayIzquierdo()){
                                Vertice v=rotarIzquierda(raiz);
                                if(!v.hayPadre()){
                                    this.raiz=v;
                                }
                            }
                        }
                        if(raizcte.hayDerecho()){
                            alturaDerecha=raizcte.derecho.altura();
                        }
                        if(raizcte.hayIzquierdo()){
                            alturaIzquierda=raizcte.izquierdo.altura();
                        }
                        if(alturaIzquierda-alturaDerecha>1){
                            raiz=rotarIzquierda(raiz);
                            this.raiz=rotarDerecha(raizcte);
                        }else if(alturaDerecha-alturaIzquierda>1){
                            this.raiz=rotarIzquierda(raizcte);
                        }
                    }
                }else{
                    if(raiz.hayDerecho()){
                        addBalanceado(raizcte,raiz.derecho, elemento);
                    }else{
                        Vertice nuevo=new Vertice(elemento);
                        raiz.derecho=nuevo;
                        nuevo.padre=raiz;
                        elementos++;
                        if(raiz.hayPadre()){
                            if(!raiz.padre.hayIzquierdo()){
                                Vertice v=rotarIzquierda(raiz);
                                if(!v.hayPadre()){
                                    this.raiz=v;
                                }
                            }else if(!raiz.padre.hayDerecho()){
                                Vertice v=rotarDerecha(raiz);
                                if(!v.hayPadre()){
                                    this.raiz=v;
                                }
                            }
                        }
                        int alturaDerecha=0;
                        int alturaIzquierda=0;
                        if(raizcte.hayDerecho()){
                            alturaDerecha=raizcte.derecho.altura();
                        }
                        if(raizcte.hayIzquierdo()){
                            alturaIzquierda=raizcte.izquierdo.altura();
                        }
                        if(alturaDerecha-alturaIzquierda>1){
                            this.raiz=rotarIzquierda(raizcte);
                        }else if(alturaIzquierda-alturaDerecha>1){
                            this.raiz=rotarDerecha(raizcte);
                        }
                    }
                    
                }
            }
        }
    }

    private Vertice rotarIzquierda(Vertice raiz){
        if(!raiz.hayDerecho()){//si el vertice a rotsar no tiene hijo derecho
            throw new IllegalArgumentException();//no podemos hacer la rotación
        }
        //Vertice derecho de la raiz
        Vertice derecho=raiz.derecho;
        raiz.derecho=derecho.izquierdo;//el derecho de la raiz será el izquierdo que tenia el derecho de la raiz
        derecho.izquierdo=raiz;//el izquierdo del derecho de la raiz sera la raiz
        
        return derecho;//raíz nueva
    }

    private Vertice rotarDerecha(Vertice raiz){
        if(!raiz.hayIzquierdo()){//si el vertice a rotsar no tiene hijo izquierdo
            throw new IllegalArgumentException();//no podemos hacer la rotación
        }
        //vertice izquierdo de la raiz
        Vertice izquierdo=raiz.izquierdo;
        raiz.izquierdo=izquierdo.derecho;//el izquierdo de la raía ahora será el serecho del izquierdo de la raiz
        izquierdo.derecho=raiz;//el derecho del izquierdo de la raiz sera la raiz
        return izquierdo;//raíz nueva
    }*/

    /*public void convertBST(ArbolBinario<T> arbol){
        if(!isEmpty()){//El arbol actual deberá ser vacio porque ahí agregaremos los elementos del arbol parametro
            throw new IllegalArgumentException("Este metodo se aplica solo a arboles vacios");
        }else{
            //iterador del arbol parametro
            Iterator<T> iterador=arbol.iterator();
            //añadimos cada elemento del arbol parametro
            while(iterador.hasNext()){
                T elem=iterador.next();
                raiz=insertar(elem, raiz);
            }
        }
    }

    private Vertice insertar(T valorNuevo, Vertice raiz){
        if(isEmpty()){
            raiz=new Vertice(valorNuevo);
            elementos++;
        }else if(valorNuevo.compareTo(raiz.elemento)<0){
            raiz.izquierdo=insertar(valorNuevo, raiz.izquierdo);
        }else if(valorNuevo.compareTo(raiz.elemento)>0){
            raiz.derecho=insertar(valorNuevo, raiz.derecho);
        }else{

        }
        if(raiz.izquierdo.altura()-raiz.derecho.altura()==2){
            if(valorNuevo.compareTo(raiz.izquierdo.elemento)<0){
                raiz=rotarIzquierda(raiz);
            }else{
                raiz=rotarIzquierda2(raiz);
            }
        }
        if(raiz.derecho.altura()-raiz.izquierdo.altura()==2){
            if(valorNuevo.compareTo(raiz.derecho.elemento)>0){
                raiz=rotarDerecha(raiz);
            }else{
                raiz=rotarDerecha2(raiz);
            }
        }
        return raiz;
    }
    
    private Vertice rotarIzquierda2(Vertice raiz){
        raiz.izquierdo=rotarDerecha(raiz.izquierdo);
        return rotarIzquierda(raiz);
    }

    private Vertice rotarDerecha2(Vertice raiz){
        raiz.derecho=rotarIzquierda(raiz.derecho);
        return rotarDerecha(raiz);
    }*/


    /**
     * Metodo para obtener un iterador del arbol BST
     * @return Iterator<T>
     */
    @Override public Iterator<T> iterator(){
        return new Iterador();
    }
}

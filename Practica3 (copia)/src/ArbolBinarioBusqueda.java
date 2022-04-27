package src.edd;
import java.lang.IllegalCallerException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import src.edd.ArbolBinario;
import src.edd.Lista;
import src.edd.Pila;


public class ArbolBinarioBusqueda<T extends Comparable> extends ArbolBinario<T> {
    //#####################################################################################################################################################
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
        @Override
        public boolean hasNext(){
            return !pila.isEmpty();
        }
        
        /**
         * Metodo que devuelve el siguiente elemento del arbol
         * Recorrido inorrder
         * @throws NoSuchElementException
         */
        @Override
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
    
    //##########################################################################################################################################################
    /**
     * Metodo constructor
     */
    public ArbolBinarioBusqueda(){
        super();
    }

    /**
     * Metodo que construye un arbol a partir de una lista
     * @param lista la lista con elementos
     * @param ordenada si la lista esta ordenada o no
     * @param menorAmayor en caso de estar ordenada, si esta ordenada de menor a mayor
     */
    public ArbolBinarioBusqueda(Lista<T> lista, boolean ordenada, boolean menorAmayor){
        super();
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        if(ordenada){
            if(!menorAmayor){
                lista.reverse();
            }
            arbol.buildSorted(lista);
        }else{
            arbol.buildUnsorted(lista);
        }
        this.raiz=arbol.raiz;
    }

    //##############################################################################################################################################################
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

    //################################################################################################################################################################

    /**
     * Metodo para contruir una arbol a partir de una lista no ordenada
     * @param lista
     * @return
     */
    //O(nlogn) porque el método add es de O(logn) y este proceso lo hacemos n veces
    public void buildUnsorted(Lista<T> lista){
        if(!this.isEmpty()){
            throw new IllegalCallerException("Este metodo solo se puede aplicar sobre arboles vacios");
        }
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();//arbol que regresaremos
        Iterator<T> iterador=lista.iterator();//iterador de la lista
        //iteramos la lista y vamos añadiendo cada elemento en el arbol
        for(int i=0;i<lista.size();i++){
            arbol.add(iterador.next());
        }
        this.raiz=arbol.raiz;
        //return arbol;//regresamos el arbol creado
    }

    //##################################################################################################################################################################################

    /**
     * Metodo para construir un arbol a partir de una lista ordenada
     * @param lista
     */
    //Tiempo O(n) porque primero verifica si hay repetidos, lo cual es tiempo n porque elimina los elementos repetidos de la lista en tiempo constante (ya que elimina el primero de la lista solamente)
    //y luego construimos el arbol binario de abajo hacia arriba.
    public void buildSorted(Lista<T> lista){
        //verificamos que el arbol sobre el que trabajaremos esté vacío y que la lista no sea vacia
        if(!this.isEmpty()){
            throw new IllegalCallerException("Este metodo solo se puede aplicar sobre arboles vacios");
        }
        //ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        if(!lista.isEmpty()){
            lista.quitaRepetidos();
        }
        if(!lista.isEmpty()){
            Iterator<T> it=lista.iterator();//Hacemos iterador de la lista
            raiz=build(lista, it,1, lista.size());//asignamos a la raiz como el vertice que nos regresa el metodo recursivo
            this.elementos=lista.size();
        }
        //return arbol;
    }

    /**
     * Metodo auxiliar recursivo de builsorted que construye el arbol de abajo hacia arriba
     * @param lista lista con elementos
     * @param iterador iterador de la lista
     * @param empieza posicion de inicio para iterar
     * @param termina posicion en la que se termina de iterar
     * @return Vertice
     */
    private Vertice build(Lista<T> lista, Iterator<T> iterador, int empieza, int termina){
        //int mitad=0;
        if(termina-empieza>2){
            Vertice v,v1, v2;//hacemos el vertice padre, el izquierdo y derecho
            int mitad=(termina-empieza)/2;//sacamos la mitad de lo que iteraremos
            v1=build(lista, iterador, empieza, empieza+mitad);//MEtodo recursivo para obtener el subarbol izquierdo
            v=new Vertice(iterador.next());//asignamos al padre
            v2=build(lista, iterador, empieza+mitad+2, termina);//metodo recursivo para obtener el subarbol derecho
            v.izquierdo=v1;//asignamos el subarbol izquierdo a v
            v1.padre=v;
            v.derecho=v2;//asignamos el subarbol derecho a v
            v2.padre=v;
            
            return v;//regresamos al padre v
        }else if(termina-empieza==2){//si la particion es de 3 elementos
            Vertice actual=new Vertice(iterador.next());//el vertice que sigue en la lista es el vertice izquierdo
            actual.padre=new Vertice(iterador.next());//el siguiente es el padre
            actual.padre.izquierdo=actual;
            actual=actual.padre;
            actual.derecho=new Vertice(iterador.next());//y el derecho es el suguiente
            actual.derecho.padre=actual;
            return actual;//regresamos al padre
        }else if(termina-empieza==1){//si la particion es de 2 elementos
            Vertice actual=new Vertice(iterador.next());//el siguiente en la lista es el vertice izquierdo
            actual.padre=new Vertice(iterador.next());//y el siguiente es el padre
            actual.padre.izquierdo=actual;
            actual=actual.padre;
            return actual;//regresamos al padre
        }else if(termina-empieza==0){//si la particion es de 1 elemento
            Vertice actual=new Vertice(iterador.next());
            return actual;//regresamos al siguiente de la lista
        }else{
            return null;
        }
    }

    //#################################################################################################################################################################################################

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
     * Metodo que regresa el vertice en el que se encuentra el elemento. En caso de que el elemento no esté, regresa null.
     * @param raiz
     * @param elemento
     * @return
     */
    public Vertice search2(Vertice raiz, T elemento){
        if(elemento==null){
            throw new IllegalArgumentException();
        }
        if(this.isEmpty()){
            return null;
        }
        //Si la el elemento de la raíz es igual al elemento buscado, hemos encontrado al elemento
        if(raiz.elemento.equals(elemento)){
            //System.out.println(raiz.altura());
            return raiz;
        }else{//Si el elemento de la raíz no es igual al elemento buscado
            if(elemento.compareTo(raiz.elemento)<0){   //y el buscado es menor que elelemento de la raíz
                if(raiz.hayIzquierdo()){   //si la raíz tiene hijo izquierdo
                    //entonces buscamos en el subarbol izquierdo
                    return search2(raiz.izquierdo, elemento);
                }else{   //si no tiene hijo izquierdo
                    return null;//entonces el elemento buscado no está en el arbol
                }
            }else{//Si el buscado es mayor que la raíz
                if(raiz.hayDerecho()){  //y la raíz tiene hijo derecho
                    //entonces buscamos en el subarbol derecho
                    return search2(raiz.derecho, elemento);
                }else{ //si no hay hijo derecho
                    //entonces el elemento no está en el árbol
                    return null;
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

    //##################################################################################################################################################################################

    /**
     * Metodo que se aplica a arboles vacios. Hace que el arbol actual sea el arbol ordenado balanceado del arbol parametro
     * @param arbol 
     */
    public void convertBST(ArbolBinario<T> arbol){
        if(!isEmpty()){
            throw new IllegalCallerException("Este metodo requiere que el arbol actual sea vacio");
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
    //################################################################################################################################################################################
    /**
     * Metodo para balancear un arbol binario de busqueda
     * @param raiz
     */
    public void balance(Vertice vertice){
        if(vertice==null){
            throw new IllegalArgumentException("No se puede balancear un arbol vacio");
        }
        //Si el vertice tiene vertice padre, lo guardamos en p y verificamos si el vertice es el hijo derecho o el izquierdo
        Vertice p=null;
        boolean der, izq;
        der=izq=false;
        if(vertice.hayPadre()){
            p=vertice.padre;
            if(p.hayDerecho()){
                if(p.derecho.elemento.equals(vertice.elemento)){
                    der=true;
                }
            }
            if(p.hayIzquierdo()){
                if(p.izquierdo.elemento.equals(vertice.elemento)){
                    izq=true;
                }
            }   
        }
        //Hacemos el arbol para poder tener un iterador
        ArbolBinarioBusqueda<T> arbol=new ArbolBinarioBusqueda<T>();
        arbol.raiz=vertice;
        //Hacemos el iterador del arbol
        Iterator<T> iterador=arbol.iterator();
        ArrayList<T> arreglo=new ArrayList<T>();//Pasamos todos los elementos del arbol a un arreglo
        while(iterador.hasNext()){
            arreglo.add(iterador.next());
        }
        
        vertice=balancea(arreglo,0,arreglo.size()-1);//la raiz del arbol balanceado será el vertice regresado por el metodo recursivo
        vertice.padre=p;
        if(p!=null){
            if(der){
                p.derecho=vertice;
            }
            if(izq){
                p.izquierdo=vertice;
            }
        }else{
            this.raiz=vertice;
        }
    }

    /**
     * Metodo auxiliar de balance
     * @param arreglo el arreglo con los elementos 
     * @param inicio la posicion de inicio
     * @param termina la posicion final
     * @return Vertice
     */
    private Vertice balancea(ArrayList<T> arreglo, int inicio, int termina){
        if(termina-inicio>2){//si la particion es de mas de 3 elementos
            int mitad=(termina-inicio)/2;//calculamos la mitad de la particion
            Vertice v=new Vertice(arreglo.get(inicio+mitad));//El vertice que sera el padre
            Vertice v1,v2;
            v1=balancea(arreglo,inicio,inicio+mitad-1);//hacemos la particion izquierda, v1 sera el subarbol izquierdo
            //System.out.println(v1.elemento);
            v2=balancea(arreglo, inicio+mitad+1,termina);//hacemos la particion derecha. v2 sera el subarbol derecho
            //System.out.println(v2.elemento);
            //Actualizamos referencias
            v.izquierdo=v1;
            v1.padre=v;
            v.derecho=v2;
            v2.padre=v;
            return v;//regresamos al vertice padre
        }else if(termina-inicio==2){//Si la particion es de 3 elementos
            Vertice v1=new Vertice(arreglo.get(inicio));//vertice de la izquierda
            Vertice v=new Vertice(arreglo.get(inicio+1));//vertice padre
            Vertice v2=new Vertice(arreglo.get(termina));//vertice de la derecha
            //actualixamos referencias
            v.izquierdo=v1;
            v.derecho=v2;
            v1.padre=v2.padre=v;
            return v;//regresamos al padre
        }else if(termina-inicio==1){//Si la particion es de dos elementos
            Vertice v1=new Vertice(arreglo.get(inicio));//vertice de la izquierda
            Vertice v=new Vertice(arreglo.get(termina));//vertice padre
            //actualizamos referencias
            v.izquierdo=v1;
            v1.padre=v;
            return v;//regresamos al padre
        }else if(termina-inicio==0){//Si la particion es de un elemento
            Vertice v=new Vertice(arreglo.get(inicio));
            return v;//regresamos al vertice con el elemento
        }else{
            return null;
        }
    }



    //#####################################################################################################################################################################3

    /**
     * Metodo para obtener un iterador del arbol BST
     * @return Iterator<T>
     */
    @Override public Iterator<T> iterator(){
        return new Iterador();
    }
}

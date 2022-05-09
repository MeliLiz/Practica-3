package src.edd;

import src.edd.ArbolBinarioBusqueda;

public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T>{
    /**
     * Clase interna protegida para vértices de árboles AVL. La única
     * diferencia con los vértices tienen altura
    */
    protected class VerticeAVL extends Vertice {
        public int altura;
    
        public VerticeAVL(T elemento){
            super(elemento);
            altura=0;
        }

        public int getAltura(){
            return altura;
        }
        
        public String toString(){
            return this.elemento.toString();
        }
        
        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * 
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeAVL vertice = (VerticeAVL) o;
            return altura == vertice.altura && super.equals(o);
        }
    }

    public ArbolAVL(){
        super();
    }

    public ArbolAVL(Collection<T> coleccion){
        super(coleccion);
    }

    @Override public void add(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException("No se puede agregar un elemento vacío");
        }
        if(isEmpty()){
            this.raiz=new VerticeAVL(elemento);
            elementos++;
        }else{
            this.raiz=addAux(convertirVertice(this.raiz), elemento);
        }
    }

    private Vertice addAux(Vertice padre, T elemento){
        if(!padre.elemento.equals(elemento)){
            if(padre.elemento.compareTo(elemento)>0){
                if(padre.hayIzquierdo()){//si hay nodo izquierdo, hacemos recursion
                    padre.izquierdo=addAux(padre.izquierdo, elemento);
                }else{//si no hay izquierdo añadimos el vertice
                    VerticeAVL nuevo=new VerticeAVL(elemento);
                    padre.izquierdo=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                    
                }
                //verificamos si debemos hacer rotaciones para conservar el balance del arbol
                int alturaDerecha=-1;
                int alturaIzquierda=-1;
                //tomamos las alturas de los subarboles izquierdo y derecho y las comparamos para saber si su resta se es mayor a uno(lo cual no debe suceder para que el arbol este balanceado)
                if(padre.hayIzquierdo()){
                    alturaIzquierda=convertirVertice(padre.izquierdo).getAltura();
                }
                if(padre.hayDerecho()){
                    alturaDerecha=convertirVertice(padre.derecho).getAltura();
                }
                if(alturaIzquierda-alturaDerecha>1){
                    //ver si es hijo izquierdo o derecho
                    System.out.println("Entra al if");
                    System.out.println("elem: "+padre.elemento);
                    System.out.println("Altura derecha: "+alturaDerecha);
                    System.out.println("Altura izquierda: "+alturaIzquierda);
                    if(elemento.compareTo(padre.izquierdo.elemento)<0){//si el elemento es menor que el izquierdo del padre
                        padre=rotarDerecha(padre);//rotamos una vez a la derecha
                    }else{//si no
                        padre=rotarDerecha2(padre);//rotamos primero a la izquierda y luego a la derecha
                    }
                }

            }else{
                if(padre.hayDerecho()){//y el vertice que estamos comparando tiene derecho
                    padre.derecho=addAux(padre.derecho, elemento);//hecemos recursion
                }else{//si no tiene derecho, añadimos el vertice
                    VerticeAVL nuevo=new VerticeAVL(elemento);
                    padre.derecho=nuevo;
                    nuevo.padre=padre;
                    elementos++;
                }
                //verificamos si debemos hacer rotaciones para conservar el balance del arbol
                int alturaDerecha=-1;
                int alturaIzquierda=-1;
                //tomamos las alturas de los subarboles izquierdo y derecho y las comparamos para saber si su resta se es mayor a uno(lo cual no debe suceder para que el arbol este balanceado)
                if(padre.hayIzquierdo()){
                    alturaIzquierda=convertirVertice(padre.izquierdo).getAltura();
                }
                if(padre.hayDerecho()){
                    alturaDerecha=convertirVertice(padre.derecho).getAltura();
                }
                    
                //System.out.println("Altura elemento: "+elemento+ " altura izq : "+alturaIzquierda+ " altura der: "+ alturaDerecha);
                if(alturaDerecha-alturaIzquierda>1){
                    //System.out.println("Elemento: "+ elemento+" Padre: "+padre);
                    if(elemento.compareTo(padre.derecho.elemento)<0){//si el elemento es menor que el derecho del padre
                        padre=rotarIzquierda2(padre);
                    }else{//si no
                        padre=rotarIzquierda(padre);
                    }     
                }
            }
        }  
        int hIzq=-1;
        int hDer=-1;
        if(padre.hayIzquierdo()){
            hIzq=convertirVertice(padre.izquierdo).getAltura();
        }
        if(padre.hayDerecho()){
            hDer=convertirVertice(padre.derecho).getAltura();
        }
        VerticeAVL p=convertirVertice(padre);
        p.altura=1+Math.max(hIzq, hDer);
        System.out.println("Elemento: "+p.elemento+" Altura: " + p.altura);
        System.out.println("Altura derecha "+hDer+" Altura izquierda "+ hIzq);
        return padre;
    }

    public void delete(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException("No se puede eliminar un elemento vacío");
        }else if(isEmpty()){
            throw new IllegalCallerException("No se puede eliminar un elemento de una arbol vacío");
        }else{
            Vertice v=search2(this.raiz, elemento);
            if(v==null){
                throw new IllegalCallerException("El elemento a eliminar no se encuentra en le arbol");
            }else{
                this.raiz=auxDel(this.raiz, elemento);
                elementos--;
            }
        }
    }

    protected Vertice auxDel(Vertice padre, T elemento){
        if(elemento.equals(padre.elemento)){
            // cuando aux no tiene izquierdo ni derecho significa que es una hoja
            if (!padre.hayIzquierdo() && !padre.hayDerecho()) { // si el nodo a eliminar no tiene hijo derecho ni izquierdo
                if(padre.hayPadre()){
                    if(padre.padre.hayDerecho()&&padre.padre.derecho.elemento.equals(padre.elemento)){
                        padre.padre.derecho=null;
                    }else{
                        padre.padre.izquierdo=null;
                    }
                }else{
                    this.raiz=null;
                }
            }else if(padre.hayDerecho()&&padre.hayIzquierdo()){
                Vertice aux = padre.derecho; // aux2 es el hijo derecho del nodo a eliminar
                while (aux.hayIzquierdo()) {
                    aux = aux.izquierdo; 
                }
                T elem = aux.elemento; // guardamos el elemento del hijo derecho del nodo a aliminar
                auxDel(aux, elem);
                padre.elemento = elem;
            }else{
                if(padre.hayDerecho()){
                    if (padre.hayPadre()) { // si el nodo a eliminar tiene padre
                        if (padre.padre.hayDerecho() && padre.padre.derecho.elemento.equals(padre.elemento)) { 
                            padre.padre.derecho = padre.derecho; // cambiamos referencias para eliminar
                            padre.derecho.padre = padre.padre;
                        } else { // si el nodo es el hijo izquierdo del padre
                            padre.padre.izquierdo = padre.derecho;
                            padre.derecho.padre = padre.padre; // cambiamos referencias para eliminar
                        }
                    } else { // cuando el nodo a eliminar es la raíz
                        this.raiz = padre.derecho;
                    }
                }else if(padre.hayIzquierdo()){
                    if (padre.hayPadre()) { // si el nodo a eliminar tiene padre
                        if (padre.padre.hayDerecho() && padre.padre.derecho.elemento.equals(padre.elemento)) { 
                            padre.padre.derecho = padre.izquierdo; // cambiamos referencias para eliminar
                            padre.izquierdo.padre = padre.padre;
                        } else { // si el nodo es el hijo izquierdo del padre
                            padre.padre.izquierdo = padre.izquierdo;
                            padre.izquierdo.padre = padre.padre; // cambiamos referencias para eliminar
                        }
                    } else { // cuando el nodo a eliminar es la raíz
                        this.raiz = padre.izquierdo;
                    }
                }
            }

        }else if(elemento.compareTo(padre.elemento)<0){
            if(padre.hayIzquierdo()){
                auxDel(padre.izquierdo, elemento);
            }
            int hIzq=-1;
            int hDer=-1;
            if(padre.hayIzquierdo()){
                hIzq=convertirVertice(padre.izquierdo).getAltura();
            }
            if(padre.hayDerecho()){
                hDer=convertirVertice(padre.derecho).getAltura();
            }
            if(hDer-hIzq>1){
                int hIzq2=-1;
                int hDer2=-1;
                if(padre.hayDerecho()){
                    VerticeAVL actual=convertirVertice(padre.derecho);
                    if(actual.hayDerecho()){
                        hDer2=convertirVertice(actual.derecho).getAltura();
                    }
                    if(actual.hayIzquierdo()){
                        hIzq2=convertirVertice(actual.izquierdo).getAltura();
                    }
                    if(hIzq2>hDer2){
                        padre=rotarIzquierda(padre);
                    }else{
                        padre=rotarIzquierda2(padre);
                    }
                }
            }
        }else{
            if(padre.hayDerecho()){
                auxDel(padre.derecho, elemento);
            }
            int hIzq=-1;
            int hDer=-1;
            if(padre.hayIzquierdo()){
                hIzq=convertirVertice(padre.izquierdo).getAltura();
            }
            if(padre.hayDerecho()){
                hDer=convertirVertice(padre.derecho).getAltura();
            }
            if(hIzq-hDer>1){
                int hIzq2=-1;
                int hDer2=-1;
                if(padre.hayIzquierdo()){
                    VerticeAVL actual=convertirVertice(padre.izquierdo);
                    if(actual.hayDerecho()){
                        hDer2=convertirVertice(actual.derecho).getAltura();
                    }
                    if(actual.hayIzquierdo()){
                        hIzq2=convertirVertice(actual.izquierdo).getAltura();
                    }
                    if(hDer2>hIzq2){
                        padre=rotarDerecha(padre);
                    }else{
                        padre=rotarDerecha2(padre);
                    }
                }
            }
        
        }
        int alturaIzq=-1;
        int alturaDer=-1;
        if(padre.hayIzquierdo()){
            alturaIzq=convertirVertice(padre.izquierdo).getAltura();
        }
        if(padre.hayDerecho()){
            alturaDer=convertirVertice(padre.derecho).getAltura();
        }
        VerticeAVL p=convertirVertice(padre);
        p.altura=1+Math.max(alturaIzq, alturaDer);
        System.out.println("Elemento: "+p.elemento+" Altura: " + p.altura);
        return padre;
    }

    public VerticeAVL convertirVertice(Vertice v){
        VerticeAVL vertice=(VerticeAVL) v;
        return vertice;
    }

    
}

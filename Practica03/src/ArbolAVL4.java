package src.edd;

import src.edd.ArbolBinarioBusqueda;

public class ArbolAVL4<T extends Comparable<T>> extends ArbolBinarioBusqueda<T>{
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

        public int getaltu(){
            return altura;
        }
        
        public String toString(){
            return this.elemento.toString();
        }

        public VerticeAVL getIzquierdo(VerticeAVL v){
            if(v==null){
                return null;
            }else{
                return convertirVertice(v.izquierdo);
            }
        }

        public VerticeAVL getDerecho(VerticeAVL v){
            if(v==null){
                return null;
            }else{
                return convertirVertice(v.derecho);
            }
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

    public ArbolAVL4(){
        super();
    }

    public ArbolAVL4(Collection<T> coleccion){
        super(coleccion);
    }

    private VerticeAVL balancear(VerticeAVL v){
        if(v!=null){
            VerticeAVL a=null;
            if(altura(convertirVertice(v.derecho))==(altura(convertirVertice(v.izquierdo))+2)){
                if(v.hayDerecho()){
                    if(altura(convertirVertice(v.derecho.derecho))==(altura(convertirVertice(v.izquierdo))+1)){
                        a=rotar(v,true);
                    }else if(altura(convertirVertice(v.derecho.derecho))==(altura(convertirVertice(v.izquierdo)))){
                        rotar(convertirVertice(v.derecho),false);
                        a=rotar(convertirVertice(v),true);
                        //a=rotar2(v, false);
                    }
                }
            }else if(altura(convertirVertice(v.izquierdo))==(altura(convertirVertice(v.derecho))+2)){
                if(v.hayIzquierdo()){
                    if(altura(convertirVertice(v.izquierdo.izquierdo))==(altura(convertirVertice(v.derecho))+1)){
                        a=rotar(v,false);
                    }else if(altura(convertirVertice(v.izquierdo.izquierdo))==altura(convertirVertice(v.derecho))){
                        rotar(convertirVertice(v.izquierdo),true);
                        a=rotar(v,false);
                        //a=rotar2(v, true);
                    }
                }
            }else{
                a=v;
            }
            return a;
        }
        return null;
    }

    private void actualizarAltura(VerticeAVL v){
        if(v!=null){
            v.altura=1+Math.max(altura(convertirVertice(v.derecho)), altura(convertirVertice(v.izquierdo)));
        }
    }

    private int altura(VerticeAVL v){
        if(v==null){
            return -1;
        }else{
            return v.altura;
        }
    }

    public VerticeAVL rotar(VerticeAVL vertice,boolean izquierda){
        VerticeAVL aux;
        if(vertice!=null){
            if(izquierda){
                VerticeAVL padre=null;
                if(vertice.hayPadre()){
                    padre=convertirVertice(vertice.padre);
                }
                aux=derecho(vertice);
                vertice.derecho=izquierdo(aux);
                if(vertice.derecho!=null){
                    vertice.derecho.padre=vertice;
                }
                if(aux!=null){//.hayIzquierdo()
                    aux.izquierdo=vertice;
                    vertice.padre=aux;
                }
                if(aux!=null){
                    aux.padre=padre;
                }
                if(padre!=null){
                    if(padre.hayDerecho()&&padre.derecho.elemento.equals(vertice.elemento)){
                        padre.derecho=aux;
                    }else{
                        padre.izquierdo=aux;
                    }
                }
                
            }else{
                
                VerticeAVL padre=null;
                if(vertice.hayPadre()){
                    padre=convertirVertice(vertice.padre);
                }
                aux=izquierdo(vertice);
                vertice.izquierdo=derecho(aux);
                if(vertice.izquierdo!=null){
                    vertice.izquierdo.padre=vertice;
                }
                if(aux!=null){
                    aux.derecho=vertice;
                    vertice.padre=aux;
                }
                if(aux!=null){
                    aux.padre=padre;
                }
                if(padre!=null){
                    if(padre.hayDerecho()&&padre.derecho.elemento.equals(vertice.elemento)){
                        padre.derecho=aux;
                    }else{
                        padre.izquierdo=aux;
                    }
                }
                
            }
            actualizarAltura(vertice);
            actualizarAltura(aux);
            return aux;
        }
        return null;
       
    }

    protected Vertice rotarDerecha(Vertice raiz){
        if(!raiz.hayIzquierdo()){//si el vertice a rotsar no tiene hijo izquierdo
            throw new IllegalArgumentException("No se puede rotar a la derecha");//no podemos hacer la rotación
        }
        Vertice izquierdo=raiz.izquierdo;
        raiz.izquierdo=izquierdo.derecho;
        if(raiz.izquierdo!=null){
            raiz.izquierdo.padre=raiz;
        }
        
        izquierdo.derecho=raiz;
        raiz.padre=izquierdo;
        return izquierdo;//raíz nueva
    }

    protected Vertice rotarIzquierda(Vertice raiz){
        if(!raiz.hayDerecho()){//si el vertice a rotsar no tiene hijo derecho
            throw new IllegalArgumentException("No se puede rotar a la izquierda");//no podemos hacer la rotación
        }
        Vertice derecho=raiz.derecho;
        raiz.derecho=derecho.izquierdo;
        if(raiz.derecho!=null){
            raiz.derecho.padre=raiz;
        }
        
        derecho.izquierdo=raiz;
        raiz.padre=derecho;
        
        return derecho;//raíz nueva
    }


    public VerticeAVL rotar2(VerticeAVL vertice, boolean izquierda){
        if(izquierda){
            rotar(convertirVertice(vertice.izquierdo),false);
            return rotar(vertice,false);
        }else{
            rotar(convertirVertice(vertice.derecho),true);
            return rotar(vertice,false);
        }
    }

    public VerticeAVL derecho(VerticeAVL v){
        if(v!=null){
            return convertirVertice(v.derecho);
        }else{
            return null;
        }
    }

    public VerticeAVL izquierdo(VerticeAVL v){
        if(v!=null){
            return convertirVertice(v.izquierdo);
        }else{
            return null;
        }
    }

    public VerticeAVL convertirVertice(Vertice v){
        if(v==null){
            return null;
        }
        VerticeAVL vertice=(VerticeAVL) v;
        return vertice;
    }

    public void insertar(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException("No se puede insertar un elemento vacío");
        }
        if(isEmpty()){
            raiz=new VerticeAVL(elemento);
            elementos++;
        }else{
            VerticeAVL b=insertarAux(convertirVertice(raiz),elemento);
            if(b!=null){
                this.raiz=b;
            }
        }
    }

    private VerticeAVL insertarAux(VerticeAVL vertice, T elemento){
        if(!elemento.equals(vertice.elemento)){
            if(elemento.compareTo(vertice.elemento)<0){
                if(vertice.hayIzquierdo()){
                    insertarAux(convertirVertice(vertice.izquierdo), elemento);
                }else{
                    VerticeAVL nuevo=new VerticeAVL(elemento);
                    vertice.izquierdo=nuevo;
                    nuevo.padre=vertice;
                    elementos++;
                    //System.out.println(nuevo);
                    //System.out.println(nuevo.padre);
                }
                
            }else{
                if(vertice.hayDerecho()){
                    insertarAux(convertirVertice(vertice.derecho), elemento);
                }else{
                    VerticeAVL nuevo=new VerticeAVL(elemento);
                    vertice.derecho=nuevo;
                    nuevo.padre=vertice;
                    elementos++;
                }
                
            }
            VerticeAVL padre=balancear(vertice);
            System.out.println("Vertice: "+vertice+" Padre: "+padre);
            //System.out.println(vertice.izquierdo);
            actualizarAltura(vertice);
            return padre;
        }
        return null;
    }

    public void delete(T elemento){
        if(elemento==null){
            throw new IllegalArgumentException("No se puede eliminar un elemento vacio");
        }else{
            raiz=eliminar(convertirVertice(raiz),elemento);
            elementos--;
            //System.out.println(raiz);
        }
    }

    private VerticeAVL eliminar(VerticeAVL vertice, T elemento){
        if(vertice==null){//si el vertice es vacío no podemos aplicar el metodo
            throw new IllegalArgumentException("El vertice es vacio");
        }
        //Si el elemento no está en el árbol no podemos eliminarlo
        Vertice a=search2(raiz, elemento);
        if(a==null){
            throw new IllegalArgumentException("El elemento a eliminar no se encuentra en el arbol");
        }
        //VerticeAVL aux;
        if(elemento.compareTo(vertice.elemento)<0){//si el elemento buscado es menor que el del vertice nos vamos a la izquierda
            eliminar(convertirVertice(vertice.izquierdo),elemento);
        }else if(elemento.compareTo(vertice.elemento)>0){//si es mayor nos vamos a la derecha
            eliminar(convertirVertice(vertice.derecho), elemento);
        }else{
            if(!vertice.hayDerecho()&&!vertice.hayIzquierdo()){
                if(vertice.hayPadre()){
                    System.out.println("Vertice: "+vertice);
                    System.out.println("Padre: "+vertice.padre);
                    if(vertice.padre.hayIzquierdo()&&vertice.padre.izquierdo.elemento.equals(vertice.elemento)){
                        vertice.padre.izquierdo=null;
                    }else{
                        vertice.padre.derecho=null;
                    }
                }else{
                    raiz=null;
                }
                //System.out.println(vertice);
                //System.out.println(vertice.padre);
                //System.out.println(vertice.padre.derecho);
                vertice=null;
                
            }else if(!vertice.hayIzquierdo()){
                if(vertice.hayPadre()){
                    if(vertice.padre.hayIzquierdo()&&vertice.padre.izquierdo.elemento.equals(vertice.elemento)){
                        vertice.padre.izquierdo=vertice.derecho;
                    }else{
                        vertice.padre.derecho=vertice.derecho;
                    }
                    vertice.derecho.padre=vertice.padre;
                }else{
                    raiz=vertice.derecho;
                }
                //aux=vertice;
                //vertice=convertirVertice(vertice.derecho);
            }else if(!vertice.hayDerecho()){
                if(vertice.hayPadre()){
                    if(vertice.padre.izquierdo.elemento.equals(vertice.elemento)){
                        vertice.padre.izquierdo=vertice.izquierdo;
                    }else{
                        vertice.padre.derecho=vertice.izquierdo;
                    }
                    vertice.izquierdo.padre=vertice.padre;
                }else{
                    raiz=vertice.izquierdo;
                }
                //aux=vertice;
                //vertice=convertirVertice(vertice.izquierdo);
            }else{
                T b=encontrarMin(convertirVertice(vertice.derecho));
                eliminar(convertirVertice(raiz), b);
                vertice.elemento=b;
            }
        }
        VerticeAVL padre=balancear(vertice);
        System.out.println(padre);
        actualizarAltura(vertice);
        return padre;
    }

    /*private T eliminarMinimo(VerticeAVL vertice){
        if(vertice!=null){
            if(vertice.izquierdo!=null){
                T b=eliminarMinimo(convertirVertice(vertice.izquierdo));
                balancear(vertice);
                actualizarAltura(vertice);
                return b;
            }else{
                T b=vertice.elemento;
                //System.out.println(b);
                raiz=eliminar(convertirVertice(raiz),b);
                //vertice=derecho(vertice);
                balancear(vertice);
                actualizarAltura(vertice);
                return b;
            }
        }else{
            throw new IllegalCallerException("No se puede usar este metodo con un vertice vacío");
        }
    }*/

    private T encontrarMin(VerticeAVL vertice){
        if(vertice!=null){
            if(vertice.izquierdo!=null){
                T b=encontrarMin(convertirVertice(vertice.izquierdo));
                return b;
            }else{
                T b=vertice.elemento;
                return b;
            }
        }else{
            throw new IllegalCallerException("No se puede usar este metodo con un vertice vacío");
        }
    }

    
}

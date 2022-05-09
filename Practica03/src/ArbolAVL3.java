package src.edd;

import src.edd.ArbolBinarioBusqueda;

public class ArbolAVL3<T extends Comparable<T>> extends ArbolBinarioBusqueda<T>{
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

    public ArbolAVL3(){
        super();
    }

    public ArbolAVL3(Collection<T> coleccion){
        super(coleccion);
    }

    private VerticeAVL balancear(VerticeAVL v){
        if(v!=null){
            VerticeAVL a=null;
            System.out.println("Verice: "+v);
            System.out.println("Derecho: "+altura(convertirVertice(v.derecho)));
            System.out.println("Izquierdo: "+altura(convertirVertice(v.izquierdo)));
            if(altura(izquierdo(v))-altura(derecho(v))==2){
                if(v.hayIzquierdo()){
                    System.out.println("altura izq izq "+altura(convertirVertice(v.izquierdo.izquierdo)));
                    System.out.println("altura izq der "+altura(convertirVertice(v.izquierdo.derecho)));
                    if(altura(convertirVertice(v.izquierdo.izquierdo))>=altura(convertirVertice(v.izquierdo.derecho))){
                        a=rotar(v,true);
                    }
                }else{
                    a=rotar2(v,true);
                } 
            }else if(altura(derecho(v))-altura(izquierdo(v))==2){
                if(v.hayDerecho()){
                    if(altura(convertirVertice(v.derecho.derecho))>=altura(convertirVertice(v.derecho.izquierdo))){
                        a=rotar(v,false);
                    }
                }else{
                    a=rotar2(v,false);
                }
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
                aux=izquierdo(vertice);
                vertice.izquierdo=derecho(aux);
                if(vertice.izquierdo!=null){
                    vertice.izquierdo.padre=vertice;
                }
                if(aux!=null){
                    aux.derecho=vertice;
                    vertice.padre=aux.derecho;
                }
                //rotarIzquierda(vertice);
            }else{
                aux=derecho(vertice);
                vertice.derecho=izquierdo(aux);
                if(vertice.derecho!=null){
                    vertice.derecho.padre=vertice;
                }
                if(aux.hayIzquierdo()){
                    aux.izquierdo=vertice;
                    vertice.padre=aux.izquierdo;
                }
                //rotarDerecha(vertice);
            }
            actualizarAltura(vertice);
            actualizarAltura(aux);
            return aux;
        }
        return null;
       
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
            System.out.println(vertice);
            System.out.println(vertice.izquierdo);
            actualizarAltura(vertice);
            return padre;
        }
        return null;
    }

    
}
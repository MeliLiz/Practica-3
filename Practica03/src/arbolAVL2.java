package src.edd;

import src.edd.ArbolAVL;
import src.edd.ArbolBinarioBusqueda;
public class arbolAVL2<T extends Comparable<T>>{
    protected class VerticeAVL<T> {
 
        public VerticeAVL<T> izquierdo;// nodo izquierdo
     
        public VerticeAVL<T> Derecho;// nodo derecho
     
        public T elemento;
     
        public int altura;// La altura del nodo actual
     
        public VerticeAVL(T elemento) {
            this.elemento=elemento;
            this.altura=0;
        }

        public int getAltura(){
            if(this==null){
                return -1;
            }
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
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeAVL vertice = (VerticeAVL) o;
            return altura == vertice.altura && elemento.equals(vertice.elemento);
        }

        /**
         * Nos dice si el vértice tiene vértice izquierdo.
         * @return <tt>true</tt> si el vértice tiene vértice izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        public boolean hayIzquierdo() {
            return izquierdo != null;
        }
     
        /*public VerticeAVL(VerticeAVL<T> izquierdo, VerticeAVL<T> Derecho, T elemento) {
            this(izquierdo,Derecho,elemento,0);
        }
     
        public VerticeAVL(VerticeAVL<T> izquierdo, VerticeAVL<T> Derecho, T elemento, int altura) {
            this.izquierdo=izquierdo;
            this.Derecho=Derecho;
            this.elemento=elemento;
            this altura = altura;
        }*/
    }

    VerticeAVL raiz;
    public arbolAVL2(){

    }

    /**
     * Metodo auxiliar de delete para encontrar el elemento minimo de un arbol
     * @param vertice
     * @return Vertice
     */
    private VerticeAVL buscaMinimo(VerticeAVL vertice){
        if(vertice.hayIzquierdo()){
            return buscaMinimo(vertice.izquierdo);
        }else{
            //System.out.println(vertice);
            return vertice;
        }
    }

    /**
   * Rotación simple izquierda-izquierda (rotación LL) izquierdo se convierte en el nodo raíz de v, v se convierte en el subárbol derecho de izquierdo
    * @param v
     * @return
    */
    private VerticeAVL<T> rotarDerecha(VerticeAVL<T> v){
        // rotar el nodo izquierdo al nodo raíz
        VerticeAVL<T> izquierdo= v.izquierdo;
        // Al mismo tiempo, el subárbol derecho de izquierdo se convierte en el subárbol izquierdo de v
        v.izquierdo=izquierdo.Derecho;
        // v se convierte en el subárbol correcto de izquierdo
        izquierdo.Derecho=v;
        // Recalcula la altura de v / izquierdo
        v.altura=Math.max(v.izquierdo.altura, v.Derecho.altura)+1;
        izquierdo.altura=Math.max(izquierdo.izquierdo.getAltura(),izquierdo.izquierdo.getAltura())+1;
        return izquierdo;// Regresar al nuevo nodo raíz
    }
    /**
    * La rotación simple derecha y derecha (rotación RR) v se convierte en el nodo raíz de izquierdo, izquierdo se convierte en el subárbol izquierdo de v
    * @return
    */
    private VerticeAVL<T> singleRotateDerecho(VerticeAVL<T> izquierdo){
 
        VerticeAVL<T> v=izquierdo.Derecho;
 
        izquierdo.Derecho=v.izquierdo;
        v.izquierdo=izquierdo;
 
        // Recalcula la altura de v / izquierdo
        izquierdo.altura=Math.max(izquierdo.izquierdo.getAltura(),izquierdo.Derecho.getAltura())+1;
        v.altura=Math.max(v.izquierdo.getAltura(),izquierdo.getAltura())+1;
 
        // Regresar al nuevo nodo raíz
        return v;
    }

    /**
    * Girar a izquierda y derecha (rotación LR) v (raíz) izquierdo y nodo Convertir y en nodo raíz
    * @return
    */
    private VerticeAVL<T> doubleRotateizquierdoithizquierdo(VerticeAVL<T> v){
        // izquierdo primero realiza la rotación RR
         v.izquierdo=singleRotateDerecho(v.izquierdo);
         // Entonces LL rotación de v
         return rotarDerecha(v);
    }

    /**
    * Rotación derecha-izquierda (rotación RL)
    * @param izquierdo
    * @return
    */
    private VerticeAVL<T> doubleRotateizquierdoithDerecho(VerticeAVL<T> v){
        // Primera rotación LL
        v.Derecho=rotarDerecha(v.Derecho);
        // Vuelve a girar de nuevo
        return singleRotateDerecho(v);
    }

    /**
    * Método de inserción
    * @param elemento
    */
    public void insert(T elemento) {
        if (elemento==null){
            throw new RuntimeException("elemento can\'t not be null ");
        }
        this.raiz=insert(elemento,this.raiz);
    }
 
    private VerticeAVL<T> insert(T elemento , VerticeAVL<T> p){
 
        // Indicando que no hay un nodo hijo, puede crear un nuevo nodo para insertar.
        if(p==null){
            p=new VerticeAVL<T>(elemento);
        }else if(elemento.compareTo(p.elemento)<0){// Encuentra la posición de inserción en el subárbol izquierdo
            p.izquierdo=insert(elemento,p.izquierdo);
 
            // Calcule la altura del subárbol después de la inserción. Si es igual a 2, debe restablecer el equilibrio nuevamente. Debido a que se inserta a la izquierda, la altura del subárbol izquierdo debe ser mayor o igual que la altura del subárbol derecho
            if(p.izquierdo.getAltura()-p.Derecho.getAltura()==2){
                // Determine si los datos son el hijo izquierdo o el hijo derecho del punto de inserción
                if(elemento.compareTo(p.izquierdo.elemento)<0){
                    // Realizar rotación LL
                    p=rotarDerecha(p);
                }else {
                    // gira a izquierda y derecha
                    p=doubleRotateizquierdoithizquierdo(p);
                }
            }
        }else if (elemento.compareTo(p.elemento)>0){// Encuentra la posición de inserción en el subárbol derecho
            p.Derecho=insert(elemento,p.Derecho);
            if(p.Derecho.getAltura()-p.izquierdo.getAltura()==2){
                if (elemento.compareTo(p.Derecho.elemento)<0){
                    // Rotación derecha e izquierda
                    p=doubleRotateizquierdoithDerecho(p);
                }else {
                    p=singleRotateDerecho(p);
                }
            }
        }else;//if evist do nothing
        // Recalcula la altura de cada nodo
        p.altura = Math.max(p.izquierdo.getAltura(),p.Derecho.getAltura()) + 1;

        return p;// Regresar al nodo raíz
    }

    /**
    * Eliminar método
    * @param elemento
    */
    public void remove(T elemento) {
        if (elemento==null){
             throw new RuntimeException("elemento can\'t not be null ");
        }
        this.raiz=remove(elemento,this.raiz);
    }
 
    /**
   * Eliminar operación
     * @param elemento
    * @param p
    * @return
    */
    private VerticeAVL<T> remove(T elemento,VerticeAVL<T> p){
 
        if(p ==null)
            return null;
 
        int comparacion=elemento.compareTo(p.elemento);
 
        // Encuentra el elemento que se eliminará del subárbol izquierdo
        if(comparacion<0){
             p.izquierdo=remove(elemento,p.izquierdo);
 
            // Comprueba si está equilibrado
            if(p.Derecho.getAltura()-p.izquierdo.getAltura()==2){
                VerticeAVL<T> actual=p.Derecho;
                // Determine qué tipo de rotación se necesita
                if(actual.izquierdo.getAltura()>actual.Derecho.getAltura()){
                    //LL
                    p=rotarDerecha(p);
                }else{
                //LR
                    p=doubleRotateizquierdoithizquierdo(p);
                }
            }

        }else if(comparacion>0){// Encuentra el elemento que se eliminará del subárbol derecho
            p.Derecho=remove(elemento,p.Derecho);
            // Comprueba si está equilibrado
            if(p.izquierdo.getAltura()-p.Derecho.getAltura()==2){
            VerticeAVL<T> actual=p.izquierdo;
            // Determine qué tipo de rotación se necesita
            if(actual.Derecho.getAltura()>actual.izquierdo.getAltura()){
                //RR
                p=singleRotateDerecho(p);
            }else{
                //RL
                p=doubleRotateizquierdoithDerecho(p);
            }
            }
        }
    // Se ha encontrado el elemento que se va a eliminar y el nodo que se va a eliminar tiene dos nodos secundarios
    else if(p.Derecho!=null&&p.izquierdo!=null){

        // Encuentra el nodo de reemplazo
        VerticeAVL a=buscaMinimo(p.Derecho);
        T n=(T)a.elemento;
        p.elemento=n;

        // Eliminar el nodo para su reemplazo
        p.Derecho = remove( p.elemento, p.Derecho );
    }else {
        // Si solo hay un nodo hijo o solo un nodo hoja
        p=(p.izquierdo!=null)? p.izquierdo:p.Derecho;
    }
        // Actualiza el valor de altura
    if(p!=null){
        p.altura = Math.max(p.izquierdo.getAltura(),p.Derecho.getAltura()) + 1;
    }
    return p;
    }
    
}

    


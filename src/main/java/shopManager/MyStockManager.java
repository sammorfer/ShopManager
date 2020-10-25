package shopManager;
import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.logging.*;
//va a seguir el patrón singleton (Una instancia sólo tiene un manager, pero puede haber varias instancias...)

/**
 * @author Isabel Román
 * @version 0.0. 
 * Primera versión de MyStockManager, clase que implementa StockManager usando el patrón singleton
 *
 */
public class MyStockManager implements StockManager{
	
	private static Logger trazador=Logger.getLogger(MyStockManager.class.getName());
	private static StockManager my_instance;
	private HazelcastInstance hz_instance;
	private Config cfg;
	//Mapa de productos que será compartido por todos los stockManager que se instancien
	private IMap<String, Product> mapProducts;
	/**
	 * El constructor crea la instancia hazelcast, pero es privado, sólo la propia clase puede crear una instancia (un objeto)
	 */
	private MyStockManager(){
		cfg = new Config();
		
		HazelcastInstance hz_instance = Hazelcast.newHazelcastInstance(cfg);
		trazador.info("Acabo de crear una instancia hazelcast para el MyStockManager");
		mapProducts = hz_instance.getMap("products");
        trazador.info("Se ha recuperado referencia al mapa products, si no se había usado se ha creado");
	}
	/**
	 * @see 
	 * java.lang.Object
	 * El compilador me avisa de que uso algo "deprecated" por eso añado la opción de compilación en gradle, para ver más detalle
	*/
	@Override
	protected void finalize() throws Throwable{
		trazador.info("Finalizando el MyStockManager, borra la instancia hazelcast y deja a null la instancia singleton");
		hz_instance.shutdown();
		my_instance=null;
		hz_instance=null;
		
		}
	
	public static StockManager getInstance() {
		if (my_instance==null){
			my_instance=new MyStockManager();
		}
		return my_instance;
	}
	@Override
	public void addProduct(Product newProduct) {
		Product productTmp;
		trazador.info("El tamaño del mapa al entrar es " + mapProducts.size());
		if(mapProducts.containsKey(newProduct.getId())){
			productTmp=searchProduct(newProduct.getId());
			newProduct.setNumber(productTmp.getNumber()+newProduct.getNumber());
		}
		mapProducts.put(newProduct.getId(), newProduct);
		trazador.info("El mapa de tamaño " + mapProducts.size()+" incluye "+newProduct);
	}
	@Override
	public Product searchProduct(String id) {
		Product product=null;
		if(mapProducts.containsKey(id)){
			product=mapProducts.get(id);
			trazador.info("Hay "+ product);
		
		}else {
			trazador.info("El "+product+" no está en el mapa");
		}
		return product;
	}
	/**
	 * @see 
	 * java.lang.Object
	 * El método clone permite crear una instancia a partir de otra, lo que en una clase que siga el patrón singleton no se puede permitir
	 */
	@Override
	public MyStockManager clone() {
	    try {
		        throw new CloneNotSupportedException();
		    } catch (CloneNotSupportedException ex) {
		        System.out.println("No se puede clonar un objeto de la clase MyStockManager, sigue patrón singleton");
		    }
		    return null; 
		}
	
}

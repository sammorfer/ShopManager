package shopmanager;
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
	private static StockManager myInstance;
	private HazelcastInstance hzInstance;
	private Config cfg;
	//Mapa de productos que será compartido por todos los stockManager que se instancien
	private IMap<String, Product> mapProducts;
	/**
	 * El constructor crea la instancia hazelcast, pero es privado, sólo la propia clase puede crear una instancia (un objeto)
	 */
	private MyStockManager(){
		cfg = new Config();
		
		hzInstance = Hazelcast.newHazelcastInstance(cfg);
		trazador.info("Acabo de crear una instancia hazelcast para el MyStockManager");
		mapProducts = hzInstance.getMap("products");
        trazador.info("Se ha recuperado referencia al mapa products, si no se había usado se ha creado");
	}
	/**
	 * @deprecated
	 * @see 
	 * java.lang.Object
	 * El compilador me avisa de que uso algo "deprecated" por eso añado la opción de compilación en gradle, para ver más detalle
	*/
	@Override
	@Deprecated(since="0,0")
	protected void finalize() throws Throwable{
		trazador.info("Finalizando el MyStockManager, apaga la instancia hazelcast");
		hzInstance.shutdown();
		}
	
	public static StockManager getInstance() {
		if (myInstance==null){
			myInstance=new MyStockManager();
		}
		return myInstance;
	}
	@Override
	public void addProduct(Product newProduct) {
		Product productTmp;
		String msg="El tamaño del mapa al entrar es " + mapProducts.size();
		trazador.info(msg);
		if(mapProducts.containsKey(newProduct.getId())){
			productTmp=searchProduct(newProduct.getId());
			newProduct.setNumber(productTmp.getNumber()+newProduct.getNumber());
		}
		mapProducts.put(newProduct.getId(), newProduct);
		msg="El mapa de tamaño " + mapProducts.size()+" incluye "+newProduct;
		trazador.info(msg);
	}
	@Override
	public Product searchProduct(String id) {
		Product product=null;
		String msg;
		if(mapProducts.containsKey(id)){
			product=mapProducts.get(id);
			msg="Hay "+ product;
			trazador.info(msg);
		
		}else {
			msg="El "+product+" no está en el mapa";
			trazador.info(msg);
		}
		return product;
	}
	/**
	 * @throws CloneNotSupportedException 
	 * @see 
	 * java.lang.Object
	 * El método clone permite crear una instancia a partir de otra, lo que en una clase que siga el patrón singleton no se puede permitir
	 */
	@Override
	public MyStockManager clone() throws CloneNotSupportedException {
		trazador.warning("No se puede clonar un objeto de la clase MyStockManager, sigue patrón singleton");
	    throw new CloneNotSupportedException();
		}
	
}

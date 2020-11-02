package shopmanager;

/**
 * @author Isabel Román
 * @version 0.0.
 * Primera versión de la interfaz StockManager, la forma de gestionar el stock compartido por distintas instancias hazelcast
 *
 */
public interface StockManager {
/**
 * 
 * @param newProduct
 * El producto a insertar en el stock, el campo de unidades dentro del producto indica cuántos se insertan.
 * Si ya estaba en el mapa se suma el número de unidades.
 * Si no estaba en el mapa se inserta el producto nuevo con el número de unidades indicado.
 */
	void addProduct(Product newProduct);
	/**
	 * 
	 * @param id
	 * Identificador del producto a buscar
	 * @return
	 * El producto buscado, que incluye el número de unidades existentes, null is no está en el mapa
	 */
	Product searchProduct(String id);
	
}


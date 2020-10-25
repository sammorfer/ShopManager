/**
 * 
 */
package shopManager;

import static org.junit.Assert.*;


import org.junit.Test;

import shopManager.MyProduct;
import shopManager.Product;
import shopManager.StockManager;
import shopManager.MyStockManager;


/**
 * @author isabo
 *
 */
public class MyStockManagerTest {

	StockManager underTest;
	String underTestAsString;

	/**
	 * Test method for {@link shopManager.MyStockManager#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		underTest=MyStockManager.getInstance();
		underTestAsString=underTest.toString();
		
		assertNotNull("getInstance no devuelve una referencia válida",underTest);
		assertEquals("getInstance devuelve una referencia diferente",underTest,MyStockManager.getInstance());
	}

	/**
	 * Test method for {@link shopManager.MyStockManager#addProduct(shopManager.Product)}.
	 */
	@Test
	public void testAddProduct() {
		Product product=new MyProduct("nuevoProducto",1);
		MyStockManager.getInstance().addProduct(product);
		assertEquals("Los objetos como string no son iguales",product.toString(),MyStockManager.getInstance().searchProduct("nuevoProducto").toString());
		assertEquals("El id del producto recuperado no es el buscado", "nuevoProducto", MyStockManager.getInstance().searchProduct("nuevoProducto").getId());
		assertEquals("El número de unidades del producto recuperado no es el esperado", 1, MyStockManager.getInstance().searchProduct("nuevoProducto").getNumber());	
	}

	/**
	 * Test method for {@link shopManager.MyStockManager#searchProduct(java.lang.String)}.
	 */
	@Test
	public void testSearchProduct() {
		assertNull("Encuentra un producto que no existe",MyStockManager.getInstance().searchProduct("noexiste"));
	}

}

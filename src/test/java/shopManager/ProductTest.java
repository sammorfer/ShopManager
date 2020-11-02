/**
 * 
 */
package shopManager;

import static org.junit.Assert.*;
import java.util.logging.*;
import org.junit.Before;

import org.junit.Test;

import shopmanager.MyProduct;
import shopmanager.Product;


/**
 * @author Isabel Román
 *
 */
public class ProductTest {
	private static Logger trazador=Logger.getLogger(ProductTest.class.getName());
	static Product producto;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		producto=new MyProduct("primerId");
	    assertEquals("Al crear producto sin número de unidades debe tener 1",1,producto.getNumber());
	    assertEquals("El id del producto es el pasado en el constructor","primerId",producto.getId());
		
	}

	
	@Test
	public void TestSetId() {
		trazador.info("Test del setId");
		producto.setId("nuevoId");
		assertEquals("El setId no funciona ","nuevoId",producto.getId());
		
	}
	
	@Test
	public void TestSetNumber() {
		trazador.info("Test del setNumber");
		producto.setNumber(33);
		assertEquals("El setNumber no funciona ",33,producto.getNumber());
	}
	
	@Test
	public void TestOneMore() {
		trazador.info("Test del oneMore");
		producto.setNumber(33);
		
		assertEquals("El oneMore no funciona ",34,producto.oneMore());
	}
	@Test
	public void TestOneLess() {
		trazador.info("Test del OneLess");
		producto.setNumber(1);
		
		assertEquals("El oneLess no funciona cuando es distinto de 0 ",0,producto.oneLess());
		producto.oneLess();
		assertEquals("El oneLess no funciona cuando es 0 ",0,producto.oneLess());
	}
	

}

package shopManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import model.MyProduct;
import shopmanager.PrizeComparator;

class PrizeComparatorTest {

	@Test
	@Tag("unidad")
	@DisplayName("Prueba del metodo que compara dos precios")
	void testCompare() {
		MyProduct producto1 = new MyProduct("Producto1");
		MyProduct producto2 = new MyProduct("Producto2");
		PrizeComparator comparador = new PrizeComparator();
		producto1.setPrize(3);
		producto2.setPrize(3);
		//Probamos el caso de precios de igual producto
		assertEquals(0, comparador.compare(producto1, producto2), "El comparador no detecta que los precios son iguales");
		//Probamos el caso de que el segundo precio sea mayor
		producto2.setPrize(4);
		assertTrue(comparador.compare(producto1, producto2)<0,"El comparador no detecta que el segundo precio es mayor");
		//Probamos el caso de que el primer precio sea mayor
		assertTrue(comparador.compare(producto2, producto1)>0,"El comparador no detecta que el primer precio es mayor");
	}

}

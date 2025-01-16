import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.Products.AlreadyExistsException;
import ru.netology.Products.NotFoundException;
import ru.netology.Products.Product;
import ru.netology.Products.ShopRepository;

public class ShopRepositoryTest {
    ShopRepository repo = new ShopRepository();
    Product product1 = new Product(1, "Product1", 100);
    Product product2 = new Product(2, "Product2", 200);
    Product product3 = new Product(3, "Product3", 300);
    Product product4 = new Product(4, "Product4", 400);

    @BeforeEach
    public void setup() {
        repo.add(product1);
        repo.add(product2);
        repo.add(product3);
        repo.add(product4);
    }


    @Test
    public void test1() {  // Добавление нового продукта
        Product newProduct = new Product(5, "Product5", 500);
        repo.add(newProduct);

        Product[] expected = {product1, product2, product3, product4, newProduct};
        Product[] actual = repo.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void test2() { // Добавление элемента с повторяющимся ID
        Assertions.assertThrows(AlreadyExistsException.class, () -> {
            repo.save(product3);
        });
    }

    @Test
    public void test3() { // Удалении несуществующего ID
        Assertions.assertThrows(NotFoundException.class, () -> {
            repo.removeById(9);
        });
    }

    @Test
    public void test4() { // Удалении существующего ID
        Product product = new Product(2, "Product2", 200);
        repo.removeById(2);

        Product[] expected = {product1, product3, product4};
        Product[] actual = repo.findAll();

        Assertions.assertArrayEquals(expected, actual);
    }
}

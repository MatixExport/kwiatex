package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.cassandra.BaseRepository;

import java.util.List;

public class CassProductRepository extends BaseRepository {

    private final ProductDao productDao;
    private final BoundStatement insertIntoProductsById;
    private final BoundStatement deleteFromProductsById;
    private final BoundStatement updateProductsById;

    public CassProductRepository(CqlSession session) {
        super(session);

        createTables();

        ProductMapper productMapper = new ProductMapperBuilder(getSession()).build();
        productDao = productMapper.getProductDao();

        insertIntoProductsById = ProductStatementFactory.prepareInsert(getSession());
        deleteFromProductsById = ProductStatementFactory.prepareDeleteFromProductsById(getSession());
        updateProductsById = ProductStatementFactory.prepareUpdateProductById(getSession());
    }

    public void createTables() {
        getSession().execute(ProductStatementFactory.createProductsByIdTable);
    }

    public void dropTables() {
        getSession().execute(ProductStatementFactory.dropProductByIdTable);
    }

    public void truncateTables() {
        getSession().execute(ProductStatementFactory.truncateProductByIdTable);
    }

    //This could be done with default DAO remove, but then if we add more tables for products (like product_by_quantity)
    //that approach would no longer be valid
    public void remove(int id) {
        getSession().execute(
                deleteFromProductsById.setInt(
                    ProductConsts.ID,id
                )
        );
    }

    public List<ProductWithInfo> getAll() {
        return productDao.findAll()
                .map(CassProduct::toDomainModel)
                .toList();
    }

    public ProductWithInfo getById(int id) {
        CassProduct product = productDao.findProductById(id);
        if(product == null) return null;
        return productDao.findProductById(id).toDomainModel();
    }

    public ProductWithInfo save(ProductWithInfo productWithInfo) {
        getSession().execute(
                productDao.bind(
                        new CassProduct(productWithInfo), insertIntoProductsById
                )
        );
        return productWithInfo;
    }

    public void update(ProductWithInfo productWithInfo) {
        getSession().execute(
                productDao.bind(
                        new CassProduct(productWithInfo),
                        updateProductsById
                )

        );
    }

}

package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.cassandra.BaseRepository;

import java.util.List;

public class CassProductRepository extends BaseRepository implements ProductRepository {

    private final ProductDao productDao;
    private final BoundStatement insertIntoProductsById;
    private final BoundStatement deleteFromProductsById;
    private final BoundStatement updateProductsById;

    public CassProductRepository() {
        super();

        createProductTables();

        ProductMapper productMapper = new ProductMapperBuilder(getSession()).build();
        productDao = productMapper.getProductDao();

        insertIntoProductsById = ProductStatementFactory.prepareInsert(getSession());
        deleteFromProductsById = ProductStatementFactory.prepareDeleteFromProductsById(getSession());
        updateProductsById = ProductStatementFactory.prepareUpdateProductById(getSession());
    }

    private void createProductTables() {
        getSession().execute(ProductStatementFactory.createProductsByIdTable);
    }

    //This could be done with default DAO remove, but then if we add more tables for products (like product_by_quantity)
    //that approach would no longer be valid
    @Override
    public void remove(int id) {
        getSession().execute(
                deleteFromProductsById.setInt(
                    ProductConsts.ID,id
                )
        );
    }

    @Override
    public List<ProductWithInfo> getAll() {
        return productDao.findAll()
                .map(CassProduct::toDomainModel)
                .toList();
    }

    @Override
    public ProductWithInfo getById(int id) {
        CassProduct product = productDao.findProductById(id);
        if(product == null) return null;
        return productDao.findProductById(id).toDomainModel();
    }

    @Override
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

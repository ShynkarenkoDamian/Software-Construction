package lab7.variant8.datarepositories;

/** Запускає контрактні тести для InMemoryRepository. */
class InMemoryRepositoryContractTest extends RepositoryContractTest {

    @Override
    protected Repository<String> createRepository() {
        return new InMemoryRepository<>();
    }
}

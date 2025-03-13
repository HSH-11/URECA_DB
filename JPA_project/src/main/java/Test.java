import entity.Product;
import repository.ProductRepository;

public class Test {
    public static void main(String[] args) {
        ProductRepository productRepo = new ProductRepository();

        // ✅ 새로운 상품 추가
        System.out.println("\n==== 상품 추가 테스트 ====");
        Product newProduct = new Product("Wireless Mouse", 29.99, 50);
        productRepo.insertProduct(newProduct);
        System.out.println("🔹 추가된 상품: " + newProduct);

        // ✅ 이름으로 상품 검색
        System.out.println("\n==== 'Mouse' 포함된 상품 검색 ====");
        productRepo.getProductsByName("Mouse").forEach(System.out::println);

        // ✅ 상품 수정
        System.out.println("\n==== 상품 정보 수정 테스트 ====");
        newProduct.setPrice(24.99);
        newProduct.setStockQuantity(100);
        productRepo.updateProduct(newProduct);
        System.out.println("🔹 수정된 상품: " + newProduct);

        // ✅ 재고 업데이트
        System.out.println("\n==== 상품 재고 수정 테스트 ====");
        productRepo.updateStock(newProduct.getProductId(), 200);
        System.out.println("🔹 재고 업데이트 완료!");

        // ✅ 최종 데이터 조회
        System.out.println("\n==== 최종 상품 목록 ====");
        productRepo.getProductsByName("Mouse").forEach(System.out::println);
    }
}

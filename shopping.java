import java.util.*;

class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Customer extends User {
    private String customerID;
    private String level;
    private Date registrationDate;
    private double totalSpent;
    private String phoneNumber;
    private String email;

    public Customer(String username, String password, String customerID, String level, Date registrationDate, double totalSpent, String phoneNumber, String email) {
        super(username, password);
        this.customerID = customerID;
        this.level = level;
        this.registrationDate = registrationDate;
        this.totalSpent = totalSpent;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getLevel() {
        return level;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void addSpent(double amount) {
        this.totalSpent += amount;
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void resetCustomerPassword(Customer customer, String newPassword) {
        customer.setPassword(newPassword);
    }
}

class Product {
    private String productID;
    private String name;
    private String manufacturer;
    private Date productionDate;
    private String model;
    private double purchasePrice;
    private double retailPrice;
    private int quantity;

    public Product(String productID, String name, String manufacturer, Date productionDate, String model, double purchasePrice, double retailPrice, int quantity) {
        this.productID = productID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.productionDate = productionDate;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public String getModel() {
        return model;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateProduct(String name, String manufacturer, Date productionDate, String model, double purchasePrice, double retailPrice, int quantity) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.productionDate = productionDate;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
        this.quantity = quantity;
    }
}

class ShoppingCart {
    private Map<Product, Integer> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public void updateProductQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            removeProduct(product);
        } else {
            cartItems.put(product, quantity);
        }
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }
}

public class ShoppingSystem {
    private static Admin admin = new Admin("admin", "ynuinfo#777");
    private static Map<String, Customer> customers = new HashMap<>();
    private static Map<String, Product> products = new HashMap<>();

    public static void main(String[] args) {
        // 预设一些数据
        initializeData();

        // 模拟登录和系统功能
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请选择登录类型: 1. 管理员 2. 客户 3. 退出");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                handleAdminFunctions(scanner);
            } else if (choice == 2) {
                handleCustomerFunctions(scanner);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }

        scanner.close();
    }

    private static void handleAdminFunctions(Scanner scanner) {
        System.out.println("请输入管理员用户名: ");
        String username = scanner.nextLine();
        System.out.println("请输入管理员密码: ");
        String password = scanner.nextLine();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            while (true) {
                System.out.println("管理员功能菜单: 1. 密码管理 2. 客户管理 3. 商品管理 4. 退出");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                if (choice == 1) {
                    handleAdminPasswordManagement(scanner);
                } else if (choice == 2) {
                    handleCustomerManagement(scanner);
                } else if (choice == 3) {
                    handleProductManagement(scanner);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("无效选择，请重试。");
                }
            }
        } else {
            System.out.println("用户名或密码错误。");
        }
    }

    private static void handleAdminPasswordManagement(Scanner scanner) {
        while (true) {
            System.out.println("密码管理菜单: 1. 修改自身密码 2. 重置客户密码 3. 返回");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                System.out.println("请输入新密码: ");
                String newPassword = scanner.nextLine();
                admin.setPassword(newPassword);
                System.out.println("密码修改成功。");
            } else if (choice == 2) {
                System.out.println("请输入要重置密码的客户ID: ");
                String customerID = scanner.nextLine();
                if (customers.containsKey(customerID)) {
                    System.out.println("请输入新密码: ");
                    String newPassword = scanner.nextLine();
                    admin.resetCustomerPassword(customers.get(customerID), newPassword);
                    System.out.println("客户密码重置成功。");
                } else {
                    System.out.println("客户ID不存在。");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void handleCustomerManagement(Scanner scanner) {
        while (true) {
            System.out.println("客户管理菜单: 1. 列出所有客户信息 2. 删除客户信息 3. 查询客户信息 4. 返回");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                listAllCustomers();
            } else if (choice == 2) {
                System.out.println("请输入要删除的客户ID: ");
                String customerID = scanner.nextLine();
                if (customers.containsKey(customerID)) {
                    System.out.println("警告：删除后无法恢复，是否继续删除操作？(y/n)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        customers.remove(customerID);
                        System.out.println("客户删除成功。");
                    }
                } else {
                    System.out.println("客户ID不存在。");
                }
            } else if (choice == 3) {
                System.out.println("请输入要查询的客户ID或用户名: ");
                String input = scanner.nextLine();
                queryCustomerInfo(input);
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void listAllCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println("客户ID: " + customer.getCustomerID() +
                               ", 用户名: " + customer.getUsername() +
                               ", 用户级别: " + customer.getLevel() +
                               ", 注册时间: " + customer.getRegistrationDate() +
                               ", 累计消费: " + customer.getTotalSpent() +
                               ", 手机号: " + customer.getPhoneNumber() +
                               ", 邮箱: " + customer.getEmail());
        }
    }

    private static void queryCustomerInfo(String input) {
        for (Customer customer : customers.values()) {
            if (customer.getCustomerID().equals(input) || customer.getUsername().equals(input)) {
                System.out.println("客户ID: " + customer.getCustomerID() +
                                   ", 用户名: " + customer.getUsername() +
                                   ", 用户级别: " + customer.getLevel() +
                                   ", 注册时间: " + customer.getRegistrationDate() +
                                   ", 累计消费: " + customer.getTotalSpent() +
                                   ", 手机号: " + customer.getPhoneNumber() +
                                   ", 邮箱: " + customer.getEmail());
                return;
            }
        }
        System.out.println("未找到客户信息。");
    }

    private static void handleProductManagement(Scanner scanner) {
        while (true) {
            System.out.println("商品管理菜单: 1. 列出所有商品信息 2. 添加商品信息 3. 修改商品信息 4. 删除商品信息 5. 查询商品信息 6. 返回");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                listAllProducts();
            } else if (choice == 2) {
                addProduct(scanner);
            } else if (choice == 3) {
                updateProduct(scanner);
            } else if (choice == 4) {
                deleteProduct(scanner);
            } else if (choice == 5) {
                queryProduct(scanner);
            } else if (choice == 6) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void listAllProducts() {
        for (Product product : products.values()) {
            System.out.println("商品编号: " + product.getProductID() +
                               ", 商品名称: " + product.getName() +
                               ", 生产厂家: " + product.getManufacturer() +
                               ", 生产日期: " + product.getProductionDate() +
                               ", 型号: " + product.getModel() +
                               ", 进货价: " + product.getPurchasePrice() +
                               ", 零售价: " + product.getRetailPrice() +
                               ", 数量: " + product.getQuantity());
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.println("请输入商品信息: ");
        System.out.print("商品编号: ");
        String productID = scanner.nextLine();
        System.out.print("商品名称: ");
        String name = scanner.nextLine();
        System.out.print("生产厂家: ");
        String manufacturer = scanner.nextLine();
        System.out.print("生产日期 (YYYY-MM-DD): ");
        Date productionDate = Date.valueOf(scanner.nextLine());
        System.out.print("型号: ");
        String model = scanner.nextLine();
        System.out.print("进货价: ");
        double purchasePrice = scanner.nextDouble();
        System.out.print("零售价: ");
        double retailPrice = scanner.nextDouble();
        System.out.print("数量: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Product product = new Product(productID, name, manufacturer, productionDate, model, purchasePrice, retailPrice, quantity);
        products.put(productID, product);
        System.out.println("商品添加成功。");
    }

    private static void updateProduct(Scanner scanner) {
        System.out.println("请输入要修改的商品编号: ");
        String productID = scanner.nextLine();
        if (products.containsKey(productID)) {
            System.out.println("请输入新的商品信息: ");
            System.out.print("商品名称: ");
            String name = scanner.nextLine();
            System.out.print("生产厂家: ");
            String manufacturer = scanner.nextLine();
            System.out.print("生产日期 (YYYY-MM-DD): ");
            Date productionDate = Date.valueOf(scanner.nextLine());
            System.out.print("型号: ");
            String model = scanner.nextLine();
            System.out.print("进货价: ");
            double purchasePrice = scanner.nextDouble();
            System.out.print("零售价: ");
            double retailPrice = scanner.nextDouble();
            System.out.print("数量: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            products.get(productID).updateProduct(name, manufacturer, productionDate, model, purchasePrice, retailPrice, quantity);
            System.out.println("商品修改成功。");
        } else {
            System.out.println("商品编号不存在。");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.println("请输入要删除的商品编号: ");
        String productID = scanner.nextLine();
        if (products.containsKey(productID)) {
            System.out.println("警告：删除后无法恢复，是否继续删除操作？(y/n)");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                products.remove(productID);
                System.out.println("商品删除成功。");
            }
        } else {
            System.out.println("商品编号不存在。");
        }
    }

    private static void queryProduct(Scanner scanner) {
        System.out.println("请输入查询条件 (商品名称/生产厂家/零售价): ");
        String condition = scanner.nextLine();
        for (Product product : products.values()) {
            if (product.getName().contains(condition) ||
                product.getManufacturer().contains(condition) ||
                Double.toString(product.getRetailPrice()).contains(condition)) {
                System.out.println("商品编号: " + product.getProductID() +
                                   ", 商品名称: " + product.getName() +
                                   ", 生产厂家: " + product.getManufacturer() +
                                   ", 生产日期: " + product.getProductionDate() +
                                   ", 型号: " + product.getModel() +
                                   ", 进货价: " + product.getPurchasePrice() +
                                   ", 零售价: " + product.getRetailPrice() +
                                   ", 数量: " + product.getQuantity());
            }
        }
    }

    private static void handleCustomerFunctions(Scanner scanner) {
        System.out.println("客户功能菜单: 1. 注册 2. 登录 3. 返回");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (choice == 1) {
            registerCustomer(scanner);
        } else if (choice == 2) {
            loginCustomer(scanner);
        } else if (choice == 3) {
            return;
        } else {
            System.out.println("无效选择，请重试。");
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.println("请输入注册信息: ");
        System.out.print("用户名 (至少5个字符): ");
        String username = scanner.nextLine();
        System.out.print("密码 (至少8个字符，包含大小写字母、数字和标点符号): ");
        String password = scanner.nextLine();
        System.out.print("客户ID: ");
        String customerID = scanner.nextLine();
        System.out.print("用户级别: ");
        String level = scanner.nextLine();
        System.out.print("注册日期 (YYYY-MM-DD): ");
        Date registrationDate = Date.valueOf(scanner.nextLine());
        System.out.print("累计消费: ");
        double totalSpent = scanner.nextDouble();
        System.out.print("手机号: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("邮箱: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(username, password, customerID, level, registrationDate, totalSpent, phoneNumber, email);
        customers.put(customerID, customer);
        System.out.println("注册成功。");
    }

    private static void loginCustomer(Scanner scanner) {
        System.out.println("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.println("请输入密码: ");
        String password = scanner.nextLine();

        for (Customer customer : customers.values()) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                handleCustomerActions(scanner, customer);
                return;
            }
        }

        System.out.println("用户名或密码错误。");
    }

    private static void handleCustomerActions(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("客户功能菜单: 1. 修改密码 2. 忘记密码 3. 购物车管理 4. 购物历史 5. 退出");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                System.out.println("请输入新密码: ");
                String newPassword = scanner.nextLine();
                customer.setPassword(newPassword);
                System.out.println("密码修改成功。");
            } else if (choice == 2) {
                System.out.println("忘记密码功能，请输入用户名和邮箱地址: ");
                String username = scanner.nextLine();
                String email = scanner.nextLine();
                if (customer.getUsername().equals(username) && customer.getEmail().equals(email)) {
                    String newPassword = generateRandomPassword();
                    customer.setPassword(newPassword);
                    System.out.println("新密码已发送到您的邮箱，请使用新密码登录。");
                } else {
                    System.out.println("用户名或邮箱地址错误。");
                }
            } else if (choice == 3) {
                handleShoppingCart(scanner, customer);
            } else if (choice == 4) {
                System.out.println("购物历史功能未实现。");
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void handleShoppingCart(Scanner scanner, Customer customer) {
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("购物车管理菜单: 1. 添加商品 2. 移除商品 3. 修改商品数量 4. 结账 5. 返回");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                System.out.println("请输入商品编号: ");
                String productID = scanner.nextLine();
                System.out.println("请输入数量: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                if (products.containsKey(productID)) {
                    cart.addProduct(products.get(productID), quantity);
                    System.out.println("商品添加成功。");
                } else {
                    System.out.println("商品编号不存在。");
                }
            } else if (choice == 2) {
                System.out.println("请输入要移除的商品编号: ");
                String productID = scanner.nextLine();
                if (products.containsKey(productID)) {
                    cart.removeProduct(products.get(productID));
                    System.out.println("商品移除成功。");
                } else {
                    System.out.println("商品编号不存在。");
                }
            } else if (choice == 3) {
                System.out.println("请输入要修改的商品编号: ");
                String productID = scanner.nextLine();
                if (products.containsKey(productID)) {
                    System.out.println("请输入新的数量: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    cart.updateProductQuantity(products.get(productID), quantity);
                    System.out.println("商品数量修改成功。");
                } else {
                    System.out.println("商品编号不存在。");
                }
            } else if (choice == 4) {
                checkout(cart, customer);
                return;
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void checkout(ShoppingCart cart, Customer customer) {
        double totalAmount = 0;
        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += product.getRetailPrice() * quantity;
            product.setQuantity(product.getQuantity() - quantity);
        }

        customer.addSpent(totalAmount);
        System.out.println("结账成功，总金额: " + totalAmount);
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    private static void initializeData() {
        // 初始化一些客户和商品数据
        customers.put("C001", new Customer("Alice", "password123!", "C001", "金牌客户", new Date(), 1000.0, "1234567890", "alice@example.com"));
        customers.put("C002", new Customer("Bob", "password456!", "C002", "银牌客户", new Date(), 500.0, "0987654321", "bob@example.com"));

        products.put("P001", new Product("P001", "商品1", "厂家1", new Date(), "型号1", 100.0, 120.0, 50));
        products.put("P002", new Product("P002", "商品2", "厂家2", new Date(), "型号2", 200.0, 240.0, 30));
    }
}
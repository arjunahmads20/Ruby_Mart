CREATE TABLE store (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(64) DEFAULT NULL,
    address VARCHAR(256) NOT NULL UNIQUE,
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(64) NOT NULL,
    fullname VARCHAR(64) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    gender ENUM("male", "female") NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(64) NOT NULL,
    role ENUM("admin", "cashier", "customer") NOT NULL,
    datetime_joined DATETIME DEFAULT CURRENT_TIMESTAMP,
    datetime_last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE admin (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE cashier (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user INT NOT NULL,
    id_store INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_store) REFERENCES store(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE customer (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user INT NOT NULL,
    account_number VARCHAR(15) NOT NULL,
    account_pin INT DEFAULT 123456,
    account_balance DECIMAL NOT NULL DEFAULT 0,
    handpalm_url VARCHAR(256),
    status ENUM("active", "non-active") NOT NULL DEFAULT "active",

    FOREIGN KEY (id_user) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE item_category (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(64) NOT NULL,
    icon_url VARCHAR(64) DEFAULT NULL,
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    datetime_last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE item (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_item_category INT DEFAULT NULL,
    name VARCHAR(64) NOT NULL,
    size FLOAT NOT NULL,

    unit ENUM("pack", "pcs", "mg", "g", "kg", "ml", "l", "cm", "m") NOT NULL DEFAULT "pcs",
    sell_price DECIMAL NOT NULL,
    buy_price DECIMAL NOT NULL,
    description TEXT DEFAULT NULL,
    picture_url VARCHAR(64) DEFAULT NULL,
    time_consumption ENUM("short", "long"),
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datetime_last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (id_item_category) REFERENCES item_category(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE item_in_store (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_item INT NOT NULL,
    id_store INT NOT NULL,
    stock INT NOT NULL,
    is_in_season BOOLEAN DEFAULT 0,
    nominal_discount DECIMAL DEFAULT 0,
    datetime_last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (id_item) REFERENCES item(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_store) REFERENCES store(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE checkout_cart (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_store INT NOT NULL,
    name VARCHAR(64) NOT NULL DEFAULT "New Cart",
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_store) REFERENCES store(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE item_in_checkout_cart (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_item INT NOT NULL,
    id_checkout_cart INT NOT NULL,

    quantity INT NOT NULL DEFAULT 0,
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_item) REFERENCES item(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_checkout_cart) REFERENCES checkout_cart(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE voucher_order (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(64) NOT NULL,
    code VARCHAR(15) NOT NULL,

    nominal_discount DECIMAL NOT NULL DEFAULT 0,
    minimum_item_cost DECIMAL NOT NULL,

    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datetime_expired DATETIME NOT NULL DEFAULT (DATE_ADD(NOW(), INTERVAL 5 DAY))
);

CREATE TABLE `order` (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_customer INT,
    id_cashier INT,
    id_store INT,
    id_voucher_order INT,

    item_buy_cost DECIMAL NOT NULL, # For backup purpose
    item_cost DECIMAL NOT NULL, # For backup purpose
    nominal_discount DECIMAL NOT NULL DEFAULT 0,

    payment_method ENUM("cash", "qris", "handpalm") NOT NULL DEFAULT "cash",
    `status` ENUM("cancelled", "pending", "wait-for-payment", "processed", "finished") NOT NULL DEFAULT "wait-for-payment",
    datetime_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datetime_finished DATETIME,

    # FOREIGN KEY (id_customer) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_cashier) REFERENCES cashier(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_store) REFERENCES store(id) ON UPDATE CASCADE ON DELETE SET NULL,
    # FOREIGN KEY (id_voucher_order) REFERENCES voucher_order(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE item_in_order (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_item INT,
    id_order INT NOT NULL,
    
    quantity INT NOT NULL DEFAULT 0,
    datetime_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,


    # For backup purpose
    id_item_category INT DEFAULT NULL,
    name VARCHAR(64) NOT NULL,
    size FLOAT NOT NULL,
    unit ENUM("pack", "pcs", "mg", "g", "kg", "ml", "l", "cm", "m") NOT NULL DEFAULT "pcs",
    sell_price DECIMAL NOT NULL,
    buy_price DECIMAL NOT NULL,
    description TEXT DEFAULT NULL,
    time_consumption ENUM("short", "long"),


    FOREIGN KEY (id_item) REFERENCES item(id) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (id_order) REFERENCES `order`(id) ON UPDATE CASCADE ON DELETE CASCADE,

    FOREIGN KEY (id_item_category) REFERENCES item_category(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE order_payment (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_order INT NOT NULL,
    account_number VARCHAR(15) NOT NULL,
    
    order_cost DECIMAL NOT NULL,
    admin_cost DECIMAL NOT NULL,

    datetime_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datetime_finished DATETIME,

    FOREIGN KEY (id_order) REFERENCES `order`(id) ON UPDATE CASCADE ON DELETE CASCADE
);



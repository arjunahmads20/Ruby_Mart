
INSERT INTO store 
    (name, address)
VALUES
    ("RB1", "Jl. ABC No. 11"),
    ("RB2", "Jl. Paus No. 8");

INSERT INTO user 
    (username, fullname, phone_number, email, gender, date_of_birth, password, role)
VALUES
    ("cashier1", "cashier 1", "0812345678", "cashier1@gmail.com", "female", "2000-08-08", "cashier1123", "cashier"),
    ("cashier2", "cashier 2", "0812345678", "cashier2@gmail.com", "female", "2000-08-08", "cashier2123", "cashier"),
    ("cashier3", "cashier 3", "0812345678", "cashier3@gmail.com", "female", "2000-08-08", "cashier3123", "cashier");
    ("admin1", "admin 1", "0812345678", "admin1@gmail.com", "male", "2000-08-08", "admin1123", "admin");

INSERT INTO cashier
    (id_user, id_store)
VALUES
    (1, 1),
    (2, 1),
    (3, 2);

INSERT INTO admin
    (id_user)
VALUES
    (4);

INSERT INTO item_category
    (name)
VALUES
    ("Sayur"),
    ("Buah"),
    ("Minuman");

INSERT INTO item
    (id_item_category, name, size, unit, sell_price, buy_price, description, time_consumption)
VALUES
    (1, "Kangkung", 1, "pcs", 3000, 2500, "Kandungan Gizi (per 100 gram kangkung segar): Kalori: 19 kkal, Karbohidrat: 3.14 gram, Protein: 2.6 gram, Serat: 2.1 gram, Vitamin C: 55 mg", "short"),
    (1, "Brokoli", 1, "kg", 24000, 22000, "Kandungan Gizi (per 100 gram): Kalori: 34 kkal, Protein: 2.82 gram, Lemak: 0.37 gram", "short"),
    (2, "Apel", 1, "kg", 25000, 24000, "Kandungan Gizi (per 100 gram): Kalori: 52 kkal, Karbohidrat: 13.81 gram, Protein: 0.26 gram, Serat: 2.4 gram", "short"),
    (2, "Jeruk", 1, "kg", 12000, 10000, "Kandungan Gizi (per 100 gram): Kalori: 45 kkal, Karbohidrat: 11.2 gram, Protein: 2.6 gram, Serat: 2.1 gram, Vitamin C: 55 mg", "short"),
    (3, "Sari Kacang Hijau", 1, "pcs", 5000, 4500, "Kandungan Gizi (per pcs): Kalori: 160 kkal, Karbohidrat: 36 gram, Protein: 2 gram", "long");
    
INSERT INTO item_in_store
    (id_item, id_store, stock, is_in_season)
VALUES
    (1, 1, 40, 0),
    (2, 1, 40, 0),
    (3, 1, 40, 0),
    (4, 1, 40, 0),
    (5, 1, 40, 0),
    (1, 2, 40, 0),
    (2, 2, 40, 0),
    (3, 2, 40, 0),
    (4, 2, 0, 0),
    (5, 2, 40, 0);

INSERT INTO checkout_cart
    (id_store, name)
VALUES
    (1, "CC1"),
    (1, "CC2"),
    (2, "CC3");

INSERT INTO voucher_order
    (name, code, nominal_discount, minimum_item_cost)
VALUES
    ("Voucher V1", "12345678", 3000, 20000);
# Preventing error by creating default instance with id 0
INSERT INTO voucher_order
    (id, name, code, nominal_discount, minimum_item_cost)
VALUES
    (0, "No voucher", "12345678", 0, 0);
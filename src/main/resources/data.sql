-- purchases
INSERT INTO purchase (id, buyer, purchase_date, total)
VALUES
    (1, 'Jonathan', '2025-06-04 10:00:00', 25.98),
    (2, 'Lucía', '2025-06-05 12:30:00', 18.50),
    (3, 'María', '2025-06-06 10:15:00', 24.98),
    (4, 'Carlos', '2025-06-06 12:30:00', 41.97);


-- purchase_items
INSERT INTO purchase_item (id, book_id, isbn, title, price, quantity, total, purchase_id)
VALUES
    (1, 1,'LIB001', 'El Principito', 12.99, 1, 12.99, 1),
    (2, 2,'LIB002', '1984', 12.99, 1, 12.99, 1),
    (3, 3, 'LIB003', 'Cien Años de Soledad', 18.50, 1, 18.50, 2),
    (4, 2,'LIB002', '1984', 12.49, 2, 24.98, 3),
    (5, 3,'LIB003', 'Cien Años de Soledad', 20.99, 1, 20.99, 4),
    (6, 4,'LIB004', 'Fahrenheit 451', 10.49, 2, 20.98, 4);
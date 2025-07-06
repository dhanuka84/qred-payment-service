INSERT INTO client (client_id, client_name) VALUES 
(1, 'Alice Johnson'),
(2, 'Bob Smith'),
(3, 'Charlie Brown');


INSERT INTO contract (contract_id, client_id, contract_number) VALUES 
(1, 1, 'C-1001'),
(2, 1, 'C-1002'),
(3, 2, 'C-2001'),
(4, 3, 'C-3001');


INSERT INTO payment (payment_id, payment_date, amount, type, contract_id) VALUES 
(1, '2024-01-15', 1000.00, 'incoming', 1),
(2, '2024-01-20', 500.00, 'outgoing', 1),
(3, '2024-02-01', 1200.00, 'incoming', 2),
(4, '2024-02-10', 750.00, 'outgoing', 3),
(5, '2024-03-01', 900.00, 'incoming', 4);

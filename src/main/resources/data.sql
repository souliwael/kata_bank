INSERT INTO accounts (id, balance) VALUES (1,50);
INSERT INTO accounts (id, balance) VALUES (2,200);
INSERT INTO transactions (transaction_date, amount, balance, operation_type, account_id) VALUES (NULL,100,0,'DEPOSIT',1);
INSERT INTO transactions (transaction_date, amount, balance, operation_type, account_id) VALUES (NULL,50,100,'WITHDRAW',1);
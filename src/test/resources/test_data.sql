DROP TABLE IF EXISTS Account;

CREATE TABLE account (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  currency_code CHAR(8) NOT NULL,
  amount DECIMAL(250) NOT NULL,
  owner VARCHAR(250) DEFAULT NULL
);

INSERT INTO account (currency_code, amount, owner) VALUES
  ('HKD', 100, 'user1'),
  ('HKD', 200, 'user2');

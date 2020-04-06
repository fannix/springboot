CREATE TABLE IF NOT EXISTS account(
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  currency_code CHAR(8) NOT NULL,
  amount DECIMAL(250) NOT NULL,
  owner VARCHAR(250) DEFAULT NULL
);

INSERT INTO account(id, currency_code, amount, owner) VALUES
  (12345678, 'HKD', 1000000, 'user3') ON DUPLICATE KEY UPDATE id=id;

INSERT INTO account(id, currency_code, amount, owner) VALUES
  (88888888, 'HKD', 1000000, 'user4') ON DUPLICATE KEY UPDATE id=id;

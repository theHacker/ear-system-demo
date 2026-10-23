CREATE TABLE quantity_report (
    id UUID NOT NULL CONSTRAINT pk__quantity_report PRIMARY KEY,
    amount DECIMAL(32, 6) NOT NULL,
    unit ENUM('KILOGRAM', 'PIECE', 'TON') NOT NULL,
    `YEAR` SMALLINT NOT NULL,
    `MONTH` TINYINT NOT NULL,
    device_type_number INT NOT NULL,
    registration_number INT NOT NULL,
    declaration_of_truth BOOLEAN NOT NULL
);

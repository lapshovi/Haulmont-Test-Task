CREATE TABLE client (
id UUID NOT NULL, 
email varchar(255),
name varchar(255),
passport_number BIGINT NOT NULL,
phone_number BIGINT,
primary key (id)
);

CREATE TABLE credit (
id UUID NOT NULL, 
limit_of_credit BIGINT NOT NULL, 
name_of_credit varchar(255) NOT NULL, 
percent_of_credit DOUBLE NOT NULL, 
primary key (id)
);

CREATE TABLE credit_offer (
id UUID NOT NULL, 
amount_of_credit BIGINT , 
credit_period_in_months INTEGER,
client_id uuid,
credit_id uuid,
paymentschedule_id uuid,
primary key (id),
FOREIGN KEY(client_id) REFERENCES client(id),
FOREIGN KEY(credit_id) REFERENCES credit(id),
FOREIGN KEY(paymentschedule_id) REFERENCES paymentschedule(id) ON DELETE CASCADE
);

CREATE TABLE payment_shedule (
id UUID NOT NULL, 
body_amount DOUBLE , 
payment_amount DOUBLE , 
payment_date DATE, 
percent_amount DOUBLE , 
remains DOUBLE , 
primary key (id)
);


insert into client (id, email, name, passport_number, phone_number) values ('c1c218e0-5ccb-4a2c-8ce0-7f1d9e1a1fcd','Ifg@gmail.com', 'Алексеев И.А.', '4565678745','9875647334');
insert into client (id, email, name, passport_number, phone_number) values ('857a83ae-b4e2-4b87-afcd-16c473698bab','ivan@bk.ru', 'Иванов И.И.', '3455657687','9876754634');
insert into client (id, email, name, passport_number, phone_number) values ('36262872-7b2d-44b5-b52e-0f2c3cf848ee', 'kuz@mail.ru', 'Кузнецов И.Е.', '5688456787','9316675634');

insert into credit (id, limit_of_credit, name_of_credit, percent_of_credit) values ('e73cfa77-f3ba-4b49-8ced-a35f1e5616e2','100000', 'Кредит 1', 10.0);
insert into credit (id, limit_of_credit, name_of_credit, percent_of_credit) values ('903163e8-8d6a-4ede-b5e7-efc05e3e8660','200000', 'Кредит 2', 15.0);

insert into credit_offer (id, amount_of_credit, client_id, credit_id, credit_period_in_months, paymentschedule_id) values ('2512e98f-4d3f-4fb5-8641-f4b35f1e0818', '50000', 'c1c218e0-5ccb-4a2c-8ce0-7f1d9e1a1fcd', 'e73cfa77-f3ba-4b49-8ced-a35f1e5616e2', '12', '4e70dcd5-d895-4817-88ac-fe366f273665');





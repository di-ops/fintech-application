insert into CUSTOMERS (id, first_name, last_name, passport, phone)
    values (default, 'Petr', 'Petrov', '1122 345411', '8-111-901-32-32');
insert into CUSTOMERS (id, first_name, last_name, passport, phone)
    values (default, 'Alex', 'Alexandrov', '7811 543211', '8-111-941-32-12');
insert into CUSTOMERS (id, first_name, last_name, passport, phone)
    values (default, 'Alex', 'Medvedev', '1122 193007', '8-111-913-45-65');

insert into ACCOUNTS (id, balance, customer_id)
    values (default, 0, 1);
insert into ACCOUNTS (id, balance, customer_id)
    values (default, 0, 2);
insert into ACCOUNTS (id, balance, customer_id)
    values (default, 0, 3);
insert into ACCOUNTS (id, balance, customer_id)
    values (default, 0, 3);

insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
    values (default, 1, '4110 0000 1111 1111', false);
insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
   values (default, 2, '4110 0000 2222 1111', false);
insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
values (default, 3, '4110 0000 3333 1111', false);
insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
values (default, 3, '4110 0000 3333 2222', false);
insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
values (default, 3, '4110 0000 4444 1111', false);
insert into CARDS (id, account_id, number_card, IS_ACTIVATED)
values (default, 3, '4110 0000 4444 2222', false);
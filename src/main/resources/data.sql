insert into asset_kind(name, ratio) values
    ('Квартира', 1.7),
    ('Дом', 1.5),
    ('Комната', 1.3);

insert into client(surname, name, patronymic, birthday, passport_series, passport_number) values
    ('Иванов', 'Иван', 'Иванович', PARSEDATETIME('21 01 1959, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), '1234', '111111'),
    ('Петров', 'Иван', 'Иванович', PARSEDATETIME('17 06 1982, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), '1234', '222222');

insert into agreement(agreement_number, agreement_date, insurance_payment, period_from, period_to, year_of_build,
                      asset_kind_id, area, calculation_date, insurance_premium, client_id,
                      country, region) values
(100000, PARSEDATETIME('15 05 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 5000000,
 PARSEDATETIME('20 05 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'),
 PARSEDATETIME('19 05 2020, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 1988, 1, 72.5,
 PARSEDATETIME('15 05 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 7777, 1, 'Россия', 'г. Москва'),
(100005, PARSEDATETIME('02 07 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 800000,
 PARSEDATETIME('11 07 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'),
 PARSEDATETIME('10 08 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 2003, 2, 106.2,
 PARSEDATETIME('02 07 2019, 00:00:00 AM','dd MM yyyy, hh:mm:ss a'), 1234.56, 2, 'Россия', 'г. Cанкт-Петербург');
                                                            

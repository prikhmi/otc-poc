-- 1) Should be APPROVED (all clean)
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_APPROVE_1', 'CUST_LOW_1', 'SHIP001',
    500.00, 'EUR',
    'DE', 'US',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

-- 2) Destination sanctioned -> REJECTED (sanctions rule)
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_REJECT_SANCTION_1', 'CUST_LOW_1', 'SHIP002',
    1000.00, 'EUR',
    'DE', 'IR',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

-- 3) Blocked customer -> REJECTED (blocked customer rule)
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_REJECT_BLOCKED_1', 'CUST_BLOCK_1', 'SHIP003',
    800.00, 'EUR',
    'DE', 'US',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

-- 4) High risk + big amount -> MANUAL_REVIEW
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_REVIEW_RISK_1', 'CUST_HIGH_1', 'SHIP004',
    6000.00, 'EUR',
    'NG', 'US',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

-- 5) Overdue invoices -> MANUAL_REVIEW
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_REVIEW_OVERDUE_1', 'CUST_MED_1', 'SHIP005',
    1500.00, 'EUR',
    'IN', 'US',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

-- 6) Credit limit exceeded -> REJECT (limit rule)
INSERT INTO payment_order (
    payment_id, customer_id, shipment_id, amount, currency,
    origin_country, destination_country, payment_method, terms, requested_date
) VALUES (
    'PAY_REJECT_LIMIT_1', 'CUST_LOW_2', 'SHIP006',
    2000.00, 'USD',
    'US', 'DE',
    'INVOICE', 'NET30',
    CURRENT_DATE
);

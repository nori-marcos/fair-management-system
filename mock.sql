-- Feiras
INSERT INTO fairs (id, name, description, start_date, end_date, location, city, state, created_by, created_at
)
VALUES ('00000001-0000-0000-0000-000000000001', 'Feira de Tecnologia 2025', 'Descrição mockada', '2025-10-01',
        '2025-10-03', 'Centro XYZ', 'Brasília', 'DF', 'system', now());

-- Roles
INSERT INTO roles (id, name)
VALUES ('00000002-0000-0000-0000-000000000001', 'ADMIN'),
       ('00000002-0000-0000-0000-000000000002', 'ORGANIZER'),
       ('00000002-0000-0000-0000-000000000003', 'EXHIBITOR'),
       ('00000002-0000-0000-0000-000000000004', 'VISITOR'),
       ('00000002-0000-0000-0000-000000000005', 'SELF');

-- Usuário ADMIN
INSERT INTO users (id, full_name, email, password, created_by, created_at
)
VALUES ('00000003-0000-0000-0000-000000000001', 'Alice Admin', 'alice@admin.com', 'admin123', 'system', NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ('00000003-0000-0000-0000-000000000001', '00000002-0000-0000-0000-000000000001');

-- Usuário ORGANIZER
INSERT INTO users (id, full_name, email, password, created_by, created_at
)
VALUES ('00000003-0000-0000-0000-000000000002', 'Bob Organizer', 'bob@org.com', 'org123', 'system', NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ('00000003-0000-0000-0000-000000000002', '00000002-0000-0000-0000-000000000002');

-- Usuário SELF
INSERT INTO users (id, full_name, email, password, created_by, created_at
)
VALUES ('00000003-0000-0000-0000-000000000003', 'David User', 'david@user.com', 'user123', 'system', NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ('00000003-0000-0000-0000-000000000003', '00000002-0000-0000-0000-000000000005');

-- Usuário VISITOR + SELF que está associado a uma feira existente
INSERT INTO users (id, full_name, email, password, created_by, created_at
)
VALUES ('00000003-0000-0000-0000-000000000004', 'Carol Visitor', 'carol@visitor.com', 'visitor123', 'system', NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ('00000003-0000-0000-0000-000000000004', '00000002-0000-0000-0000-000000000004'),
       ('00000003-0000-0000-0000-000000000004', '00000002-0000-0000-0000-000000000005');

-- Visitor
INSERT INTO visitors (id, user_id, contact_name, contact_email, fair_id, created_by, created_at)
VALUES ('00000004-0000-0000-0000-000000000001',
        '00000003-0000-0000-0000-000000000004',
        'Carol Visitor',
        'carol@visitor.com',
        '00000001-0000-0000-0000-000000000001',
        'system',
        NOW());

-- Ticket gerado automaticamente para o visitor
INSERT INTO tickets (id, fair_id, visitor_id, exhibitor_id, created_by, created_at)
VALUES ('00000005-0000-0000-0000-000000000001',
        '00000001-0000-0000-0000-000000000001',
        '00000004-0000-0000-0000-000000000001',
        NULL,
        'system',
        NOW());

-- Usuário EXHIBITOR + SELF que está associado a uma feira existente
INSERT INTO users (id, full_name, email, password, created_by, created_at
)
VALUES ('00000003-0000-0000-0000-000000000005', 'Elias Expositor', 'elias@techcorp.com', 'expositor123', 'system',
        NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ('00000003-0000-0000-0000-000000000005', '00000002-0000-0000-0000-000000000003'),
       ('00000003-0000-0000-0000-000000000005', '00000002-0000-0000-0000-000000000005');

-- Company
INSERT INTO companies (id, name, email, phone, cnpj, created_by, created_at
)
VALUES ('00000006-0000-0000-0000-000000000001', 'TechCorp', 'contato@techcorp.com', '6133221100', '12345678000199',
        'system', NOW());

-- Exhibitor
INSERT INTO exhibitors (id, user_id, contact_name, contact_email,
                        company_id, fair_id, created_by, created_at
)
VALUES ('00000007-0000-0000-0000-000000000001',
        '00000003-0000-0000-0000-000000000005',
        'Elias Expositor',
        'elias@techcorp.com',
        '00000006-0000-0000-0000-000000000001',
        '00000001-0000-0000-0000-000000000001',
        'system',
        NOW());

-- Ticket for Exhibitor
INSERT INTO tickets (id, fair_id, visitor_id, exhibitor_id, created_by, created_at)
VALUES ('00000005-0000-0000-0000-000000000002',
        '00000001-0000-0000-0000-000000000001',
        NULL,
        '00000007-0000-0000-0000-000000000001',
        'system',
        NOW());

-- Produto de tecnologia da empresa TechCorp
INSERT INTO products (id, name, description, price, company_id, created_by, created_at)
VALUES ('00000008-0000-0000-0000-000000000001',
        'Notebook Ultra X',
        'Notebook com processador Ryzen 9, 32GB RAM, SSD 1TB e GPU RTX 4060',
        8999.90,
        '00000006-0000-0000-0000-000000000001',
        'elias@techcorp.com',
        NOW());
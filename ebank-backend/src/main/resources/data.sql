-- Insert test users
-- Password for all users is "password" (encoded with BCrypt)
INSERT INTO users (nom, prenom, numero_identite, date_naissance, email, adresse_postale, login, password, role, enabled, account_non_expired, account_non_locked, credentials_non_expired, created_at, updated_at) VALUES
('Agent', 'Guichet', 'AGENT001', '1985-01-01', 'agent@ebank.com', '123 Rue de la Banque, Paris', 'agent1', '$2a$10$EblZqNptyYdFCAC/H5uuVOxSTJ6h9OYFCRoJXDmhJ9F9EzFMZCIHC', 'AGENT_GUICHET', true, true, true, true, NOW(), NOW()),
('Dupont', 'Jean', 'CLIENT001', '1990-05-15', 'jean.dupont@email.com', '456 Avenue des Clients, Lyon', 'client1', '$2a$10$EblZqNptyYdFCAC/H5uuVOxSTJ6h9OYFCRoJXDmhJ9F9EzFMZCIHC', 'CLIENT', true, true, true, true, NOW(), NOW()),
('Martin', 'Marie', 'CLIENT002', '1988-08-20', 'marie.martin@email.com', '789 Boulevard des Comptes, Marseille', 'client2', '$2a$10$EblZqNptyYdFCAC/H5uuVOxSTJ6h9OYFCRoJXDmhJ9F9EzFMZCIHC', 'CLIENT', true, true, true, true, NOW(), NOW());

-- Insert test bank accounts for clients
INSERT INTO bank_accounts (rib, solde, statut, client_id, created_at, updated_at, last_activity_at) VALUES
('FR1420041010050500013M02606', 1500.00, 'OUVERT', 2, NOW(), NOW(), NOW()),
('FR1420041010050500013M02607', 2500.00, 'OUVERT', 2, NOW(), NOW(), NOW()),
('FR1420041010050500013M02608', 3000.00, 'OUVERT', 3, NOW(), NOW(), NOW());

-- Insert sample operations
INSERT INTO operations (intitule, type, montant, motif, compte_source_id, compte_destination_id, date_operation, solde_avant, solde_apres) VALUES
('Dépôt initial', 'CREDIT', 1500.00, 'Ouverture de compte', 1, null, NOW() - INTERVAL 30 DAY, 0.00, 1500.00),
('Virement reçu de FR1420041010050500013M02607', 'CREDIT', 200.00, 'Remboursement prêt', 1, 2, NOW() - INTERVAL 15 DAY, 1500.00, 1700.00),
('Virement émis vers FR1420041010050500013M02606', 'DEBIT', 200.00, 'Remboursement prêt', 2, 1, NOW() - INTERVAL 15 DAY, 2700.00, 2500.00),
('Retrait DAB', 'DEBIT', 200.00, 'Retrait espèces', 1, null, NOW() - INTERVAL 10 DAY, 1700.00, 1500.00),
('Dépôt initial', 'CREDIT', 2700.00, 'Ouverture de compte', 2, null, NOW() - INTERVAL 25 DAY, 0.00, 2700.00),
('Dépôt initial', 'CREDIT', 3000.00, 'Ouverture de compte', 3, null, NOW() - INTERVAL 20 DAY, 0.00, 3000.00); 
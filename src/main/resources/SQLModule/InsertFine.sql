-- Операция добавления штрафа
INSERT INTO fines (drone_id, violation, penalty, created_date)
VALUES (?, ?, ?, CURRENT_TIMESTAMP)


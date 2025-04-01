COPY operadoras_saude
FROM '/dados/Relatorio_cadop.csv'
DELIMITER ';'
CSV HEADER
ENCODING 'UTF8';
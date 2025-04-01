COPY TB_1T2024(data, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
FROM '/dados/1T2024.csv'
DELIMITER ';'
CSV HEADER;
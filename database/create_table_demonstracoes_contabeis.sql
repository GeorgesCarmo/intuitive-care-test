CREATE TABLE TB_1T2024 (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    reg_ans VARCHAR(10) NOT NULL,
    cd_conta_contabil VARCHAR(20) NOT NULL,
    descricao TEXT NOT NULL,
    vl_saldo_inicial NUMERIC(15,2) NOT NULL,
    vl_saldo_final NUMERIC(15,2) NOT NULL
);
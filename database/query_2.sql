/*
Quais as 10 operadoras com maiores despesas nessa categoria no último ano?
*/
SELECT DISTINCT t4.reg_ans, os.registro_ans
FROM TB_4T2024 t4
LEFT JOIN operadoras_saude os ON CAST(t4.reg_ans AS INT) = os.registro_ans
WHERE os.registro_ans IS NULL;

SELECT DISTINCT descricao FROM TB_4T2024;

WITH despesas_anuais AS (
    SELECT 
        reg_ans,
        SUM(vl_saldo_final - vl_saldo_inicial) AS total_despesas
    FROM (
        SELECT reg_ans, descricao, vl_saldo_inicial, vl_saldo_final FROM tb_1T2024
        UNION ALL
        SELECT reg_ans, descricao, vl_saldo_inicial, vl_saldo_final FROM tb_2T2024
        UNION ALL
        SELECT reg_ans, descricao, vl_saldo_inicial, vl_saldo_final FROM tb_3T2024
        UNION ALL
        SELECT reg_ans, descricao, vl_saldo_inicial, vl_saldo_final FROM tb_4T2024
    ) AS todas_tabelas
    WHERE descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR %'
    GROUP BY reg_ans
)
SELECT 
    os.registro_ans,
    os.razao_social,
    d.total_despesas
FROM despesas_anuais d
JOIN operadoras_saude os ON CAST(d.reg_ans AS INT) = os.registro_ans
ORDER BY d.total_despesas DESC
LIMIT 10;

/*
registro_ans|razao_social                                            |total_despesas|
------------+--------------------------------------------------------+--------------+
        5711|BRADESCO SAÚDE S.A.                                     |30941701628.46|
        6246|SUL AMERICA COMPANHIA DE SEGURO SAÚDE                   |21124940442.30|
      326305|AMIL ASSISTÊNCIA MÉDICA INTERNACIONAL S.A.              |20820818085.36|
      359017|NOTRE DAME INTERMÉDICA SAÚDE S.A.                       | 9307980465.62|
      368253|HAPVIDA ASSISTENCIA MEDICA S.A.                         | 7755562753.15|
      346659|CAIXA DE ASSISTÊNCIA DOS FUNCIONÁRIOS DO BANCO DO BRASIL| 7459368017.21|
      339679|UNIMED NACIONAL - COOPERATIVA CENTRAL                   | 7002487899.10|
      302147|PREVENT SENIOR PRIVATE OPERADORA DE SAÚDE LTDA          | 5920615078.62|
      343889|UNIMED BELO HORIZONTE COOPERATIVA DE TRABALHO MÉDICO    | 5411476065.42|
         701|UNIMED SEGUROS SAÚDE S/A                                | 4824024195.15|
*/
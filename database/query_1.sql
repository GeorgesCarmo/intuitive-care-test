/*
Quais as 10 operadoras com maiores despesas em "EVENTOS/ SINISTROS CONHECIDOS OU
AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" no último trimestre?
*/
SELECT 
    os.razao_social,
    t4.reg_ans,
    SUM(t4.vl_saldo_inicial - t4.vl_saldo_final) AS total_despesas
FROM TB_4T2024 t4
JOIN operadoras_saude os 
    ON CAST(t4.reg_ans AS INT) = os.registro_ans
WHERE t4.descricao = 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR '
GROUP BY os.razao_social, t4.reg_ans
ORDER BY total_despesas DESC
LIMIT 10;

/*
razao_social                                          |reg_ans|total_despesas|
------------------------------------------------------+-------+--------------+
ASSOCIAÇÃO PETROBRAS DE SAÚDE - APS                   |422631 |   67646814.33|
ASSOCIAÇÃO DO FISCO DE ALAGOAS                        |393533 |    2150359.06|
UNIMED DE VOTUPORANGA - COOPERATIVA DE TRABALHO MÉDICO|328073 |    1201879.75|
VISION MED ASSISTÊNCIA MÉDICA LTDA                    |403911 |    1115432.88|
BIO SAÚDE SERVIÇOS MÉDICOS LTDA                       |402966 |    1033927.70|
FUNDO DE ASSISTÊNCIA À SAÚDE DOS FUNCIONÁRIOS DO BEC  |414689 |     715683.03|
SUL AMÉRICA SERVIÇOS DE SAÚDE S.A.                    |416428 |     286717.70|
GAMA SAUDE LTDA.                                      |407011 |     249041.63|
ODONTO JARAGUÁ LTDA                                   |312321 |      31121.11|
SISTEMA PREVSAUDE DENTAL LTDA                         |333239 |      29996.69|
*/